package golite.codegen;

import golite.symbol.*;
import golite.weeder.*;
import golite.parser.*;
import golite.lexer.*;
import golite.node.*;
import golite.analysis.*;
import java.util.*;

public class CodeGen extends DepthFirstAdapter {
    
    public class CodeGenException extends RuntimeException {
       	public CodeGenException(java.lang.String message) {
            super(message);
        }
    }
    
    private static HashMap<Node,Type> typemap = new HashMap<Node,Type>();
    private static HashMap<Node, List<Node>> shortDeclInfo = new HashMap<Node, List<Node>>();
    private static List<String> names;
    private static HashMap<String, Integer> slicemap;
    private int tabCount = 0;
    private boolean noNewline = false;
    private boolean noNewBraces = false;
    private boolean printStmt = false;
    private boolean stmt2Exp = false;
    boolean noComma = true;
    
    private static HashMap<Node, Node> globals = new HashMap<Node, Node>();
    
    private boolean topLevel = true;
    
    // name of function for concatenating strings in C
    String concatFuncName = "concat";
    // name of function for bounds checking in C
    String boundsFuncName = "bounds_check";
    // name of function for checking if two arrays are equal in C
    String equalArraysFuncName = "eq_array";
    
    public static void print(Node node)
    {
        SymbolTable symTab = new SymbolTable();
        symTab.typeCheck(node);
        typemap = symTab.getTypemap();
        shortDeclInfo = symTab.getShortDeclInfo();
        names = symTab.getNames();
        slicemap = symTab.getSlicemap();
        // System.err.println(names.toString());
        node.apply(new CodeGen());
    }
    
    /* --------------HELPER METHODS-------------- */
    
    private void puts(String s) {
        System.out.print(s);
        System.out.flush();
    }
    
    private void putLine(String s) {
        tab();
        System.out.println(s);
        System.out.flush();
    }
    
    private void newline()
    {
        if (!noNewline) {
            puts(";\n");
        }
    }
    
    // prints comma-separated list
    private void printList(List<Node> list)
    {
        Iterator<Node> itr = list.iterator();
        if (itr.hasNext()) {
            Node next = itr.next();
            next.apply(this);
        }
        while (itr.hasNext()) {
            Node next = itr.next();
            puts(", ");
            next.apply(this);
        }
    }
    
    private void printType(Type t) {
        if (t instanceof IntType) {
            puts("int");
        }
        if (t instanceof Float64Type) {
            puts("double");
        }
        if (t instanceof BooleanType) {
            puts("bool");
        }
        if (t instanceof RuneType) {
            puts("char");
        }
        if (t instanceof StringType) {
            puts("char*");
        }
        if (t instanceof StructType) {
            StructType t2 = (StructType) t;
            puts(t2.getStructName());
        }
        if (t instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) t;
            Type t2 = arrayType.getType();
            printType(t2);
        }
        if (t instanceof SliceType) {
            puts("slice");
        }
        if (t instanceof AliasType) {
            puts(((AliasType)t).getAliasName());
        }
    }
    
    private void printReturnType(Type t) {
        if (t==null) {
            puts("void");
        }
        if (t instanceof IntType || t instanceof Float64Type
            || t instanceof BooleanType || t instanceof RuneType) {
            printType(t);
        }
        if (t instanceof StringType) {
            puts("char*");
        }
        if (t instanceof StructType) {
            StructType t2 = (StructType) t;
            puts(t2.getStructName());
        }
        if (t instanceof ArrayType) {
            printReturnType(((ArrayType)t).getType());
            puts("*");
        }
        if (t instanceof SliceType) {
            puts("slice");
        }
        if (t instanceof AliasType) {
            puts(((AliasType)t).getAliasName());
        }
    }
    
    // put in printf format
    private void printFormat(Type t) {
        if (t instanceof IntType) {
            puts("%d");
        }
        if (t instanceof Float64Type) {
            puts("%f");
        }
        if (t instanceof BooleanType) {
            puts("%s");
        }
        if (t instanceof RuneType) {
            puts("%c");
        }
        if (t instanceof StringType) {
            puts("%s");
        }
        if (t instanceof AliasType) {
            printFormat(removeAlias(t));
        }
    }
    
    
    // initialize to zero
    private void initialize(Type t) {
        if (t instanceof IntType || t instanceof Float64Type || t instanceof RuneType || t instanceof BooleanType) {
            puts("0");
        }
        if (t instanceof StringType) {
            puts("\"\"");
        }
        if (t instanceof ArrayType) {
            puts("{");
            initialize(((ArrayType)t).getType());
            puts("}");
        }
        if (t instanceof StructType) {
            puts("{0}");
        }
        if (t instanceof SliceType) {
            puts("{0}");
        }
    }
    
    private void printBinaryExp(Node left, String op, Node right)
    {
        puts("(");
        left.apply(this);
        puts(op);
        right.apply(this);
        puts(")");
    }
    
    private void tab()
    {
        for (int i=0; i<tabCount; i++)  {
            puts("\t");
        }
    }
    
    /* returns the underlying type of an alias type */
    public Type removeAlias(Type t){
        while(t instanceof AliasType){
            AliasType aliast = (AliasType) t;
            t = aliast.getType();
        }
        return t;
    }
    
    // generate new variable name that isn't used in program
    public String newName(String id) {
        if (!names.contains(id)) {
            return id;
        }
        else {
            int num = 0;
            while (names.contains(id + num)) {
                num++;
            }
            return (id + num);
        }
    }
    
    
    // a [10][20]int --> {20, 10}
    public List<String> getDimensions(ArrayType t) {
        List<String> dimensions = new ArrayList<String>();
        dimensions.add(t.getSize());
        Type t2 = t.getType();
        while (t2 instanceof ArrayType) {
            ArrayType t2Array = (ArrayType) t2;
            dimensions.add(t2Array.getSize());
            t2 = t2Array.getType();
        }
        Collections.reverse(dimensions);
        return dimensions;
    }
    
    // prints dimensions list "{20, 10}"
    public void printDimensions(List<String> dimensions) {
        puts("{");
        if (dimensions.size()>0) {
            Iterator<String> itr = dimensions.iterator();
            if (itr.hasNext()) {
                puts(itr.next());
            }
            while (itr.hasNext()) {
                puts(",");
                puts(itr.next());
            }
        }
        puts("}");
    }
    
    public Type getUnderlyingType(ArrayType type) {
        Type nextType = type.getType();
        while(nextType instanceof ArrayType) {
            nextType = ((ArrayType)nextType).getType();
        }
        return nextType;
    }
    
    // initialize slice
    public void printSliceInit(TId id, Type t) {
        
        if (!topLevel) {
            puts(id.getText());
            puts(".curSize = 0; ");
            if (slicemap.containsKey(id.getText())) {
                Integer s = slicemap.get(id.getText());
                SliceType tSlice = (SliceType) t;
                Type t2 = removeAlias(tSlice.getType());
                Type t3 = tSlice.getType();
                int total = 1;
                while (t2 instanceof ArrayType || t2 instanceof SliceType) {
                    if (t2 instanceof ArrayType) {
                        String size = ((ArrayType)t2).getSize();
                        int sizeInt = Integer.parseInt(size);
                        total *= sizeInt;
                        t2 = removeAlias(((ArrayType)t2).getType());
                    }
                    else {
                        total *= s;
                        t2 = removeAlias(((SliceType)t2).getType());
                    }
                }
                puts(id.getText());
                puts(".data = malloc(");
                puts(Integer.toString(total));
                if (! (t2 instanceof StringType)) {
                    puts("*1024*sizeof(");
                    printType(t3);
                }
                else {
                    puts("*256*sizeof(char");
                }
                puts(")); ");
                puts(id.getText());
                puts(".maxSize = " + s + "; ");
            }
        }
        
    }
    
    
    /* --------------STATEMENTS AND TYPES-------------- */
    
    public void inAProgram(AProgram node) {
        puts("#include <stdio.h>\n");
        puts("#include <stdlib.h>\n");
        puts("#include <string.h>\n");
        puts("#include <stdbool.h>\n\n");
        
        // function for concatenating strings
        /*
         char* concat(char *s1, char *s2) {
         int len = strlen(s1) + strlen(s2) + 1;
         char *s = malloc(len);
         strcpy(s, s1);
         strcat(s, s2);
         return s;
         }
         */
        puts("char* " + concatFuncName + "(char *s1, char *s2) {\n");
        tabCount++;
        tab();
        puts("int len = strlen(s1) + strlen(s2) + 1;\n");
        tab();
        puts("char *s = malloc(len);\n");
        tab();
        puts("strcpy(s, s1);\n");
        tab();
        puts("strcat(s, s2);\n");
        tab();
        puts("return s;\n");
        tabCount--;
        puts("}\n\n");
        
        
        // a[i] --> a[bounds_check(i)]
        puts("int " + boundsFuncName + "(int index, int len) {\n");
        tabCount++;
        tab();
        puts("if (index<0 || index>=len) {\n");
        tabCount++;
        tab();
        puts("printf(\"Index out of bounds.\\n\"); exit(EXIT_FAILURE);\n");
        tabCount--;
        tab();
        puts("}\n");
        tab();
        puts("return index;\n");
        tabCount--;
        puts("}\n\n");
        
        // struct for slices
        // max size of data is set when slice is first declared
        // (based on number of appends applied during program)
        puts("typedef struct { int curSize; int maxSize; void *data; } slice;\n\n");
        
        /*
         // Function for comparing arrays
         //This doesn't actually work... it generates segfaults.
         //I think it is because we can't convert back from void* to type** (double pointer)
         int eq_array(void* array1, void* array2, char* type, int* dimensions, int index) {
         int i;
         if (index == 0) {
         for (i=0; i<dimensions[index]; i++) {
         if (strcmp(type, "string") == 0) {
         if (strcmp(((char**)array1)[i], ((char**)array2[i])) != 0)  return false;
         }
         else if (strcmp(type, "struct") == 0) {
         //TODO
         //how do we compare two structs... do we have to pass this information in advance
         //to this eq_array function and call eq_struct with it?
         }
         else if (strcmp(type, "int") == 0) {
         if (memcmp(array1, array2, dimensions[index] * sizeof(int)) != 0) return false;
         } else if (strcmp(type, "float") == 0) {
         if (memcmp(array1, array2, dimensions[index] * sizeof(float)) != 0) return false;
         } else if (strcmp(type, "rune") == 0) {
         if (memcmp(array1, array2, dimensions[index] * sizeof(char)) != 0) return false;
         } else if (strcmp(type, "boolean") == 0) {
         if (memcmp(array1, array2, dimensions[index] * sizeof(bool)) != 0) return false;
         }
         }
         } else {
         for (i=0; i<dimensions[index]; i++) {
         if (eq_array(((char*)array1[i], ((char*)array2[i]), type, dimensions, index--) != 0) return false;
         }
         }
         return true;
         }
         */
        
        puts ("int " + equalArraysFuncName + "(void* array1, void* array2, char* type, int* dimensions, int index) {\n");
        tabCount++;
        putLine("int i;");
        putLine("if (index == 0) {");
        tabCount++;
        putLine("for (i=0; i<dimensions[index]; i++) {");
        tabCount++;
        putLine("if (strcmp(type, \"string\") == 0) {");
        tabCount++;
        putLine("if (strcmp(((char**)array1)[i], ((char**)array2)[i]) != 0)  return false;");
        tabCount--;
        putLine("} else if (strcmp(type, \"struct\") == 0) {");
        tabCount++;
        //TODO
        //Deal with struct
        tabCount--;
        putLine("} else if (strcmp(type, \"int\") == 0) {");
        tabCount++;
        putLine("if (memcmp(array1, array2, dimensions[index] * sizeof(int)) != 0) return false;");
        tabCount--;
        putLine("} else if (strcmp(type, \"float\") == 0) {");
        tabCount++;
        putLine("if (memcmp(array1, array2, dimensions[index] * sizeof(float)) != 0) return false;");
        tabCount--;
        putLine("} else if (strcmp(type, \"rune\") == 0) {");
        tabCount++;
        putLine("if (memcmp(array1, array2, dimensions[index] * sizeof(char)) != 0) return false;");
        tabCount--;
        putLine("} else if (strcmp(type, \"boolean\") == 0) {");
        tabCount++;
        putLine("if (memcmp(array1, array2, dimensions[index] * sizeof(bool)) != 0) return false;");
        tabCount--;
        putLine("} else {");
        tabCount++;
        putLine("if (memcmp(array1, array2, dimensions[index] * sizeof(type)) != 0) return false;");
        tabCount--;
        putLine("}");
        tabCount--;
        putLine("}");
        tabCount--;
        putLine("} else {");
        /*
        tabCount++;
        putLine("for (i=0; i<dimensions[index]; i++) {");
        tabCount++;
        putLine("if (eq_array(((char**)array1)[i], ((char**)array2)[i], type, dimensions, index-1) != 0) return false;");
        tabCount--;
        putLine("}");
        tabCount--;
         */
        putLine("}");
        putLine("return true;");
        tabCount--;
        putLine("}");
        puts("\n");
        
    }
    
    /* var declaration */
    public void caseAVarDecStmt(AVarDecStmt node)
    {
        List<Node> idList = new ArrayList<Node>(node.getId());
        List<Node> expList = new ArrayList<Node>(node.getVal());
        Iterator<Node> itr1 = idList.iterator();
        Iterator<Node> itr2 = expList.iterator();
        Node type = node.getType();
        
        boolean first = true;
        
        if(type != null)
        {
            // "var idlist type" --> "type id array_size?;" for each id in idlist
            if (expList.isEmpty()) {
                
                while (itr1.hasNext()) {
                    Node id = itr1.next();
                    if (id instanceof ABlankExp) {
                        puts("\n");
                        continue;
                    }
                    if (!first) {
                        tab();
                    }
                    type.apply(this);
                    puts(" ");
                    id.apply(this);
                    Type tOrig = typemap.get(id);
                    Type t = removeAlias(tOrig);
                    while (t instanceof ArrayType) {
                        puts("[");
                        puts(((ArrayType)t).getSize());
                        puts("]");
                        t = removeAlias(((ArrayType)t).getType());
                    }
                    puts(" = ");
                    initialize(removeAlias(tOrig));
                    puts(";\n");
                    if (first) {
                        first = false;
                    }
                    if (!topLevel && (removeAlias(tOrig) instanceof SliceType)) {
                        if (id instanceof AIdExp) {
                            AIdExp idExp = (AIdExp) id;
                            printSliceInit(idExp.getId(), tOrig);
                        }
                    }
                    
                }
                
            }
            
            // "var idlist type = explist" --> "type id array_size? = exp;" for each id in idlist
            else {
                while (itr1.hasNext()) {
                    Node id = itr1.next();
                    Node exp = itr2.next();
                    if (id instanceof ABlankExp) {
                        puts("\n");
                        continue;
                    }
                    if (!first) {
                        tab();
                    }
                    if (topLevel) {
                        globals.put(id, exp);
                    }
                    Type t1 = typemap.get(id);
                    Type t = removeAlias(t1);
                    if (t instanceof ArrayType)  {
                        // int a[10] = {0}; int b[10] = a;
                        // memcpy (b, a, sizeof(a))
                        type.apply(this);
                        puts(" ");
                        id.apply(this);
                        while (t instanceof ArrayType) {
                            puts("[");
                            puts(((ArrayType)t).getSize());
                            puts("]");
                            t = removeAlias(((ArrayType)t).getType());
                        }
                        puts("; ");
                        if (!topLevel) {
                            puts("memcpy(");
                            id.apply(this);
                            puts(", ");
                            exp.apply(this);
                            puts(", ");
                            puts("sizeof(");
                            exp.apply(this);
                            puts("))");
                            puts(";\n");
                        }
                    }
                    else if (t instanceof SliceType) {
                        puts("slice ");
                        id.apply(this);
                        puts(";");
                        if (id instanceof AIdExp) {
                            printSliceInit(((AIdExp)id).getId(), t);
                        }
                        if (!topLevel) {
                            if (! (exp instanceof AAppendExp)) {
                                id.apply(this);
                                puts(" = ");
                                exp.apply(this);
                                puts(";\n");
                            }
                            else {
                                exp.apply(this);
                            }
                        }
                    }
                    else {
                        type.apply(this);
                        puts(" ");
                        id.apply(this);
                        while (t instanceof ArrayType) {
                            puts("[");
                            puts(((ArrayType)t).getSize());
                            puts("]");
                            t = removeAlias(((ArrayType)t).getType());
                        }
                        if (!topLevel) {
                            puts(" = ");
                            exp.apply(this);
                        }
                        puts(";\n");
                    }
                    if (first) {
                        first = false;
                    }
                }
            }
        }
        
        // "var idlist = explist" --> "type id array_size? = exp;" for each id in idlist
        else {
            while (itr1.hasNext()) {
                Node id = itr1.next();
                Node exp = itr2.next();
                if (id instanceof ABlankExp) {
                    puts("\n");
                    continue;
                }
                if (!first) {
                    tab();
                }
                if (topLevel) {
                    globals.put(id, exp);
                }
                Type expType1 = typemap.get(exp);
                Type expType = removeAlias(expType1);
                
                if (expType instanceof ArrayType)  {
                    printType(expType1);
                    puts(" ");
                    id.apply(this);
                    while (expType instanceof ArrayType) {
                        puts("[");
                        puts(((ArrayType)expType).getSize());
                        puts("]");
                        expType = removeAlias(((ArrayType)expType).getType());
                    }
                    puts("; ");
                    if (!topLevel) {
                        puts("memcpy(");
                        id.apply(this);
                        puts(", ");
                        exp.apply(this);
                        puts(", ");
                        puts("sizeof(");
                        exp.apply(this);
                        puts("))");
                        puts(";\n");
                    }
                }
                else if (expType instanceof SliceType) {
                    puts("slice ");
                    id.apply(this);
                    puts(";");
                    if (id instanceof AIdExp) {
                        printSliceInit(((AIdExp)id).getId(), expType1);
                    }
                    if (!topLevel) {
                        if (! (exp instanceof AAppendExp)) {
                            id.apply(this);
                            puts(" = ");
                            exp.apply(this);
                            puts(";\n");
                        }
                        else {
                            exp.apply(this);
                        }
                    }
                }
                
                else {
                    printType(expType1);
                    puts(" ");
                    id.apply(this);
                    Type t = removeAlias(typemap.get(id));
                    while (t instanceof ArrayType) {
                        puts("[");
                        puts(((ArrayType)t).getSize());
                        puts("]");
                        t = removeAlias(((ArrayType)t).getType());
                    }
                    if (!topLevel) {
                        puts(" = ");
                        exp.apply(this);
                    }
                    puts(";\n");
                    
                }
                if (first) {
                    first = false;
                }
            }
        }
    }
    
    /* type declaration */
    public void caseATypeDecStmt(ATypeDecStmt node)
    {
        PExp name = node.getExp();
        PType typeNode = node.getType();
        if (name instanceof AIdExp) {
            String typeDecName = ((AIdExp)name).getId().getText();
            puts("typedef ");
            typeNode.apply(this);
            puts(" " + typeDecName + ";\n");
        }
        
    }
    
    /* func declaration */
    public void caseAFuncDecStmt(AFuncDecStmt node)
    {
        topLevel = false;
        boolean isMain = false;
        
        // "func name (params) return_type { body }" --> "return_type name (params) { body }"
        Node name = node.getExp();
        if (name instanceof AIdExp) {
            AIdExp funcName = (AIdExp) name;
            String funcNameStr = funcName.getId().getText();
            List<PIdsType> params = new ArrayList<PIdsType>(node.getIdsType());
            Type type = typemap.get(funcName);
            if (type instanceof FunctionType) {
                FunctionType funcType = (FunctionType) type;
                Type returnType = funcType.getReturnType();
                if (funcNameStr.equals("main")) {
                    isMain = true;
                    puts("int");
                }
                else {
                    printReturnType(returnType);
                }
                puts(" ");
                puts(funcNameStr);
                puts(" (");
                noComma = true;
                for (PIdsType e: params) {
                    e.apply(this);
                }
                noNewBraces = true;
                puts(") {\n");
                List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
                if (isMain) {
                    for (Map.Entry<Node, Node> entry : globals.entrySet()) {
                        Node id = entry.getKey();
                        Node exp = entry.getValue();
                        if (!(exp instanceof AAppendExp)) {
                            id.apply(this);
                            puts(" = ");
                            exp.apply(this);
                            puts(";\n");
                        }
                        else {
                            exp.apply(this);
                        }
                    }
                }
                for(PStmt e : body)
                {
                    e.apply(this);
                }
                if (isMain) {
                    tabCount++;
                    tab();
                    puts("return 0;\n");
                    tabCount--;
                }
                puts("}\n\n");
                noNewBraces = false;
            }
            else {
                int line = golite.weeder.LineNumber.getLineNumber(node);
                throw new CodeGenException("[" + line + "] Error in typemap.");
            }
            
        }
        
        topLevel = true;
        
    }
    
    /* group of func params */
    public void caseAIdsType(AIdsType node)
    {
        // "id_1,...,id_n type" --> "type id_1, ..., type id_n"
        
        Node type = node.getType();
        
        List<Node> idList = new ArrayList<Node>(node.getExp());
        Iterator<Node> itr = idList.iterator();
        
        if (itr.hasNext()) {
            if (noComma) {
                noComma = false;
            }
            else {
                puts(", ");
            }
            Node id = itr.next();
            type.apply(this);
            puts(" ");
            id.apply(this);
            Type t = removeAlias(typemap.get(id));
            while (t instanceof ArrayType) {
                puts("[");
                puts(((ArrayType)t).getSize());
                puts("]");
                t = removeAlias(((ArrayType)t).getType());
            }
        }
        
        while (itr.hasNext()) {
            puts(", ");
            Node id = itr.next();
            type.apply(this);
            puts(" ");
            id.apply(this);
            Type t = removeAlias(typemap.get(id));
            while (t instanceof ArrayType) {
                puts("[");
                puts(((ArrayType)t).getSize());
                puts("]");
                t = removeAlias(((ArrayType)t).getType());
            }
        }
        
    }
    
    /* exp statement */
    public void caseAExpStmt(AExpStmt node)
    {
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
            puts(";\n");
        }
    }
    
    /* assign_eq statement */
    public void caseAAssignEqStmt(AAssignEqStmt node)
    {
        // TODO: check assignment for arrays/slices/strings
        
        List<Node> idList = new ArrayList<Node>(node.getL());
        List<Node> expList = new ArrayList<Node>(node.getR());
        Iterator<Node> itr1 = idList.iterator();
        Iterator<Node> itr2 = expList.iterator();
        
        // turn statement into expression for if expression
        // "a, b, c = e1, e2, e3" --> "(a=e1) && (b=e2) && (c=e3)"
        if (stmt2Exp) {
            if (itr1.hasNext()) {
                Node id = itr1.next();
                Node exp = itr2.next();
                if (!(id instanceof ABlankExp)) {
                    puts("(");
                    id.apply(this);
                    puts(" = ");
                    exp.apply(this);
                    puts(")");
                }
            }
            while (itr1.hasNext()) {
                Node id = itr1.next();
                Node exp = itr2.next();
                if (id instanceof ABlankExp) {
                    continue;
                }
                puts("&& (");
                id.apply(this);
                puts(" = ");
                exp.apply(this);
                puts(")");
            }
        }
        
        else {
            boolean first = true;
            while (itr1.hasNext()) {
                Node id = itr1.next();
                Node exp = itr2.next();
                if (id instanceof ABlankExp) {
                    puts("\n");
                    continue;
                }
                if (!first) {
                    tab();
                }
                if (!(exp instanceof AAppendExp)) {
                    id.apply(this);
                    puts(" = ");
                    exp.apply(this);
                    puts(";\n");
                }
                else {
                    exp.apply(this);
                }
                if (first) {
                    first = false;
                }
            }
        }
    }
    
    /* plus_eq statement */
    public void caseAPlusEqStmt(APlusEqStmt node)
    {
        PExp leftNode = node.getL();
        PExp rightNode = node.getR();
        Type t = removeAlias(typemap.get(leftNode));
        if (!(t instanceof StringType)) {
            leftNode.apply(this);
            puts(" += ");
            rightNode.apply(this);
            newline();
        }
        else {
            leftNode.apply(this);
            puts(" = " + concatFuncName + "(");
            leftNode.apply(this);
            puts(",");
            rightNode.apply(this);
            puts(")");
            newline();
        }
    }
    
    /* minus_eq statement */
    public void caseAMinusEqStmt(AMinusEqStmt node)
    {
        node.getL().apply(this);
        puts(" -= ");
        node.getR().apply(this);
        newline();
    }
    
    /* star_eq statement */
    public void caseAStarEqStmt(AStarEqStmt node)
    {
        node.getL().apply(this);
        puts(" *= ");
        node.getR().apply(this);
        newline();
    }
    
    /* slash_eq statement */
    public void caseASlashEqStmt(ASlashEqStmt node)
    {
        node.getL().apply(this);
        puts(" /= ");
        node.getR().apply(this);
        newline();
    }
    
    /* mod_eq statement */
    public void caseAModEqStmt(AModEqStmt node)
    {
        node.getL().apply(this);
        puts(" %= ");
        node.getR().apply(this);
        newline();
    }
    
    /* amp_eq statement */
    public void caseAAmpEqStmt(AAmpEqStmt node)
    {
        node.getL().apply(this);
        puts(" &= ");
        node.getR().apply(this);
        newline();
    }
    
    /* l_shift_eq statement */
    public void caseALShiftEqStmt(ALShiftEqStmt node)
    {
        node.getL().apply(this);
        puts(" <<= ");
        node.getR().apply(this);
        newline();
    }
    
    /* r_shift_eq statement */
    public void caseARShiftEqStmt(ARShiftEqStmt node)
    {
        node.getL().apply(this);
        puts(" >>= ");
        node.getR().apply(this);
        newline();
    }
    
    /* amp_caret_eq statement */
    public void caseAAmpCaretEqStmt(AAmpCaretEqStmt node)
    {
        node.getL().apply(this);
        puts(" &^= ");
        node.getR().apply(this);
        newline();
    }
    
    /* pipe_eq statement */
    public void caseAPipeEqStmt(APipeEqStmt node)
    {
        node.getL().apply(this);
        puts(" |= ");
        node.getR().apply(this);
        newline();
    }
    
    /* caret_eq statement */
    public void caseACaretEqStmt(ACaretEqStmt node)
    {
        node.getL().apply(this);
        puts(" ^= ");
        node.getR().apply(this);
        newline();
    }
    
    /* short var declaration */
    public void caseAShortDeclStmt(AShortDeclStmt node)
    {
        // "idlist := explist" -->
        // [case 1] "type id array_size? = exp;" for each id in idlist not previously declared in current scope
        // [case 2] "id = exp;" for each id in idlist already declared in current scope
        
        List<Node> newVars = shortDeclInfo.get(node);
        
        List<Node> idList = new ArrayList<Node>(node.getL());
        List<Node> expList = new ArrayList<Node>(node.getR());
        Iterator<Node> itr1 = idList.iterator();
        Iterator<Node> itr2 = expList.iterator();
        boolean first = true;
        while (itr1.hasNext()) {
            Node id = itr1.next();
            Node exp = itr2.next();
            if (id instanceof ABlankExp) {
                puts("\n");
                continue;
            }
            // newly declared variable
            if (newVars.contains(id)) {
                if (!first) {
                    tab();
                }
                Type expType = typemap.get(exp);
                printType(expType);
                puts(" ");
                id.apply(this);
                puts(" = ");
                exp.apply(this);
                newline();
                if (first) {
                    first = false;
                }
            }
            // already declared variable
            else {
                if (!first) {
                    tab();
                }
                if (exp instanceof AAppendExp) {
                    exp.apply(this);
                }
                else {
                    id.apply(this);
                    puts(" = ");
                    exp.apply(this);
                    newline();
                    if (first) {
                        first = false;
                    }
                }
            }
        }
        
        
        
    }
    
    /* increment */
    public void caseAIncStmt(AIncStmt node)
    {
        node.getExp().apply(this);
        puts("++");
        newline();
    }
    
    /* decrement */
    public void caseADecStmt(ADecStmt node)
    {
        node.getExp().apply(this);
        puts("--");
        newline();
    }
    
    /* print */
    public void caseAPrintStmt(APrintStmt node)
    {
        printStmt = true;
        boolean first = true;
        List<Node> expList = new ArrayList<Node>(node.getExp());
        for (Node e: expList) {
            if (!first) {
                tab();
            }
            puts("printf(\"");
            Type t = typemap.get(e);
            printFormat(t);
            puts("\"");
            puts(", ");
            e.apply(this);
            puts(");\n");
            if (first) {
                first = false;
            }
        }
        printStmt = false;
    }
    
    /* println */
    public void caseAPrintlnStmt(APrintlnStmt node)
    {
        printStmt = true;
        boolean first = true;
        List<Node> expList = new ArrayList<Node>(node.getExp());
        Iterator<Node> itr = expList.iterator();
        if (expList.isEmpty()) {
            puts("printf(\"\\");
            puts("n\");\n");
        }
        while (itr.hasNext()) {
            Node e = itr.next();
            if (!first) {
                tab();
            }
            puts("printf(\"");
            Type t = typemap.get(e);
            printFormat(t);
            
            if (itr.hasNext()) {
                puts(" \"");
            }
            else {
                puts("\\");
                puts("n\"");
            }
            puts(", ");
            e.apply(this);
            puts(");\n");
            if (first) {
                first = false;
            }
        }
        printStmt = false;
    }
    
    /* return */
    public void caseAReturnStmt(AReturnStmt node)
    {
        if(node.getExp() != null)
        {
            puts("return ");
            node.getExp().apply(this);
            puts(";\n");
        }
        else {
            puts("return;");
        }
    }
    
    /* if statement */
    public void caseAIfStmt(AIfStmt node)
    {
        boolean hasInit = false;
        PExp exp = node.getExp();
        PStmt shortStmt = node.getStmt();
        List<PStmt> trueStmts = node.getTrue();
        List<PElseIf> elseIfBlocks = node.getElseIf();
        List<PStmt> falseStmts = node.getFalse();
        
        // check if any else_if init introduces new vars
        boolean newVars = false;;
        for (PElseIf elseIfBlock: elseIfBlocks) {
            AElseIf e = (AElseIf) elseIfBlock;
            if (e.getStmt() instanceof AShortDeclStmt) {
                newVars = true;
                break;
            }
        }
        
        //write simple expression before if statement
        
        /* if init_stmt exp {
         body
         }
         
         -->
         
         {
         init_stmt;
         if (exp) {
         body
         }
         }
         */
        
        // extra braces because body can shadow variables in init_stmt
        
        if (shortStmt != null) {
            hasInit = true;
            puts("{\n");
            tabCount++;
            tab();
            shortStmt.apply(this);
        }
        //write if and condition
        if (hasInit) {
            tab();
        }
        puts("if (");
        exp.apply(this);
        puts(")\n");
        tab();
        for (PStmt stmt: trueStmts) {
            stmt.apply(this);
        }
        // case where init stmt doesn't introduce new var is handled in caseAElseIf
        if (!newVars) {
            for (PElseIf elseIfBlock: elseIfBlocks) {
                tab();
                elseIfBlock.apply(this);
            }
            if (!falseStmts.isEmpty()) {
                tab();
                puts("else\n");
                for (PStmt stmt: falseStmts) {
                    tab();
                    stmt.apply(this);
                }
            }
        }
        // TODO: otherwise turn elif chain into series of ifs
        else {
            String entered = newName("entered");
            puts("bool " + entered + " = false;\n");
            Iterator<PElseIf> itr = elseIfBlocks.iterator();
            int stmts = 0;
            while (itr.hasNext()) {
                PElseIf elif = itr.next();
                AElseIf e = (AElseIf) elif;
                if (e.getStmt()!=null) {
                    stmts++;
                    puts("{\n");
                    tab();
                    e.getStmt().apply(this);
                    puts("if(!" + entered + " && ");
                    e.getExp().apply(this);
                    puts(") {\n");
                    tab();
                    List<PStmt> body = e.getBody();
                    puts(entered + "= true;\n");
                    noNewBraces = true;
                    for (PStmt s: body) {
                        s.apply(this);
                    }
                    noNewBraces = false;
                    puts("}");
                }
                
            }
            if (!falseStmts.isEmpty()) {
                tab();
                puts("else\n");
                for (PStmt stmt: falseStmts) {
                    tab();
                    stmt.apply(this);
                }
            }
            while (stmts>0) {
                stmts--;
                puts("}\n");
            }
            
        }
        puts("\n");
        if (hasInit) {
            tabCount--;
            tab();
            puts("}\n");
        }
        
    }
    
    /* else if */
    // if this node is reached, then init stmt is not a short declaration
    // add init stmt into if condition
    public void caseAElseIf(AElseIf node)
    {
        PStmt shortStmt = node.getStmt();
        PExp exp = node.getExp();
        List<PStmt> stmts = node.getBody();
        if (shortStmt != null && !(node.getStmt() instanceof AEmptyStmt)) {
            puts("else if ((");
            stmt2Exp = true;
            shortStmt.apply(this);
            stmt2Exp = false;
            puts(")");
            if(exp != null)
            {
                puts(" && ");
                exp.apply(this);
            }
            puts(")\n");
            tab();
            for (PStmt stmt: stmts) {
                stmt.apply(this);
            }
        }
        else {
            puts("else if (");
            if(node.getExp() != null)
            {
                node.getExp().apply(this);
            }
            puts(")\n");
            tab();
            List<PStmt> body = new ArrayList<PStmt>(node.getBody());
            for(PStmt e : body)
            {
                e.apply(this);
            }
        }
    }
    
    /* switch statement */
    public void caseASwitchStmt(ASwitchStmt node)
    {
        boolean hasInit = false;
        if(node.getStmt() != null && !(node.getStmt() instanceof AEmptyStmt))
        {
            hasInit = true;
            puts("{\n");
            tabCount++;
            tab();
            node.getStmt().apply(this);
        }
        if (hasInit) {
            tab();
        }
        Node exp = node.getExp();
        // switch with expression
        if( exp != null)
        {
            Type t = removeAlias(typemap.get(exp));
            boolean nonBasic = false;
            if (!(t instanceof IntType || t instanceof BooleanType || t instanceof Float64Type
                  || t instanceof RuneType)) {
                nonBasic = true;
            }
            if (t instanceof BooleanType) {
                puts("switch ");
                puts("(");
                exp.apply(this);
                puts(") {\n");
                List<PSwitchExp> cases = new ArrayList<PSwitchExp>(node.getSwitchExp());
                for(PSwitchExp e : cases) {
                    tab();
                    e.apply(this);
                }
                puts("}\n");
            }
            
            // if switch exp is not bool, add explicit comparisons
            /*
             switch x {
             case a:
             default:
             }
             -->
             switch (1) {
             case (x==a):
             default:
             }
             
             */
            else {
                List<PSwitchExp> cases = new ArrayList<PSwitchExp>(node.getSwitchExp());
                for(PSwitchExp s : cases) {
                    tab();
                    List<Node> expList = new ArrayList<Node>(((ASwitchExp)s).getExp());
                    if(!expList.isEmpty()) {
                        noNewBraces = true;
                        puts("if (");
                        // change list of expressions to series of comparisons
                        Iterator<Node> itr = expList.iterator();
                        if (itr.hasNext()) {
                            Node next = itr.next();
                            if (!nonBasic) {
                                exp.apply(this);
                                puts("==");
                                next.apply(this);
                            }
                            else if (t instanceof StringType) {
                                puts("strcmp(");
                                exp.apply(this);
                                puts(",");
                                next.apply(this);
                                puts(") == 0");
                            }
                            else if (t instanceof ArrayType) {
                                Type type = getUnderlyingType((ArrayType)t);
                                String stringType = "";
                                List<String> dimensions = getDimensions((ArrayType)t);
                                if (type instanceof IntType) stringType = "int";
                                else if (type instanceof Float64Type) stringType = "float";
                                else if (type instanceof RuneType) stringType = "rune";
                                else if (type instanceof BooleanType) stringType = "boolean";
                                else if (type instanceof StringType) stringType = "string";
                                else if (type instanceof StructType) stringType = "struct";
                                else if (type instanceof ArrayType) stringType = "array";
                                puts(equalArraysFuncName + "(");
                                exp.apply(this);
                                puts(", ");
                                next.apply(this);
                                puts(", \"" + stringType + "\"");
                                puts(", " + "(int[]) ");
                                printDimensions(dimensions);
                                puts(", " + (dimensions.size()-1) + ")");
                            }
                        }
                        while (itr.hasNext()) {
                            Node next = itr.next();
                            puts(" || ");
                            if (!nonBasic) {
                                exp.apply(this);
                                puts("==");
                                next.apply(this);
                            }
                            else if (t instanceof StringType) {
                                puts("strcmp(");
                                exp.apply(this);
                                puts(",");
                                next.apply(this);
                                puts(") == 0)");
                            }
                            else if (t instanceof ArrayType) {
                                Type type = getUnderlyingType((ArrayType)t);
                                String stringType = "";
                                List<String> dimensions = getDimensions((ArrayType)t);
                                if (type instanceof IntType) stringType = "int";
                                else if (type instanceof Float64Type) stringType = "float";
                                else if (type instanceof RuneType) stringType = "rune";
                                else if (type instanceof BooleanType) stringType = "boolean";
                                else if (type instanceof StringType) stringType = "string";
                                else if (type instanceof StructType) stringType = "struct";
                                else if (type instanceof ArrayType) stringType = "array";
                                puts(equalArraysFuncName + "(");
                                exp.apply(this);
                                puts(", ");
                                next.apply(this);
                                puts(", \"" + stringType + "\"");
                                puts(", " + "(int[]) ");
                                printDimensions(dimensions);
                                puts(", " + (dimensions.size()-1) + ")");
                            }
                        }
                        puts(") {\n");
                        List<PStmt> body = new ArrayList<PStmt>(((ASwitchExp)s).getStmt());
                        for(PStmt e : body)
                        {
                            e.apply(this);
                        }
                        tab();
                        noNewBraces = false;
                        puts("}\n");
                    }
                    else {
                        puts("else {\n");
                        noNewBraces = true;
                        List<PStmt> body = new ArrayList<PStmt>(((ASwitchExp)s).getStmt());
                        for(PStmt e : body)
                        {
                            e.apply(this);
                        }
                        tab();
                        puts("}\n");
                        noNewBraces = false;
                    }
                }
            }
        }
        // switch without expression
        else {
            puts("switch (1) {\n");
            List<PSwitchExp> cases = new ArrayList<PSwitchExp>(node.getSwitchExp());
            for(PSwitchExp e : cases) {
                tab();
                e.apply(this);
            }
            puts("}\n");
        }
        
        tab();
        
        if (hasInit) {
            tabCount--;
            tab();
            puts("}\n");
        }
    }
    
    /* switch expression */
    // if this node is reached, switch exp is int/boolean
    public void caseASwitchExp(ASwitchExp node)
    {
        List<Node> expList = new ArrayList<Node>(node.getExp());
        if(!expList.isEmpty()) {
            noNewBraces = true;
            puts("case (");
            // change list of expressions to chain of or's
            Iterator<Node> itr = expList.iterator();
            if (itr.hasNext()) {
                Node next = itr.next();
                next.apply(this);
            }
            while (itr.hasNext()) {
                Node next = itr.next();
                puts(" || ");
                next.apply(this);
            }
            
            puts("):\n");
            List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
            for(PStmt e : body)
            {
                e.apply(this);
            }
            tab();
            puts("break;\n");
            noNewBraces = false;
        }
        else {
            puts("default:\n");
            noNewBraces = true;
            List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
            for(PStmt e : body)
            {
                e.apply(this);
            }
            tab();
            puts("break;\n");
            noNewBraces = false;
        }
    }
    
    /* infinite loop */
    public void caseAInfiniteLoopStmt(AInfiniteLoopStmt node)
    {
        puts("while (true) ");
        List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : body)
        {
            e.apply(this);
        }
    }
    
    /* while loop */
    public void caseAWhileStmt(AWhileStmt node)
    {
        puts("while (");
        node.getExp().apply(this);
        puts(") ");
        List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : body)
        {
            e.apply(this);
        }
    }
    
    /* for loop */
    public void caseAForStmt(AForStmt node)
    {
        noNewline = true;
        boolean initOutside = false;
        if(node.getInit() != null)
        {
            if (! (node.getInit() instanceof AShortDeclStmt)) {
                puts("for (");
                node.getInit().apply(this);
            }
            else {
                AShortDeclStmt shortDec = (AShortDeclStmt) node.getInit();
                List<Node> idList = new ArrayList<Node>(shortDec.getL());
                if (idList.size()>1) {
                    // bring declaration outside of for loop
                    initOutside = true;
                    puts("{\n");
                    tabCount++;
                    tab();
                    noNewline = false;
                    node.getInit().apply(this);
                    noNewline = true;
                    puts("for (");
                    
                }
                else {
                    puts("for (");
                    node.getInit().apply(this);
                }
            }
        }
        puts("; ");
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        puts("; ");
        if(node.getCond() != null)
        {
            node.getCond().apply(this);
        }
        noNewline = false;
        puts(") ");
        List<PStmt> body = new ArrayList<PStmt>(node.getBody());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        puts("\n");
        noNewBraces = false;
        if (initOutside) {
            tabCount--;
            tab();
            puts("}\n");
        }
    }
    
    /* block */
    public void caseABlockStmt(ABlockStmt node)
    {
        List<PStmt> copy = new ArrayList<PStmt>(node.getStmt());
        if (noNewBraces) {
            noNewBraces = false;
            for(PStmt e : copy)
            {
                tabCount++;
                tab();
                e.apply(this);
                tabCount--;
            }
            noNewBraces = true;
        }
        else {
            puts("{\n");
            for(PStmt e : copy)
            {
                tabCount++;
                tab();
                e.apply(this);
                tabCount--;
            }
            tab();
            puts("}\n");
        }
    }
    
    /* break */
    public void caseABreakStmt(ABreakStmt node)
    {
        puts("break;\n");
    }
    
    /* continue */
    public void caseAContinueStmt(AContinueStmt node)
    {
        puts("continue;\n");
    }
    
    /* int type */
    public void caseAIntType(AIntType node)
    {
        puts("int");
    }
    
    /* float type */
    public void caseAFloatType(AFloatType node)
    {
        puts("double");
    }
    
    /* bool type */
    public void caseABoolType(ABoolType node)
    {
        puts("bool");
    }
    
    /* rune type */
    public void caseARuneType(ARuneType node)
    {
        puts("char");
    }
    
    /* string type */
    public void caseAStringType(AStringType node)
    {
        puts("char*");
    }
    
    /* slice type */
    public void caseASliceType(ASliceType node)
    {
        puts("slice");
    }
    
    /* array type */
    public void caseAArrayType(AArrayType node)
    {
        node.getType().apply(this);
    }
    
    /* struct type */
    public void caseAStructType(AStructType node)
    {
        List<PField> fields = node.getField();
        puts("struct {\n");
        tabCount++;
        for (PField field: fields) {
            field.apply(this);
        }
        tabCount--;
        tab();
        puts("}");
        
    }
    
    /* field */
    public void caseAField(AField node)
    {
        List<PExp> fieldIds = node.getExp();
        for (PExp fieldId : fieldIds) {
            tab();
            node.getType().apply(this);
            puts(" ");
            fieldId.apply(this);
            Type t = removeAlias(typemap.get(fieldId));
            while (t instanceof ArrayType) {
                puts("[");
                puts(((ArrayType)t).getSize());
                puts("]");
                t = removeAlias(((ArrayType)t).getType());
            }
            puts(";\n");
        }
    }
    
    /* alias type */
    public void caseAAliasType(AAliasType node)
    {
        puts(node.getId().getText());
    }
    
    /* -----------------EXPRESSIONS----------------- */
    
    /* int */
    public void caseAIntExp(AIntExp node)
    {
        puts(node.getIntLit().getText());
    }
    
    /* float */
    public void caseAFloatExp(AFloatExp node)
    {
        puts(node.getFloatLit().getText());
    }
    
    /* rune */
    public void caseARuneExp(ARuneExp node)
    {
        puts(node.getRuneLit().getText());
    }
    
    /* string */
    public void caseAStrExp(AStrExp node)
    {
        puts(node.getStrLit().getText());
    }
    
    /* identifier */
    public void caseAIdExp(AIdExp node)
    {
        String id = node.getId().getText();
        if (printStmt) {
            Type type = removeAlias(typemap.get(node));
            if (type instanceof BooleanType) {
                id = id + " ? \"true\" : \"false\"";
            }
        }
        puts(id);
    }
    
    /* blank identifier */
    public void caseABlankExp(ABlankExp node)
    {
        // don't do anything
    }
    
    /* -------binary expressions------ */
    
    /* || */
    public void caseABinOrExp(ABinOrExp node)
    {
        printBinaryExp(node.getL(), " || ", node.getR());
    }
    
    /* && */
    public void caseABinAndExp(ABinAndExp node)
    {
        printBinaryExp(node.getL(), " || ", node.getR());
    }
    
    /* == */
    public void caseAEqExp(AEqExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType || t instanceof StructType || t instanceof ArrayType)) {
            printBinaryExp(node.getL(), " == ", node.getR());
        }
        if (t instanceof StringType) {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") == 0)");
        }
        //This only deals with simple types. Still same problem of dealing with fields that are structs/arrays
        if (t instanceof StructType) {
            List<Map.Entry<List<String>, Type>> fieldLines = ((StructType)t).getFields();
            for (Map.Entry<List<String>, Type> fields: fieldLines ) {
                for (String id : fields.getKey()) {
                    Type tl = typemap.get(node.getL());
                    if (! ((removeAlias(tl) instanceof ArrayType))) {
                        puts("(");
                        node.getL().apply(this);
                        puts("." + id);
                        puts(" == ");
                        node.getR().apply(this);
                        puts("." + id);
                        puts(") && ");
                    }
                    
                    
                }
                
            }
            puts("true");
        }
        
        if (t instanceof ArrayType) {
            Type type = getUnderlyingType((ArrayType)t);
            String stringType = "";
            List<String> dimensions = getDimensions((ArrayType)t);
            if (type instanceof IntType) stringType = "int";
            else if (type instanceof Float64Type) stringType = "float";
            else if (type instanceof RuneType) stringType = "rune";
            else if (type instanceof BooleanType) stringType = "boolean";
            else if (type instanceof StringType) stringType = "string";
            else if (type instanceof StructType) stringType = "struct";
            else if (type instanceof ArrayType) stringType = "array";
            puts(equalArraysFuncName + "(");
            node.getL().apply(this);
            puts(", ");
            node.getR().apply(this);
            puts(", \"" + stringType + "\"");
            puts(", " + "(int[" + Integer.toString(dimensions.size()) + "]) ");
            printDimensions(dimensions);
            puts(", " + (dimensions.size()-1) + ")");
        }
        
        
        /*
         //This doesn't work because the implementation of equalArraysFuncName is wrong
         if (t instanceof ArrayType) {
         Type type = getUnderlyingType((ArrayType)t);
         String stringType = "";
         List<String> dimensions = getDimensions((ArrayType)t);
         if (type instanceof IntType) stringType = "int";
         else if (type instanceof Float64Type) stringType = "float";
         else if (type instanceof RuneType) stringType = "rune";
         else if (type instanceof BooleanType) stringType = "boolean";
         else if (type instanceof StringType) stringType = "string";
         else if (type instanceof StructType) stringType = "struct";
         else if (type instanceof ArrayType) stringType = "array";
         puts(equalArraysFuncName + "(");
         node.getL().apply(this);
         puts(", ");
         node.getR().apply(this);
         puts(", \"" + stringType + "\"");
         puts(", " + "(int[]) ");
         printDimensions(dimensions);
         puts(", " + (dimensions.size()-1) + ")");
         }
         */
    }
    
    /* != */
    public void caseANeqExp(ANeqExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType || t instanceof StructType || t instanceof ArrayType)) {
            printBinaryExp(node.getL(), " != ", node.getR());
        }
        if (t instanceof StringType) {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") != 0)");
        }
        if (t instanceof StructType) {
            List<Map.Entry<List<String>, Type>> fieldLines = ((StructType)t).getFields();
            for (Map.Entry<List<String>, Type> fields: fieldLines ) {
                for (String id : fields.getKey()) {
                    Type tl = typemap.get(node.getL());
                    if (! ((removeAlias(tl) instanceof ArrayType))) {
                        puts("(");
                        node.getL().apply(this);
                        puts("." + id);
                        puts(" != ");
                        node.getR().apply(this);
                        puts("." + id);
                        puts(") || ");
                    }
                    
                    
                }
                
            }
            puts("true");
        }
        if (t instanceof ArrayType) {
            puts("!(");
            Type type = getUnderlyingType((ArrayType)t);
            String stringType = "";
            List<String> dimensions = getDimensions((ArrayType)t);
            if (type instanceof IntType) stringType = "int";
            else if (type instanceof Float64Type) stringType = "float";
            else if (type instanceof RuneType) stringType = "rune";
            else if (type instanceof BooleanType) stringType = "boolean";
            else if (type instanceof StringType) stringType = "string";
            else if (type instanceof StructType) stringType = "struct";
            else if (type instanceof ArrayType) stringType = "array";
            puts(equalArraysFuncName + "(");
            node.getL().apply(this);
            puts(", ");
            node.getR().apply(this);
            puts(", \"" + stringType + "\"");
            puts(", " + "(int[" + Integer.toString(dimensions.size()) + "]) ");
            printDimensions(dimensions);
            puts(", " + (dimensions.size()-1) + ")");
            puts(")");
        }
    }
    
    /* < */
    public void caseALtExp(ALtExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType)) {
            printBinaryExp(node.getL(), " < ", node.getR());
        }
        else {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") < 0)");
        }
    }
    
    /* <= */
    public void caseALeqExp(ALeqExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType)) {
            printBinaryExp(node.getL(), " <= ", node.getR());
        }
        else {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") <= 0)");
        }
    }
    
    /* > */
    public void caseAGtExp(AGtExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType)) {
            printBinaryExp(node.getL(), " > ", node.getR());
        }
        else {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") > 0)");
        }
    }
    
    /* >= */
    public void caseAGeqExp(AGeqExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType)) {
            printBinaryExp(node.getL(), " >= ", node.getR());
        }
        else {
            puts("(strcmp(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(") >= 0)");
        }
    }
    
    /* plus */
    public void caseAPlusExp(APlusExp node)
    {
        Type t = removeAlias(typemap.get(node.getL()));
        if (!(t instanceof StringType)) {
            printBinaryExp(node.getL(), " + ", node.getR());
        }
        else {
            puts(concatFuncName + "(");
            node.getL().apply(this);
            puts(",");
            node.getR().apply(this);
            puts(")");
        }
    }
    
    /* minus */
    public void caseAMinusExp(AMinusExp node)
    {
        printBinaryExp(node.getL(), " - ", node.getR());
    }
    
    /* pipe */
    public void caseAPipeExp(APipeExp node)
    {
        printBinaryExp(node.getL(), " | ", node.getR());
    }
    
    /* caret */
    public void caseACaretExp(ACaretExp node)
    {
        printBinaryExp(node.getL(), " ^ ", node.getR());
    }
    
    /* star */
    public void caseAStarExp(AStarExp node)
    {
        printBinaryExp(node.getL(), " * ", node.getR());
    }
    
    /* slash */
    public void caseASlashExp(ASlashExp node)
    {
        printBinaryExp(node.getL(), " / ", node.getR());
    }
    
    /* mod */
    public void caseAModExp(AModExp node)
    {
        printBinaryExp(node.getL(), " % ", node.getR());
    }
    
    /* l_shift */
    public void caseALShiftExp(ALShiftExp node)
    {
        printBinaryExp(node.getL(), " << ", node.getR());
    }
    
    /* r_shift */
    public void caseARShiftExp(ARShiftExp node)
    {
        printBinaryExp(node.getL(), " >> ", node.getR());
    }
    
    /* amp */
    public void caseAAmpExp(AAmpExp node)
    {
        printBinaryExp(node.getL(), " & ", node.getR());
    }
    
    /* amp_caret */
    public void caseAAmpCaretExp(AAmpCaretExp node)
    {
        printBinaryExp(node.getL(), " &^ ", node.getR());
    }
    
    /* -------unary expressions------ */
    
    /* u_plus */
    public void caseAUPlusExp(AUPlusExp node)
    {
        puts("(+");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* u_minus */
    public void caseAUMinusExp(AUMinusExp node)
    {
        puts("(-");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* u_bang */
    public void caseAUBangExp(AUBangExp node)
    {
        puts("(!");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* u_caret */
    public void caseAUCaretExp(AUCaretExp node)
    {
        puts("(^");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* -------other expressions------ */
    
    /* typecast */
    public void caseATypeCastExp(ATypeCastExp node)
    {
        puts("(");
        node.getType().apply(this);
        puts(")");
        node.getExp().apply(this);
    }
    
    /* selector */
    public void caseASelectorExp(ASelectorExp node)
    {
        // parentheses might be needed when referring to struct using pointer
        puts("(");
        node.getExp().apply(this);
        puts(".");
        puts(node.getId().getText());
        puts(")");
    }
    
    /* index */
    public void caseAIndexingExp(AIndexingExp node)
    {
        // do runtime bounds checking, i.e. for a[i] check that 0<=i<=len(a)
        // a[i] --> a[bounds_check(i, len)] or *(a + bounds_check(i, len))
        Type t = removeAlias(typemap.get(node.getArray()));
        
        // slices: a[i] --> ((sliceType*)(a.data))[bounds_check(i, len)]
        if (t instanceof SliceType) {
            puts("((");
            SliceType tSlice = (SliceType) t;
            Type t2 = removeAlias(tSlice.getType());
            printType(t2);
            puts("*)(");
            node.getArray().apply(this);
            puts(".data))[");
            puts(boundsFuncName + "(");
            node.getIndex().apply(this);
            puts(", ");
            node.getArray().apply(this);
            puts(".maxSize");
            puts(")]");
        }
        else {
            node.getArray().apply(this);
            puts("[");
            puts(boundsFuncName + "(");
            node.getIndex().apply(this);
            puts(", ");
            if (t instanceof ArrayType) {
                puts(((ArrayType)t).getSize());
            }
            puts(")]");
        }
    }
    
    /* func call */
    public void caseAFuncCallExp(AFuncCallExp node)
    {
        Type t = typemap.get(node.getName());
        if (t instanceof FunctionType) {
            node.getName().apply(this);
            puts("(");
            List<Node> args = new ArrayList<Node>(node.getParam());
            printList(args);
            puts(")");
        }
        // otherwise, it is a typecast
        else {
            puts("(");
            node.getName().apply(this);
            puts(")");
            List<Node> args = new ArrayList<Node>(node.getParam());
            printList(args);
        }
    }
    
    /* append */
    public void caseAAppendExp(AAppendExp node)
    {
        
        //append(a,x):
        //a.curSize++;
        //*((sliceType*)(a.data))[a.curSize] = x;
        
        Type type = removeAlias(typemap.get(node.getExp()));
        
        String name = node.getId().getText();
        
        puts("((");
        printType(type);
        puts("*)(");
        puts(name + ".data))[" + name + ".curSize] = ");
        node.getExp().apply(this);
        puts("; ");
        
        puts(name);
        puts(".curSize++;\n");
        
        
    }
    
    
}
