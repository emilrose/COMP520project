/*
 * JOOS is Copyright (C) 1997 Laurie Hendren & Michael I. Schwartzbach
 *
 * Reproduction of all or part of this software is permitted for
 * educational or research use on condition that this copyright notice is
 * included in any copy. This software comes with no warranty of any
 * kind. In no event will the authors be liable for any damages resulting from
 * use of this software.
 *
 * email: hendren@cs.mcgill.ca, mis@brics.dk
 */


/*
 
 Group 12:
 Emil Rose, 260515401
 Samuel Laferriere, 260462162
 Zoe Guan, 260380162
 
 */

/* iload x        iload x        iload x
 * ldc 0          ldc 1          ldc 2
 * imul           imul           imul
 * ------>        ------>        ------>
 * ldc 0          iload x        iload x
 *                               dup
 *                               iadd
 */

int simplify_multiplication_right(CODE **c)
{ int x,k;
  if (is_iload(*c,&x) && 
      is_ldc_int(next(*c),&k) && 
      is_imul(next(next(*c)))) {
     if (k==0) return replace(c,3,makeCODEldc_int(0,NULL));
     else if (k==1) return replace(c,3,makeCODEiload(x,NULL));
     else if (k==2) return replace(c,3,makeCODEiload(x,
                                       makeCODEdup(
                                       makeCODEiadd(NULL))));
     return 0;
  }
  return 0;
}

/* dup
 * astore x
 * pop
 * -------->
 * astore x
 */
int simplify_astore(CODE **c)
{ int x;
  if (is_dup(*c) &&
      is_astore(next(*c),&x) &&
      is_pop(next(next(*c)))) {
     return replace(c,3,makeCODEastore(x,NULL));
  }
  return 0;
}

/* iload x
 * ldc k   (0<=k<=127)
 * iadd
 * istore x
 * --------->
 * iinc x k
 */ 
int positive_increment(CODE **c)
{ int x,y,k;
  if (is_iload(*c,&x) &&
      is_ldc_int(next(*c),&k) &&
      is_iadd(next(next(*c))) &&
      is_istore(next(next(next(*c))),&y) &&
      x==y && 0<=k && k<=127) {
     return replace(c,4,makeCODEiinc(x,k,NULL));
  }
  return 0;
}

/* goto L1
 * ...
 * L1:
 * goto L2
 * ...
 * L2:
 * --------->
 * goto L2
 * ...
 * L1:    (reference count reduced by 1)
 * goto L2
 * ...
 * L2:    (reference count increased by 1)  
 */
int simplify_goto_goto(CODE **c)
{ int l1,l2;
  if (is_goto(*c,&l1) && is_goto(next(destination(l1)),&l2) && l1>l2) {
     droplabel(l1);
     copylabel(l2);
     return replace(c,1,makeCODEgoto(l2,NULL));
  }
  return 0;
}


/********************** more patterns **********************/

/*              ...:a
 * dup          ...:a:a
 * istore x     ...:a      local[x] = a
 * pop          ...:
 * -------->
 * istore x     ...:       local[x] = a
 */
int simplify_istore(CODE **c)
{ int x;
    if (is_dup(*c) &&
        is_istore(next(*c),&x) &&
        is_pop(next(next(*c)))) {
        return replace(c,3,makeCODEistore(x,NULL));
    }
    return 0;
}


/* iload x      ...:local[x]
 * ldc k        ...:local[x]:k
 * isub         ...:local[x]-k
 * istore x     ...:               local[x] = local[x]-k
 * --------->
 * iinc x -k    ...:               local[x] = local[x]-k
 */
int decrement(CODE **c)
{ int x,y,k;
    if (is_iload(*c,&x) &&
        is_ldc_int(next(*c),&k) &&
        is_isub(next(next(*c))) &&
        is_istore(next(next(next(*c))),&y) &&
        x==y && -127<=k && k<=127) {
        return replace(c,4,makeCODEiinc(x,-k,NULL));
    }
    return 0;
}


/*                  ...:a
 * store x          ...:       local[x] = a
 * load x           ...:a
 * --------->
 * dup              ...:a:a
 * store x          ...:a      local[x] = a
 *
 * This doesn't directly decrease the number of instructions but may lead to
 * an optimization involving dup (ex: simplify_istore/simplify_astore).
 */
int simplify_store_load(CODE **c) {
    int x, y;
    if (is_istore(*c, &x) &&
        is_iload(next(*c), &y) &&
        x==y) {
        return replace(c, 2, makeCODEdup(makeCODEistore(x, NULL)));
    }
    else if (is_astore(*c, &x) &&
             is_aload(next(*c), &y) &&
             x==y) {
        return replace(c, 2, makeCODEdup(makeCODEastore(x, NULL)));
    }
    return 0;
}

/* store x          ...:b      local[x] = a
 * store x          ...:       local[x] = b
 * --------->
 * pop              ...:b
 * store x          ...:       local[x] = b
 *
 * This doesn't directly decrease the number of instructions but may lead to
 * an optimization involving pop (ex: remove_load_pop/remove_dup/pop).
 */
int simplify_store_store(CODE **c) {
    int x, y;
    if (is_istore(*c, &x) &&
        is_istore(next(*c), &y) &&
        x==y) {
        return replace(c, 2, makeCODEpop(makeCODEistore(x, NULL)));
    }
    else if (is_astore(*c, &x) &&
             is_astore(next(*c), &y) &&
             x==y) {
        return replace(c, 2, makeCODEpop(makeCODEastore(x, NULL)));
    }
    return 0;
}

/* load x
 * load x
 * --------->
 * load x
 * dup
 *
 * ldc x        
 * ldc x
 * --------->
 * ldc x
 * dup
 *
 * This doesn't directly decrease the number of instructions but may lead to
 * an optimization involving dup (ex: simplify_dup_compare).
 */
int simplify_load_load(CODE **c) {
    int x, y;
    if (is_iload(*c, &x) &&
        is_iload(next(*c), &y) &&
        x==y) {
        return replace(c, 2, makeCODEiload(x, makeCODEdup(NULL)));
    }
    else if (is_aload(*c, &x) &&
             is_aload(next(*c), &y) &&
             x==y) {
        return replace(c, 2, makeCODEaload(x, makeCODEdup(NULL)));
    }
    else if (is_ldc_int(*c, &x) &&
             is_ldc_int(next(*c), &y) &&
             x==y) {
        return replace(c, 2, makeCODEldc_int(x, makeCODEdup(NULL)));
    }
    return 0;
}


/* iload/aload x        ...:local[x]
 * istore/astore x      ...:               local[x] = local[x]
 * --------->
 * nop
 */
int remove_load_store(CODE **c)
{
    int x, y;
    if (is_iload(*c,&x) &&
        is_istore(next(*c), &y) &&
        x==y) {
        return replace(c, 2, makeCODEnop(NULL));
    }
    else if (is_aload(*c, &x) &&
             is_astore(next(*c), &y) &&
             x==y) {
        return replace(c, 2, makeCODEnop(NULL));
    }
    return 0;
}


/* load x
 * pop
 * --------->
 * nop
 *
 * ldc k
 * pop
 * --------->
 * nop
 */
int remove_load_pop(CODE **c) {
    int x;
    char* arg;
    if (is_iload(*c, &x) ||
        is_aload(*c, &x) ||
        is_ldc_int(*c, &x) ||
        is_ldc_string(*c, &arg)) {
        if (is_pop(next(*c))) {
            replace(c, 2, makeCODEnop(NULL));
        }
    }
    return 0;
}


/* dup
 * pop
 * --------->
 * nop
 */
int remove_dup_pop(CODE **c) {
    if (is_dup(*c) && is_pop(next(*c))) {
        replace(c, 2, makeCODEnop(NULL));
    }
    return 0;
}

/* dup
 * swap
 * --------->
 * dup
 */
int remove_dup_swap(CODE **c) {
    if (is_dup(*c) && is_swap(next(*c))) {
        replace(c, 2, makeCODEdup(NULL));
    }
    return 0;
}


/* swap
 * swap
 * --------->
 * nop
 */
int remove_swap_swap(CODE **c) {
    if (is_swap(*c) && is_swap(next(*c))) {
        replace(c, 2, makeCODEdup(NULL));
    }
    return 0;
}


/* load_instruction1 (can be ldc)
 * load_instruction2 (can be ldc)
 * swap
 * --------->
 * load_instruction2
 * load_instruction1
 */
int swap_loads(CODE **c) {
    int x, y;
    char *arg;
    char *arg2;
    if (is_iload(*c, &x) &&
        next(*c)!=NULL &&
        is_swap(next(next(*c)))) {
        if (is_iload(next(*c), &y)) {
            return replace(c, 3, makeCODEiload(y, makeCODEiload(x, NULL)));
        }
        else if (is_aload(next(*c), &y)) {
            return replace(c, 3, makeCODEaload(y, makeCODEiload(x, NULL)));
        }
        else if (is_ldc_int(next(*c), &y)) {
            return replace(c, 3, makeCODEldc_int(y, makeCODEiload(x, NULL)));
        }
        else if (is_ldc_string(next(*c), &arg)) {
            return replace(c, 3, makeCODEldc_string(arg, makeCODEiload(x, NULL)));
        }
        else if (is_aconst_null(next(*c))) {
            return replace(c, 3, makeCODEaconst_null(makeCODEiload(x, NULL)));
        }
    }
    else if (is_aload(*c, &x) &&
             next(*c)!=NULL &&
             is_swap(next(next(*c)))) {
        if (is_iload(next(*c), &y)) {
            return replace(c, 3, makeCODEiload(y, makeCODEaload(x, NULL)));
        }
        else if (is_aload(next(*c), &y)) {
            return replace(c, 3, makeCODEaload(y, makeCODEaload(x, NULL)));
        }
        else if (is_ldc_int(next(*c), &y)) {
            return replace(c, 3, makeCODEldc_int(y, makeCODEaload(x, NULL)));
        }
        else if (is_ldc_string(next(*c), &arg)) {
            return replace(c, 3, makeCODEldc_string(arg, makeCODEaload(x, NULL)));
        }
        else if (is_aconst_null(next(*c))) {
            return replace(c, 3, makeCODEaconst_null(makeCODEaload(x, NULL)));
        }
    }
    else if (is_ldc_int(*c, &x) &&
             next(*c)!=NULL &&
             is_swap(next(next(*c)))) {
        if (is_iload(next(*c), &y)) {
            return replace(c, 3, makeCODEiload(y, makeCODEldc_int(x, NULL)));
        }
        else if (is_aload(next(*c), &y)) {
            return replace(c, 3, makeCODEaload(y, makeCODEldc_int(x, NULL)));
        }
        else if (is_ldc_int(next(*c), &y)) {
            return replace(c, 3, makeCODEldc_int(y, makeCODEldc_int(x, NULL)));
        }
        else if (is_ldc_string(next(*c), &arg)) {
            return replace(c, 3, makeCODEldc_string(arg, makeCODEldc_int(x, NULL)));
        }
        else if (is_aconst_null(next(*c))) {
            return replace(c, 3, makeCODEaconst_null(makeCODEldc_int(x, NULL)));
        }
    }
    else if (is_ldc_string(*c, &arg) &&
             next(*c)!=NULL &&
             is_swap(next(next(*c)))) {
        if (is_iload(next(*c), &y)) {
            return replace(c, 3, makeCODEiload(y, makeCODEldc_string(arg, NULL)));
        }
        else if (is_aload(next(*c), &y)) {
            return replace(c, 3, makeCODEaload(y, makeCODEldc_string(arg, NULL)));
        }
        else if (is_ldc_int(next(*c), &y)) {
            return replace(c, 3, makeCODEldc_int(y, makeCODEldc_string(arg, NULL)));
        }
        else if (is_ldc_string(next(*c), &arg2)) {
            return replace(c, 3, makeCODEldc_string(arg2,makeCODEldc_string(arg, NULL)));
        }
        else if (is_aconst_null(next(*c))) {
            return replace(c, 3, makeCODEaconst_null(makeCODEldc_string(arg, NULL)));
        }
    }
    else if (is_aconst_null(*c) &&
             next(*c)!=NULL &&
             is_swap(next(next(*c)))) {
        if (is_iload(next(*c), &y)) {
            return replace(c, 3, makeCODEiload(y, makeCODEaconst_null(NULL)));
        }
        else if (is_aload(next(*c), &y)) {
            return replace(c, 3, makeCODEaload(y, makeCODEaconst_null(NULL)));
        }
        else if (is_ldc_int(next(*c), &y)) {
            return replace(c, 3, makeCODEldc_int(y, makeCODEaconst_null(NULL)));
        }
        else if (is_ldc_string(next(*c), &arg2)) {
            return replace(c, 3, makeCODEldc_string(arg2, makeCODEaconst_null(NULL)));
        }
        else if (is_aconst_null(next(*c))) {
            return replace(c, 3, makeCODEaconst_null(makeCODEaconst_null(NULL)));
        }
    }
    return 0;
}


/* ireturn/areturn/return
 * non-label instruction
 * --------->
 * ireturn/areturn/return
 *
 * The non-label instruction will never be reached.
 * If non-label instruction uses label, decrease the reference count of the label 
 * (taken care of by replace_modified).
 */
int simplify_return(CODE **c) {
    int l;
    if (next(*c)!=NULL && !(is_label(next(*c), &l))) {
        if (is_ireturn(*c)) {
            return replace_modified(c, 2, makeCODEireturn(NULL));
        }
        else if (is_areturn(*c)) {
            return replace_modified(c, 2, makeCODEareturn(NULL));
        }
        else if (is_return(*c)) {
            return replace_modified(c, 2, makeCODEreturn(NULL));
        }
    }
    return 0;
}


/* ldc_int x            ...:x
 * ldc_int y            ...:x:y
 * iop                  ...:x+y
 * --------->
 * ldc_int op(x,y)      ...:x+y
 */
int simplify_ldc_ldc_op(CODE **c)
{
    int x, y;
    if (is_ldc_int(*c, &x)
        && is_ldc_int(next(*c), &y)
        && x>=-128 && x<=127
        && y>=-128 && y<=127) {
        if (is_iadd(next(next(*c))) && (x+y)>=-128 && (x+y)<=127) {
            return replace(c, 3, makeCODEldc_int(x+y, NULL));
        }
        else if (is_isub(next(next(*c))) && (x-y)>=-128 && (x-y)<=127) {
            return replace(c, 3, makeCODEldc_int(x-y, NULL));
        }
        else if (is_imul(next(next(*c))) && (x*y)>=-128 && (x*y)<=127) {
            return replace(c, 3, makeCODEldc_int(x*y, NULL));
        }
        else if (is_idiv(next(next(*c))) && (x/y)>=-128 && (x/y)<=127) {
            return replace(c, 3, makeCODEldc_int(x/y, NULL));
        }
        else if (is_irem(next(next(*c)))) {
            return replace(c, 3, makeCODEldc_int(x%y, NULL));
        }
    }
    return 0;
}


/* iinc k x
 * iinc k y
 * --------->
 * iinc k x+y
 */
int simplify_iinc_iinc(CODE **c)
{
    int k, k2, x, y;
    if (is_iinc(*c, &k, &x) &&
        x>=-128 && x<=127 &&
        is_iinc(next(*c), &k2, &y) &&
        k==k2 &&
        y>=-128 && y<=127 &&
        (x+y)>=-128 && (x+y)<=127) {
        return replace(c, 2, makeCODEiinc(k, x+y, NULL));
    }
    return 0;
}


/* Assuming l1 and l2 have indegree 1:
 *
 * if_icmpeq/if_icmpne/if_icmplt/if_icmple/if_icmpgt/if_icmpge l1
 * iconst_0
 * goto l2
 * l1:
 * iconst_1
 * l2:
 * ifeq l3
 * --------->
 * if_icmpne/if_icmpeq/if_icmpge/if_icmpgt/if_icmple/if_icmplt l3
 *
 *
 * ifeq/ifne/if_acmpeq/if_acmpne/ifnull/ifnonnull l1
 * iconst_0
 * goto l2
 * l1:
 * iconst_1
 * l2:
 * ifeq l3
 * --------->
 * ifne/ifeq/if_acmpne/if_acmpeq/ifnonnull/ifnull l3
 *
 *
 * "if (cmp) x=1
 * else x=0           is equivalent to    "if !cmp do..."
 * if x==0 do..."
 */
int simplify_if_cmp(CODE **c)
{
    int l1, l2, l3;
    int l_1, l_2;
    int c1, c2;
    if (is_if(c, &l1) &&
        is_ldc_int(next(*c), &c1) &&
        c1==0 &&
        is_goto(next(next(*c)), &l2) &&
        is_label(next(next(next(*c))), &l_1) &&
        uniquelabel(l_1) &&
        l1==l_1 &&
        is_ldc_int(next(next(next(next(*c)))), &c2) &&
        c2!=0 && c2>=-128 && c2 <=128 &&
        is_label(next(next(next(next(next(*c))))), &l_2) &&
        l2==l_2 &&
        uniquelabel(l_2) &&
        is_ifeq(next(next(next(next(next(next(*c)))))), &l3)) {
        
        if (is_if_icmpeq(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmpne(l3, NULL));
        }
        else if (is_if_icmpne(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmpeq(l3, NULL));
        }
        else if (is_if_icmplt(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmpge(l3, NULL));
        }
        else if (is_if_icmple(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmpgt(l3, NULL));
        }
        else if (is_if_icmpgt(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmple(l3, NULL));
        }
        else if (is_if_icmpge(*c, &l1)) {
            return replace(c, 7, makeCODEif_icmplt(l3, NULL));
        }
        else if (is_ifeq(*c, &l1)) {
            return replace(c, 7, makeCODEifne(l3, NULL));
        }
        else if (is_ifne(*c, &l1)) {
            return replace(c, 7, makeCODEifeq(l3, NULL));
        }
        else if (is_if_acmpeq(*c, &l1)) {
            return replace(c, 7, makeCODEif_acmpne(l3, NULL));
        }
        else if (is_if_acmpne(*c, &l1)) {
            return replace(c, 7, makeCODEif_acmpeq(l3, NULL));
        }
        else if (is_ifnull(*c, &l1)) {
            return replace(c, 7, makeCODEifnonnull(l3, NULL));
        }
        else if (is_ifnonnull(*c, &l1)) {
            return replace(c, 7, makeCODEifnull(l3, NULL));
        }
    }
    return 0;
}


/* Assuming l1 and l2 have indegree 1:
 *
 * ldc "str"
 * dup
 * ifnull l1
 * goto l2
 * l1: (assume l1 has in-degree 1)
 * pop
 * ldc "null"
 * l2:
 * --------->
 * ldc "str"
 *
 * "str" is not null so the instructions from "dup" to "l2:" are unnecessary.
 */
int simplify_ifnull(CODE **c) {
    char *str;
    char *str2;
    int l1, l2;
    int l_1, l_2;
    if (is_ldc_string(*c, &str) &&
        is_dup(next(*c)) &&
        is_ifnull(next(next(*c)), &l1) &&
        uniquelabel(l1) &&
        is_goto(next(next(next(*c))), &l2) &&
        uniquelabel(l2) &&
        is_label(next(next(next(next(*c)))), &l_1) &&
        l1==l_1 &&
        is_pop(next(next(next(next(next(*c)))))) &&
        is_ldc_string(next(next(next(next(next(next(*c)))))), &str2) &&
        is_label(next(next(next(next(next(next(next(*c))))))), &l_2) &&
        l2==l_2) {
        return replace(c, 8, makeCODEldc_string(str, NULL));
    }
    return 0;
}



/* iconst_0         ...:0
 * goto l           ...:0       branch to l
 * ...
 * l:               ...:0
 * dup              ...:0:0
 * ifeq l2          ...:0       branch to l2
 * ...
 * l2:              ...:0
 * --------->
 * iconst_0         ...:0
 * goto l2          ...:0       branch to l2
 * ...
 * l:
 * dup
 * ifeq l2
 * ...
 * l2:              ...:0
 *
 * l reference count decreased by 1
 * l2 reference count increased by 1
 */
int goto_dup_ifeq(CODE **c) {
    int x, l, l2;
    if (is_ldc_int(*c, &x) &&
        x==0 &&
        is_goto(next(*c), &l) &&
        is_dup(next(destination(l))) &&
        is_ifeq(next(next(destination(l))), &l2) && l>l2) {
        droplabel(l);
        copylabel(l2);
        return replace(c, 2, makeCODEldc_int(0, makeCODEgoto(l2, NULL)));
    }
    else if (is_ldc_int(*c, &x) &&
        x!=0 &&
        is_goto(next(*c), &l) &&
        is_dup(next(destination(l))) &&
        is_ifne(next(next(destination(l))), &l2) && l>l2) {
        droplabel(l);
        copylabel(l2);
        return replace(c, 2, makeCODEldc_int(x, makeCODEgoto(l2, NULL)));
    }
    return 0;
}

/* iconst_0         ...:0
 * goto l           ...:0       branch to l
 * ...
 * l:               ...:0
 * ifeq l2          ...:        branch to l2
 * ...
 * l2:              ...:        
 * --------->
 * goto l2:         ...:        branch to l2
 * ...
 * l:
 * ifeq l2          ...:
 *  ...
 * l2:
 *
 * l reference count decreased by 1
 * l2 reference count increased by 1
 */
int goto_ifeq(CODE **c) {
    int x, l, l2;
    if (is_ldc_int(*c, &x) &&
        x==0 &&
        is_goto(next(*c), &l) &&
        is_ifeq(next(destination(l)), &l2) && l>l2) {
        droplabel(l);
        copylabel(l2);
        return replace(c, 2, makeCODEgoto(l2, NULL));
    }
    else if (is_ldc_int(*c, &x) &&
        x!=0 && x>=-128 && x<=127 &&
        is_goto(next(*c), &l) &&
        is_ifne(next(destination(l)), &l2) && l>l2) {
        droplabel(l);
        copylabel(l2);
        return replace(c, 2, makeCODEgoto(l2, NULL));
    }
    return 0;
}


/* ifne l           ...:a       branch to l
 * ...
 * l:               ...:a
 * dup              ...:a:a
 * ifne l2          ...:a       branch to l2
 * --------->
 * ifne l2          ...:a       branch to l2
 * ...
 * l:
 * dup
 * ifne l2
 *
 * l reference count decreased by 1
 * l2 reference count increased by 1
 */
int skip_label_dup(CODE **c) {
    int l, l2;
    if (is_if(c, &l) &&
        is_dup(next(destination(l)))) {
        if (is_ifne(*c, &l) && is_ifne(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEifne(l2, NULL));
        }
        else if (is_ifeq(*c, &l) && is_ifeq(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEifeq(l2, NULL));
        }
        else if (is_if_icmpeq(*c, &l) && is_if_icmpeq(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmpeq(l2, NULL));
        }
        else if (is_if_icmpne(*c, &l) && is_if_icmpne(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmpne(l2, NULL));
        }
        else if (is_if_icmplt(*c, &l) && is_if_icmplt(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmplt(l2, NULL));
        }
        else if (is_if_icmple(*c, &l) && is_if_icmple(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmple(l2, NULL));
        }
        else if (is_if_icmpgt(*c, &l) && is_if_icmpgt(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmpgt(l2, NULL));
        }
        else if (is_if_icmpge(*c, &l) && is_if_icmpge(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_icmpge(l2, NULL));
        }
        else if (is_if_acmpeq(*c, &l) && is_if_acmpeq(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_acmpeq(l2, NULL));
        }
        else if (is_if_acmpne(*c, &l) && is_if_acmpne(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEif_acmpne(l2, NULL));
        }
        else if (is_ifnull(*c, &l) && is_ifnull(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEifnull(l2, NULL));
        }
        else if (is_ifnonnull(*c, &l) && is_ifnonnull(next(next(destination(l))), &l2) && l>l2) {
            droplabel(l);
            copylabel(l2);
            return replace(c, 1, makeCODEifnonnull(l2, NULL));
        }
    }
    return 0;
}



/* iconst_0         ...:x:0
 * iadd/isub        ...:x
 * --------->
 * nop              ...:x
 *
 *
 * iconst_1         ...:x:1
 * imul/idiv        ...:x
 * --------->
 * nop              ...:x
 *
 * iinc k 0
 * --------->
 * nop
 */
int remove_const_op(CODE **c)
{
    int x;
    int k;
    if (is_ldc_int(*c, &x) &&
        x==0 &&
        ( is_iadd(next(*c)) || is_isub(next(*c)) ) ) {
        return replace(c, 2, makeCODEnop(NULL));
    }
    else if (is_ldc_int(*c, &x) &&
             x==1 &&
             (is_imul(next(*c)) || is_idiv(next(*c)) ) ) {
        return replace(c, 2, makeCODEnop(NULL));
    }
    else if (is_iinc(*c, &k, &x) && x==0) {
        return replace(c, 1, makeCODEnop(NULL));
    }
    return 0;
}

/* ldc_int -1       ...:x:-1
 * imul/idiv        ...:-x
 * --------->
 * ineg             ...:-x
 */
int negate(CODE **c)
{
    int x;
    if (is_ldc_int(*c, &x) &&
        x==(-1) &&
        (is_imul(next(*c)) || is_idiv(next(*c)) ) ) {
        return replace(c, 2, makeCODEineg(NULL));
    }
    return 0;
}

/* ineg             ...:x:-y
 * iadd             ...:x-y
 * --------->
 * isub             ...:x-y
 *
 * ineg             ...:x:-y
 * isub             ...:x+y
 * --------->
 * iadd             ...:x+y
 */
int simplify_ineg_binop(CODE **c) {
    if (is_ineg(*c)) {
        if (is_iadd(next(*c))) {
            return replace(c, 2, makeCODEisub(NULL));
        }
        else if (is_isub(next(*c))) {
            return replace(c, 2, makeCODEiadd(NULL));
        }
    }
    return 0;
}

/* ldc_int x        ...:a:x
 * iadd/isub        ...:a+x
 * ldc_int y        ...:a+x:y
 * iadd/isub        ...:a+x+y
 * --------->
 * ldc_int op1(0,x)+op2(0,y)    ...:a:x+y
 * iadd                         ...:a+x+y
 */
int simplify_add_add(CODE **c) {
    int x, y;
    if (is_ldc_int(*c, &x) &&
        x>=-128 && x<=127 &&
        is_iadd(next(*c)) &&
        is_ldc_int(next(next(*c)), &y) &&
        y>=-128 && y<=127) {
        if (is_iadd(next(next(next(*c)))) && (x+y)>=-128 && (x+y)<=127) {
            return replace(c, 4, makeCODEldc_int(x+y, makeCODEiadd(NULL)));
        }
        else if (is_isub(next(next(next(*c)))) && (x-y)>=-128 && (x-y)<=127) {
            return replace(c, 4, makeCODEldc_int(x-y, makeCODEiadd(NULL)));
        }
    }
    else if (is_ldc_int(*c, &x) &&
             x>=-128 && x<=127 &&
             is_isub(next(*c)) &&
             is_ldc_int(next(next(*c)), &y) &&
             y>=-128 && y<=127) {
        if (is_iadd(next(next(next(*c)))) && (-x+y)>=-128 && (-x+y)<=127) {
            return replace(c, 4, makeCODEldc_int(-x+y, makeCODEiadd(NULL)));
        }
        else if (is_isub(next(next(next(*c)))) && (-x-y)>=-128 && (-x-y)<=127) {
            return replace(c, 4, makeCODEldc_int(-x-y, makeCODEiadd(NULL)));
        }
    }
    return 0;
}

/* ldc_int x        ...:a:x
 * imul             ...:a*x
 * ldc_int y        ...:a*x:y
 * imul             ...:a*x*y
 * --------->
 * ldc_int x*y      ...:a:x*y
 * imul             ...:a*x*y
 */
int simplify_mul_mul(CODE **c) {
    int x, y;
    if (is_ldc_int(*c, &x) &&
        x>=-128 && x<=127 &&
        is_imul(next(*c)) &&
        is_ldc_int(next(next(*c)), &y) &&
        y>=-128 && y<=127 &&
        is_imul(next(next(next(*c))))) {
        return replace(c, 4, makeCODEldc_int(x*y, makeCODEimul(NULL)));
    }
    return 0;
}

/* goto l
 * ...
 * l:
 * ireturn/areturn/return
 * --------->
 * ireturn/areturn/return
 * ...
 * l: (reference count reduced by 1)
 * ireturn/areturn/return
 */
int goto_return(CODE **c) {
    int l;
    if (is_goto(*c, &l)) {
        if (is_ireturn(next(destination(l)))) {
            droplabel(l);
            return replace(c, 1, makeCODEireturn(NULL));
        }
        else if (is_areturn(next(destination(l)))) {
            droplabel(l);
            return replace(c, 1, makeCODEareturn(NULL));
        }
        else if (is_return(next(destination(l)))) {
            droplabel(l);
            return replace(c, 1, makeCODEreturn(NULL));
        }
    }
    return 0;
}


/* goto l1
 * ...
 * l1:
 * l2:
 * --------->
 * goto l2
 * ...
 * l1: (reference count reduced by 1)
 * l2: (reference count increased by 1)
 */
int goto_empty_label(CODE **c) {
    int l1, l2;
    if (is_goto(*c, &l1) &&
        is_label(next(destination(l1)), &l2) && l1!=l2) {
        droplabel(l1);
        copylabel(l2);
        return replace(c, 1, makeCODEgoto(l2, NULL));
    }
    return 0;
}

/* goto l
 * l:
 * --------->
 * l: (reference count reduced by 1)
 */
int goto_next(CODE **c) {
    int l1, l2;
    if (is_goto(*c, &l1) &&
        is_label(next(*c), &l2) &&
        l1==l2) {
        kill_line(c);
    }
    return 0;
}

/* ifstmt l1
 * ...
 * l1:
 * l2:
 * --------->
 * ifstmt l2
 * ...
 * l1: (reference count reduced by 1)
 * l2: (reference count increased by 1)
 *
 *
 * ifstmt l1
 * ...
 * l1:
 * goto l2
 * ...
 * l2:
 * --------->
 * ifstmt l2
 * ...
 * l1: (reference count reduced by 1)
 * l2: (reference count increased by 1)
 */
int if_empty_label_or_goto (CODE **c) {
    int l1, l2;
    if (is_if(c, &l1) &&
        (is_label(next(destination(l1)), &l2) || is_goto(next(destination(l1)), &l2))
        && l1>l2) {
        droplabel(l1);
        copylabel(l2);
        if (is_ifeq(*c, &l1)) {
            return replace(c, 1, makeCODEifeq(l2, NULL));
        }
        else if (is_ifne(*c, &l1)) {
            return replace(c, 1, makeCODEifne(l2, NULL));
        }
        else if (is_if_acmpeq(*c, &l1)) {
            return replace(c, 1, makeCODEif_acmpeq(l2, NULL));
        }
        else if (is_if_acmpne(*c, &l1)) {
            return replace(c, 1, makeCODEif_acmpne(l2, NULL));
        }
        else if (is_ifnull(*c, &l1)) {
            return replace(c, 1, makeCODEifnull(l2, NULL));
        }
        else if (is_ifnonnull(*c, &l1)) {
            return replace(c, 1, makeCODEifnonnull(l2, NULL));
        }
        else if (is_if_icmpeq(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmpeq(l2, NULL));
        }
        else if (is_if_icmpne(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmpne(l2, NULL));
        }
        else if (is_if_icmpgt(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmpgt(l2, NULL));
        }
        else if (is_if_icmplt(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmplt(l2, NULL));
        }
        else if (is_if_icmple(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmple(l2, NULL));
        }
        else if (is_if_icmpge(*c, &l1)) {
            return replace(c, 1, makeCODEif_icmpge(l2, NULL));
        }
    }
    return 0;
}

/* ldc/iload/aload x        :x
 * dup                      :x:x
 * aload k                  :x:x:local[k]
 * swap                     :x:local[k]:x
 * putfield f sig           :x              local[k].f=x
 * pop                      :
 * --------->
 * aload k                  :local[k]
 * ldc/iload/aload x        :local[k]:x
 * putfield f sig           :               local[k].f=x
 */
int simplify_putfield1(CODE **c) {
    int k;
    int x;
    char *arg;
    char *arg2;
    if (*c!=NULL &&
        is_dup(next(*c)) &&
        is_aload(next(next(*c)), &k) &&
        is_swap(next(next(next(*c)))) &&
        is_putfield(next(next(next(next(*c)))), &arg) &&
        is_pop(next(next(next(next(next(*c))))))) {
        if (is_iload(*c, &x)) {
            return replace(c, 6, makeCODEaload(k, makeCODEiload(x, makeCODEputfield(arg, NULL))));
        }
        else if (is_aload(*c, &x)) {
            return replace(c, 6, makeCODEaload(k, makeCODEaload(x, makeCODEputfield(arg, NULL))));
        }
        else if (is_ldc_int(*c, &x)) {
            return replace(c, 6, makeCODEaload(k, makeCODEldc_int(x, makeCODEputfield(arg, NULL))));
        }
        else if (is_ldc_string(*c, &arg2)) {
            return replace(c, 6, makeCODEaload(k, makeCODEldc_string(arg2, makeCODEputfield(arg, NULL))));
        }
        else if (is_aconst_null(*c)) {
            return replace(c, 6, makeCODEaload(k, makeCODEaconst_null(makeCODEputfield(arg, NULL))));
        }
    }
    return 0;
}


/* dup                      :o:o
 * aload k                  :o:o:local[k]
 * swap                     :o:local[k]:o
 * putfield f sig           :o              local[k].f=o
 * pop                      :
 * --------->
 * aload k                  :o:local[k]
 * swap                     :local[k]:o     local[k].f=o
 * putfield f sig           :
 */
int simplify_putfield2(CODE **c) {
    int k;
    char *arg;
    if (is_dup(*c) &&
        is_aload(next(*c), &k) &&
        is_swap(next(next(*c))) &&
        is_putfield(next(next(next(*c))), &arg) &&
        is_pop(next(next(next(next(*c))))) ) {
        return replace(c, 5, makeCODEaload(k, makeCODEswap(makeCODEputfield(arg, NULL))));
    }
    return 0;
}


/* dup
 * if_icmpeq/if_acmpeq/if_icmple/if_icmpge l
 * --------->
 * goto l
 */
int simplify_dup_compare(CODE **c) {
    int l;
    if (is_dup(*c)) {
        if (is_if_icmpeq(next(*c), &l) ||
            is_if_acmpeq(next(*c), &l) ||
            is_if_icmpge(next(*c), &l) ||
            is_if_icmple(next(*c), &l)) {
            return replace(c, 2, makeCODEgoto(l, NULL));
        }
    }
    return 0;
}

/* iconst_1
 * dup
 * ifeq l
 * --------->
 * iconst_1
 *
 * l reference count decreased by 1
 *
 *
 * iconst_0
 * dup
 * ifeq l
 * --------->
 * iconst_0
 * goto l
 */
int simplify_iload_dup_compare(CODE **c) {
    int x, l;
    if (is_ldc_int(*c, &x) &&
        x>=-128 && x<=127 &&
        is_dup(next(*c))) {
        if (is_ifeq(next(next(*c)), &l)) {
            if (x!=0) {
                return replace_modified(c, 3, makeCODEldc_int(x, NULL));
            }
            else {
                return replace(c, 3, makeCODEldc_int(0, makeCODEgoto(l, NULL)));
            }
        }
        else if (is_ifne(next(next(*c)), &l)) {
            if (x!=0) {
                return replace(c, 3, makeCODEldc_int(x, makeCODEgoto(l, NULL)));
            }
            else {
                return replace_modified(c, 3, makeCODEldc_int(0, NULL));
            }
        }
    }
    return 0;
}

/* ldc x (x nonzero)
 * ifne l
 * --------->
 * goto l
 *
 *
 * iconst_0
 * ifeq l
 * --------->
 * goto l
 *
 *
 * ldc x
 * ldc y (y!=x)
 * if_icmpne/if_icmplt/if_icmpgt l
 * --------->
 * goto l
 */
int simplify_iload_compare(CODE **c) {
    int x, y;
    int l;
    if (is_ldc_int(*c, &x) &&
        x!=0 && x>=-128 && x<=127 &&
        is_ifne(next(*c), &l)) {
        return replace(c, 2, makeCODEgoto(l, NULL));
    }
    else if (is_ldc_int(*c, &x) &&
             x==0 &&
             is_ifeq(next(*c), &l)) {
        return replace(c, 2, makeCODEgoto(l, NULL));
    }
    else if (is_ldc_int(*c, &x) &&
             x>=-128 && x<=127 &&
             is_ldc_int(next(*c), &y) &&
             y>=-128 && y<=127 &&
             y!=x) {
        if (is_if_icmpne(next(next(*c)), &l) ||
            is_if_icmplt(next(next(*c)), &l) ||
            is_if_icmpgt(next(next(*c)), &l))
        return replace(c, 3, makeCODEgoto(l, NULL));
    }
    return 0;
}

/* iconst_0
 * goto l
 * iconst_1
 * l:
 * dup
 * ifne l2
 * pop
 * -------->
 * iconst_0
 *
 * The top of the stack is 0 so the instructions from after "goto l" to "pop" are unnecessary.
 */
int simplify_ifne(CODE **c) {
    int x, l, l1, l2;
    if (is_ldc_int(*c, &x) &&
        x==0 &&
        is_goto(next(*c), &l) &&
        is_label(next(next(next(*c))), &l1) &&
        l1==l &&
        is_dup(next(next(next(next(*c))))) &&
        is_ifne(next(next(next(next(next(*c))))), &l2) &&
        is_pop(next(next(next(next(next(next(*c)))))))) {
        return replace_modified(c, 7, makeCODEldc_int(x, NULL));
    }
    return 0;
}



/* deadlabel:
 * --------->
 * nop
 *
 * Replace with nop instead of NULL to ensure method won't end with an empty label.
 */
int replace_deadlabel(CODE **c)
{
    int l;
    if (is_label(*c, &l) && deadlabel(l)) {
        return replace(c, 1, makeCODEnop(NULL));
    }
    return 0;
}


/* nop
 * instruction
 * --------->
 * instruction
 */
int remove_nop(CODE **c)
{
    if (is_nop(*c) && next(*c)!=NULL) {
        kill_line(c);
    }
    
    return 0;
}




/********************** end of patterns **********************/


#define OPTS 40


OPTI optimization[OPTS] = {
    simplify_multiplication_right,
    simplify_astore,
    positive_increment,
    simplify_goto_goto,
    simplify_istore,
    decrement,
    simplify_store_load,
    simplify_store_store,
    simplify_load_load,
    remove_load_store,
    remove_load_pop,
    remove_dup_pop,
    remove_dup_swap,
    remove_swap_swap,
    swap_loads,
    simplify_return,
    simplify_ldc_ldc_op,
    simplify_iinc_iinc,
    simplify_if_cmp,
    simplify_ifnull,
    goto_dup_ifeq,
    goto_ifeq,
    skip_label_dup,
    remove_const_op,
    negate,
    simplify_ineg_binop,
    simplify_add_add,
    simplify_mul_mul,
    goto_return,
    goto_empty_label,
    goto_next,
    if_empty_label_or_goto,
    simplify_putfield1,
    simplify_putfield2,
    simplify_dup_compare,
    simplify_iload_dup_compare,
    simplify_iload_compare,
    simplify_ifne,
    replace_deadlabel,
    remove_nop};




