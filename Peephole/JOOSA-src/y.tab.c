/* A Bison parser, made by GNU Bison 2.7.12-4996.  */

/* Bison implementation for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2013 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.7.12-4996"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
/* Line 371 of yacc.c  */
#line 13 "joos.y"

 
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "tree.h"

extern CLASSFILE *theclassfile;
 

/* Line 371 of yacc.c  */
#line 79 "y.tab.c"

# ifndef YY_NULL
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULL nullptr
#  else
#   define YY_NULL 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* In a future release of Bison, this section will be replaced
   by #include "y.tab.h".  */
#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     tABSTRACT = 258,
     tBOOLEAN = 259,
     tBREAK = 260,
     tBYTE = 261,
     tCASE = 262,
     tCATCH = 263,
     tCHAR = 264,
     tCLASS = 265,
     tCONST = 266,
     tCONTINUE = 267,
     tDEFAULT = 268,
     tDO = 269,
     tDOUBLE = 270,
     tELSE = 271,
     tEXTENDS = 272,
     tEXTERN = 273,
     tFINAL = 274,
     tFINALLY = 275,
     tFLOAT = 276,
     tFOR = 277,
     tGOTO = 278,
     tIF = 279,
     tIMPLEMENTS = 280,
     tIMPORT = 281,
     tIN = 282,
     tINSTANCEOF = 283,
     tINT = 284,
     tINTERFACE = 285,
     tLONG = 286,
     tMAIN = 287,
     tMAINARGV = 288,
     tNATIVE = 289,
     tNEW = 290,
     tNULL = 291,
     tPACKAGE = 292,
     tPRIVATE = 293,
     tPROTECTED = 294,
     tPUBLIC = 295,
     tRETURN = 296,
     tSHORT = 297,
     tSTATIC = 298,
     tSUPER = 299,
     tSWITCH = 300,
     tSYNCHRONIZED = 301,
     tTHIS = 302,
     tTHROW = 303,
     tTHROWS = 304,
     tTRANSIENT = 305,
     tTRY = 306,
     tVOID = 307,
     tVOLATILE = 308,
     tWHILE = 309,
     tEQ = 310,
     tLEQ = 311,
     tGEQ = 312,
     tNEQ = 313,
     tAND = 314,
     tOR = 315,
     tINC = 316,
     tPATH = 317,
     tERROR = 318,
     tINTCONST = 319,
     tBOOLCONST = 320,
     tCHARCONST = 321,
     tSTRINGCONST = 322,
     tIDENTIFIER = 323
   };
#endif
/* Tokens.  */
#define tABSTRACT 258
#define tBOOLEAN 259
#define tBREAK 260
#define tBYTE 261
#define tCASE 262
#define tCATCH 263
#define tCHAR 264
#define tCLASS 265
#define tCONST 266
#define tCONTINUE 267
#define tDEFAULT 268
#define tDO 269
#define tDOUBLE 270
#define tELSE 271
#define tEXTENDS 272
#define tEXTERN 273
#define tFINAL 274
#define tFINALLY 275
#define tFLOAT 276
#define tFOR 277
#define tGOTO 278
#define tIF 279
#define tIMPLEMENTS 280
#define tIMPORT 281
#define tIN 282
#define tINSTANCEOF 283
#define tINT 284
#define tINTERFACE 285
#define tLONG 286
#define tMAIN 287
#define tMAINARGV 288
#define tNATIVE 289
#define tNEW 290
#define tNULL 291
#define tPACKAGE 292
#define tPRIVATE 293
#define tPROTECTED 294
#define tPUBLIC 295
#define tRETURN 296
#define tSHORT 297
#define tSTATIC 298
#define tSUPER 299
#define tSWITCH 300
#define tSYNCHRONIZED 301
#define tTHIS 302
#define tTHROW 303
#define tTHROWS 304
#define tTRANSIENT 305
#define tTRY 306
#define tVOID 307
#define tVOLATILE 308
#define tWHILE 309
#define tEQ 310
#define tLEQ 311
#define tGEQ 312
#define tNEQ 313
#define tAND 314
#define tOR 315
#define tINC 316
#define tPATH 317
#define tERROR 318
#define tINTCONST 319
#define tBOOLCONST 320
#define tCHARCONST 321
#define tSTRINGCONST 322
#define tIDENTIFIER 323



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{
/* Line 387 of yacc.c  */
#line 26 "joos.y"

   struct CLASSFILE *classfile;
   struct CLASS *class;
   struct FIELD *field;
   struct TYPE *type;
   struct ID *id;
   struct CONSTRUCTOR *constructor;
   struct METHOD *method;
   struct FORMAL *formal;
   struct STATEMENT *statement;
   struct EXP *exp;
   struct RECEIVER *receiver;
   struct ARGUMENT *argument;
   int modifier;
   int intconst;
   int boolconst;
   char charconst;
   char *stringconst;


/* Line 387 of yacc.c  */
#line 279 "y.tab.c"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */

/* Copy the second part of user declarations.  */

/* Line 390 of yacc.c  */
#line 307 "y.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef __attribute__
/* This feature is available in gcc versions 2.5 and later.  */
# if (! defined __GNUC__ || __GNUC__ < 2 \
      || (__GNUC__ == 2 && __GNUC_MINOR__ < 5))
#  define __attribute__(Spec) /* empty */
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif


/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(N) (N)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (YYID (0))
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  7
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   540

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  87
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  70
/* YYNRULES -- Number of rules.  */
#define YYNRULES  151
/* YYNRULES -- Number of states.  */
#define YYNSTATES  317

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   323

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    85,     2,     2,     2,    84,     2,     2,
      73,    74,    82,    80,    72,    81,    86,    83,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    71,
      78,    77,    79,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    75,     2,    76,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    69,     2,    70,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     6,     8,     9,    12,    23,    24,    26,
      28,    30,    33,    46,    47,    50,    52,    54,    56,    58,
      59,    61,    63,    66,    71,    73,    77,    79,    82,    96,
      98,   101,   108,   109,   111,   113,   117,   120,   121,   123,
     125,   128,   139,   149,   158,   169,   171,   173,   178,   183,
     184,   186,   188,   191,   200,   208,   210,   212,   214,   216,
     218,   219,   221,   223,   226,   228,   230,   232,   234,   236,
     238,   242,   244,   248,   250,   252,   258,   266,   268,   270,
     272,   274,   282,   288,   294,   304,   314,   315,   317,   319,
     323,   324,   326,   329,   332,   334,   336,   338,   340,   344,
     345,   347,   351,   353,   355,   357,   361,   363,   367,   369,
     373,   377,   379,   383,   387,   391,   395,   399,   401,   405,
     409,   411,   415,   419,   423,   425,   428,   430,   433,   435,
     440,   445,   447,   449,   451,   453,   457,   459,   461,   467,
     474,   476,   478,   480,   481,   483,   485,   489,   491,   493,
     495,   497
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int16 yyrhs[] =
{
      88,     0,    -1,    89,    90,    -1,    92,    -1,    -1,    89,
      62,    -1,    40,    91,    10,    68,    94,    69,    96,   100,
     107,    70,    -1,    -1,    19,    -1,     3,    -1,    93,    -1,
      92,    93,    -1,    18,    40,    91,    10,    68,    94,    27,
      67,    69,   102,   112,    70,    -1,    -1,    17,    68,    -1,
      68,    -1,     9,    -1,     4,    -1,    29,    -1,    -1,    97,
      -1,    98,    -1,    97,    98,    -1,    39,    95,    99,    71,
      -1,    68,    -1,    99,    72,    68,    -1,   101,    -1,   100,
     101,    -1,    40,    68,    73,   104,    74,    69,    44,    73,
     154,    74,    71,   117,    70,    -1,   103,    -1,   102,   103,
      -1,    40,    68,    73,   104,    74,    71,    -1,    -1,   105,
      -1,   106,    -1,   105,    72,   106,    -1,    95,    68,    -1,
      -1,   108,    -1,   109,    -1,   108,   109,    -1,    40,   110,
     116,    68,    73,   104,    74,    69,   117,    70,    -1,    40,
     116,    68,    73,   104,    74,    69,   117,    70,    -1,    40,
       3,   116,    68,    73,   104,    74,    71,    -1,    40,    43,
      52,    32,    73,   111,    74,    69,   117,    70,    -1,    19,
      -1,    46,    -1,    68,    68,    75,    76,    -1,    68,    75,
      76,    68,    -1,    -1,   113,    -1,   114,    -1,   113,   114,
      -1,    40,   115,   116,    68,    73,   104,    74,    71,    -1,
      40,   116,    68,    73,   104,    74,    71,    -1,    19,    -1,
       3,    -1,    46,    -1,    52,    -1,    95,    -1,    -1,   118,
      -1,   119,    -1,   118,   119,    -1,   121,    -1,   120,    -1,
     122,    -1,   123,    -1,   126,    -1,   128,    -1,    95,    99,
      71,    -1,    71,    -1,    69,   117,    70,    -1,   134,    -1,
     136,    -1,    24,    73,   139,    74,   119,    -1,    24,    73,
     139,    74,   124,    16,   119,    -1,   121,    -1,   125,    -1,
     127,    -1,   129,    -1,    24,    73,   139,    74,   124,    16,
     124,    -1,    54,    73,   139,    74,   119,    -1,    54,    73,
     139,    74,   124,    -1,    22,    73,   130,    71,   132,    71,
     130,    74,   119,    -1,    22,    73,   130,    71,   132,    71,
     130,    74,   124,    -1,    -1,   131,    -1,   135,    -1,   131,
      72,   135,    -1,    -1,   139,    -1,    68,    61,    -1,   135,
      71,    -1,   138,    -1,   152,    -1,   151,    -1,   133,    -1,
      41,   137,    71,    -1,    -1,   139,    -1,    68,    77,   139,
      -1,   140,    -1,   138,    -1,   141,    -1,   140,    60,   141,
      -1,   142,    -1,   141,    59,   142,    -1,   143,    -1,   142,
      55,   143,    -1,   142,    58,   143,    -1,   144,    -1,   143,
      78,   144,    -1,   143,    79,   144,    -1,   143,    56,   144,
      -1,   143,    57,   144,    -1,   143,    28,    68,    -1,   145,
      -1,   144,    80,   145,    -1,   144,    81,   145,    -1,   146,
      -1,   145,    82,   146,    -1,   145,    83,   146,    -1,   145,
      84,   146,    -1,   147,    -1,    81,   146,    -1,   149,    -1,
      85,   146,    -1,   148,    -1,    73,   139,    74,   147,    -1,
      73,     9,    74,   146,    -1,    68,    -1,   150,    -1,   156,
      -1,    47,    -1,    73,   139,    74,    -1,   151,    -1,   152,
      -1,    35,    68,    73,   154,    74,    -1,   153,    86,    68,
      73,   154,    74,    -1,    68,    -1,   150,    -1,    44,    -1,
      -1,   155,    -1,   139,    -1,   155,    72,   139,    -1,    64,
      -1,    65,    -1,    66,    -1,    67,    -1,    36,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,    97,    97,    99,   103,   104,   107,   113,   114,   116,
     120,   122,   126,   132,   133,   137,   139,   141,   143,   148,
     149,   153,   155,   159,   163,   165,   169,   171,   175,   186,
     188,   192,   197,   198,   202,   204,   208,   213,   214,   218,
     220,   224,   226,   228,   230,   234,   236,   240,   242,   247,
     248,   252,   254,   258,   260,   264,   266,   268,   273,   275,
     280,   281,   285,   287,   291,   293,   295,   297,   299,   301,
     305,   309,   311,   313,   315,   319,   323,   327,   329,   331,
     333,   337,   342,   346,   358,   363,   368,   369,   371,   372,
     375,   376,   386,   395,   399,   401,   403,   405,   408,   413,
     414,   418,   422,   424,   428,   430,   434,   436,   440,   442,
     444,   448,   450,   452,   454,   456,   458,   462,   464,   466,
     472,   474,   476,   478,   482,   484,   489,   491,   493,   496,
     499,   503,   505,   509,   511,   513,   515,   517,   521,   525,
     529,   531,   533,   538,   539,   543,   545,   549,   551,   553,
     555,   557
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "tABSTRACT", "tBOOLEAN", "tBREAK",
  "tBYTE", "tCASE", "tCATCH", "tCHAR", "tCLASS", "tCONST", "tCONTINUE",
  "tDEFAULT", "tDO", "tDOUBLE", "tELSE", "tEXTENDS", "tEXTERN", "tFINAL",
  "tFINALLY", "tFLOAT", "tFOR", "tGOTO", "tIF", "tIMPLEMENTS", "tIMPORT",
  "tIN", "tINSTANCEOF", "tINT", "tINTERFACE", "tLONG", "tMAIN",
  "tMAINARGV", "tNATIVE", "tNEW", "tNULL", "tPACKAGE", "tPRIVATE",
  "tPROTECTED", "tPUBLIC", "tRETURN", "tSHORT", "tSTATIC", "tSUPER",
  "tSWITCH", "tSYNCHRONIZED", "tTHIS", "tTHROW", "tTHROWS", "tTRANSIENT",
  "tTRY", "tVOID", "tVOLATILE", "tWHILE", "tEQ", "tLEQ", "tGEQ", "tNEQ",
  "tAND", "tOR", "tINC", "tPATH", "tERROR", "tINTCONST", "tBOOLCONST",
  "tCHARCONST", "tSTRINGCONST", "tIDENTIFIER", "'{'", "'}'", "';'", "','",
  "'('", "')'", "'['", "']'", "'='", "'<'", "'>'", "'+'", "'-'", "'*'",
  "'/'", "'%'", "'!'", "'.'", "$accept", "classfile", "imports", "class",
  "classmods", "externclasses", "externclass", "extension", "type",
  "fields", "nefields", "field", "idlist", "constructors", "constructor",
  "externconstructors", "externconstructor", "formals", "neformals",
  "formal", "methods", "nemethods", "method", "methodmods", "mainargv",
  "externmethods", "externnemethods", "externmethod", "externmods",
  "returntype", "statements", "nestatements", "statement", "declaration",
  "simplestatement", "ifthenstatement", "ifthenelsestatement",
  "statementnoshortif", "ifthenelsestatementnoshortif", "whilestatement",
  "whilestatementnoshortif", "forstatement", "forstatementnoshortif",
  "listassignexp", "nelistassignexp", "listbooleanexp",
  "incrementexpression", "expressionstatement", "statementexpression",
  "returnstatement", "returnexpression", "assignment", "expression",
  "orexpression", "andexpression", "eqexpression", "relexpression",
  "addexpression", "multexpression", "unaryexpression",
  "unaryexpressionnotminus", "castexpression", "postfixexpression",
  "primaryexpression", "classinstancecreation", "methodinvocation",
  "receiver", "arguments", "nearguments", "literal", YY_NULL
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323,   123,
     125,    59,    44,    40,    41,    91,    93,    61,    60,    62,
      43,    45,    42,    47,    37,    33,    46
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    87,    88,    88,    89,    89,    90,    91,    91,    91,
      92,    92,    93,    94,    94,    95,    95,    95,    95,    96,
      96,    97,    97,    98,    99,    99,   100,   100,   101,   102,
     102,   103,   104,   104,   105,   105,   106,   107,   107,   108,
     108,   109,   109,   109,   109,   110,   110,   111,   111,   112,
     112,   113,   113,   114,   114,   115,   115,   115,   116,   116,
     117,   117,   118,   118,   119,   119,   119,   119,   119,   119,
     120,   121,   121,   121,   121,   122,   123,   124,   124,   124,
     124,   125,   126,   127,   128,   129,   130,   130,   131,   131,
     132,   132,   133,   134,   135,   135,   135,   135,   136,   137,
     137,   138,   139,   139,   140,   140,   141,   141,   142,   142,
     142,   143,   143,   143,   143,   143,   143,   144,   144,   144,
     145,   145,   145,   145,   146,   146,   147,   147,   147,   148,
     148,   149,   149,   150,   150,   150,   150,   150,   151,   152,
     153,   153,   153,   154,   154,   155,   155,   156,   156,   156,
     156,   156
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     2,     1,     0,     2,    10,     0,     1,     1,
       1,     2,    12,     0,     2,     1,     1,     1,     1,     0,
       1,     1,     2,     4,     1,     3,     1,     2,    13,     1,
       2,     6,     0,     1,     1,     3,     2,     0,     1,     1,
       2,    10,     9,     8,    10,     1,     1,     4,     4,     0,
       1,     1,     2,     8,     7,     1,     1,     1,     1,     1,
       0,     1,     1,     2,     1,     1,     1,     1,     1,     1,
       3,     1,     3,     1,     1,     5,     7,     1,     1,     1,
       1,     7,     5,     5,     9,     9,     0,     1,     1,     3,
       0,     1,     2,     2,     1,     1,     1,     1,     3,     0,
       1,     3,     1,     1,     1,     3,     1,     3,     1,     3,
       3,     1,     3,     3,     3,     3,     3,     1,     3,     3,
       1,     3,     3,     3,     1,     2,     1,     2,     1,     4,
       4,     1,     1,     1,     1,     3,     1,     1,     5,     6,
       1,     1,     1,     0,     1,     1,     3,     1,     1,     1,
       1,     1
};

/* YYDEFACT[STATE-NAME] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       4,     0,     0,     0,     3,    10,     7,     1,     7,     5,
       2,    11,     9,     8,     0,     0,     0,     0,    13,    13,
       0,     0,     0,    14,     0,    19,     0,     0,     0,    20,
      21,     0,    17,    16,    18,    15,     0,     0,    37,    26,
      22,     0,    49,    29,    24,     0,     0,     0,    27,     0,
      38,    39,     0,     0,    30,     0,    50,    51,    23,     0,
      32,     0,    45,     0,    46,    58,    15,    59,     0,     0,
       6,     0,    40,    32,    56,    55,    57,    15,     0,     0,
      12,     0,    52,    25,     0,     0,    33,    34,     0,     0,
       0,     0,     0,     0,     0,    36,     0,     0,     0,     0,
       0,    32,     0,     0,    32,     0,    35,    32,     0,    32,
       0,    31,    32,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   143,     0,     0,     0,     0,     0,    60,     0,
      54,     0,   151,   142,   134,   147,   148,   149,   150,   131,
       0,     0,     0,   103,   145,   102,   104,   106,   108,   111,
     117,   120,   124,   128,   126,   132,   136,   137,     0,     0,
     144,   133,    43,     0,     0,    60,    60,     0,     0,    99,
       0,    15,    60,    71,     0,     0,     0,    61,    62,    65,
      64,    66,    67,    68,    69,    97,    73,     0,    74,    94,
     141,    96,    95,    53,     0,     0,     0,     0,   131,   125,
     127,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    47,    48,
       0,     0,    86,     0,     0,   100,     0,    92,     0,     0,
       0,    42,    63,    93,   143,   101,     0,   135,   105,   107,
     109,   110,   116,   114,   115,   112,   113,   118,   119,   121,
     122,   123,     0,    60,   146,    44,    41,   140,     0,    87,
      88,     0,    98,     0,    72,   135,    70,     0,   130,   129,
     143,     0,    90,     0,     0,     0,   138,     0,    28,     0,
      91,    89,     0,     0,     0,    75,    64,     0,    78,    79,
      80,    82,   139,    86,    86,     0,     0,     0,     0,     0,
       0,     0,    76,     0,    90,     0,     0,    84,     0,     0,
      83,    86,     0,     0,    81,     0,    85
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,     3,    10,    14,     4,     5,    21,   175,    28,
      29,    30,    45,    38,    39,    42,    43,    85,    86,    87,
      49,    50,    51,    68,   117,    55,    56,    57,    78,    69,
     176,   177,   178,   179,   180,   181,   182,   287,   288,   183,
     289,   184,   290,   258,   259,   279,   185,   186,   187,   188,
     224,   189,   144,   145,   146,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,   157,   158,   159,   160,   161
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -284
static const yytype_int16 yypact[] =
{
       7,    -9,    57,   -21,     7,  -284,    15,  -284,    15,  -284,
    -284,  -284,  -284,  -284,    49,    69,    14,    18,    76,    76,
      35,    84,    44,  -284,    47,    77,    51,    17,    82,    77,
    -284,    91,  -284,  -284,  -284,  -284,    56,    65,   102,  -284,
    -284,    80,   104,  -284,  -284,   -10,    85,     4,  -284,    79,
     115,  -284,    87,   403,  -284,    94,   117,  -284,  -284,    99,
      17,    23,  -284,   119,  -284,  -284,    85,  -284,    23,   105,
    -284,   359,  -284,    17,  -284,  -284,  -284,    87,    23,   106,
    -284,   405,  -284,  -284,   108,   112,   107,  -284,   124,   148,
     129,   125,   127,   134,   133,  -284,   142,    17,   137,   143,
     144,    17,   160,   146,    17,   170,  -284,    17,   166,    17,
     162,  -284,    17,   164,   167,   168,   -51,   169,   171,   172,
     174,   178,   394,   179,   176,   177,   183,   187,   282,   184,
    -284,   190,  -284,  -284,  -284,  -284,  -284,  -284,  -284,   -57,
     235,   430,   430,  -284,  -284,   200,   202,     0,    40,   -14,
     141,  -284,  -284,  -284,  -284,   180,  -284,  -284,   182,   188,
     191,  -284,  -284,   189,   201,   282,   282,   199,   203,   394,
     204,   -47,   282,  -284,   394,    56,   205,   282,  -284,  -284,
    -284,  -284,  -284,  -284,  -284,  -284,  -284,   207,  -284,  -284,
    -284,   194,   195,  -284,   211,   394,   214,   215,   206,  -284,
    -284,   430,   430,   430,   430,   217,   430,   430,   430,   430,
     430,   430,   430,   430,   430,   226,   225,   394,  -284,  -284,
     227,   237,   465,   394,   234,  -284,   394,  -284,   239,   236,
      -1,  -284,  -284,  -284,   394,  -284,   430,   455,   202,     0,
      40,    40,  -284,   -14,   -14,   -14,   -14,   141,   141,  -284,
    -284,  -284,   240,   282,  -284,  -284,  -284,   -13,   241,   242,
    -284,   245,  -284,   247,  -284,  -284,  -284,   248,  -284,  -284,
     394,   254,   394,   465,   330,   282,  -284,   251,  -284,   244,
    -284,  -284,   255,   257,   258,  -284,   311,   316,  -284,  -284,
    -284,  -284,  -284,   465,   465,   394,   394,   282,   259,   264,
     263,   266,  -284,   282,   394,   330,   330,  -284,   267,   325,
    -284,   465,   330,   268,  -284,   330,  -284
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -284,  -284,  -284,  -284,   265,  -284,   270,   324,   186,  -284,
    -284,   315,   181,  -284,   307,  -284,   318,    31,  -284,   260,
    -284,  -284,   308,  -284,  -284,  -284,  -284,   305,  -284,   154,
    -163,  -284,  -176,  -284,  -270,  -284,  -284,  -200,  -284,  -284,
    -284,  -284,  -284,  -283,  -284,    60,  -284,  -284,  -210,  -284,
    -284,  -100,   -67,  -284,   175,   165,  -115,   -55,  -102,  -136,
     132,  -284,  -284,  -128,  -112,   -85,  -284,  -219,  -284,  -284
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -142
static const yytype_int16 yytable[] =
{
     190,   232,   220,   221,   286,   199,   200,    61,    32,   228,
     298,   299,   260,    33,   227,   267,   191,   124,    12,     8,
     195,    32,   143,    62,   125,     1,    33,    32,   313,  -140,
     195,     6,    33,    34,    13,   286,   286,   190,   190,  -140,
     143,     9,   286,   192,   190,   286,    34,    63,   227,   190,
      64,   277,    34,   191,   191,   203,    65,     7,   204,    16,
     191,    58,    59,   281,   195,   191,   210,   211,   205,   143,
     266,    59,    66,   197,   143,    65,   249,   250,   251,    17,
     192,   192,    18,   260,   260,    35,    19,   192,   240,   241,
     271,    35,   192,    20,   190,   143,   206,   207,   285,   291,
     268,   260,   225,    23,    92,   309,   310,   229,   247,   248,
     191,    24,   314,    25,    26,   316,    27,   143,   208,   209,
      31,   302,    37,   143,    44,   190,   143,   307,   235,   285,
     291,    41,   110,    46,   143,   113,   302,   192,   115,   307,
     118,   191,    47,   120,    53,   190,   190,   190,    52,    70,
     254,   243,   244,   245,   246,    71,   261,    81,    60,   263,
      73,   191,   191,   191,    80,   190,   190,    83,   192,   190,
     143,    89,   143,    91,    94,   190,    95,   190,   190,    97,
      99,   191,   191,   190,   190,   191,    96,   190,   192,   192,
     192,   191,    98,   191,   191,   143,   143,   100,   101,   191,
     191,   102,   103,   191,   143,   280,   104,    79,   192,   192,
     107,   105,   192,    36,   114,    88,   108,   109,   192,   112,
     192,   192,    90,   212,   213,   214,   192,   192,   300,   301,
     192,   111,    93,    67,   116,    79,   119,   280,   121,    67,
     122,   128,   123,   126,   196,   127,    84,    67,   129,   130,
     162,   163,   165,   164,    67,   193,   166,    67,   194,    84,
     201,   202,   216,   217,    67,   218,  -141,    67,   215,   219,
     131,   132,   222,    15,    11,   231,   223,   226,   233,   133,
    -136,  -137,   134,    84,   234,   242,    32,    84,   236,   237,
      84,    33,  -140,    84,   252,    84,   253,   255,    84,   135,
     136,   137,   138,   139,   167,   262,   168,   256,   140,   264,
     265,    34,   272,   270,   273,   293,   141,   131,   132,   274,
     142,   275,   276,   169,   278,   292,   133,   -77,   294,   134,
     295,   296,   297,   303,    32,   304,   170,   305,   311,    33,
     306,   312,   315,    22,    40,    48,   135,   136,   137,   138,
     171,   172,   282,   173,   283,   174,   230,   106,    72,    34,
      54,    82,    61,    32,   308,   131,   132,   239,    33,   269,
       0,   169,     0,     0,   133,     0,   238,   134,    62,     0,
       0,     0,     0,     0,   284,     0,     0,     0,    34,     0,
       0,     0,     0,     0,   135,   136,   137,   138,   171,   172,
       0,   173,    63,   174,     0,    64,    74,    32,    74,    32,
       0,    65,    33,     0,    33,     0,     0,     0,     0,     0,
       0,     0,    75,     0,    75,     0,     0,    35,     0,   131,
     132,     0,    34,     0,    34,     0,     0,     0,   133,     0,
       0,   134,     0,     0,     0,     0,     0,     0,     0,    76,
       0,    76,     0,     0,     0,    65,     0,    65,   135,   136,
     137,   138,   139,     0,     0,   131,   132,   140,     0,     0,
       0,    77,     0,    35,   133,   141,     0,   134,     0,   142,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     131,   132,     0,     0,   135,   136,   137,   138,   198,   133,
     131,   132,   134,   140,     0,     0,     0,     0,     0,   133,
       0,   141,   134,     0,     0,   142,     0,     0,     0,   135,
     136,   137,   138,   198,     0,     0,     0,     0,   140,   135,
     136,   137,   138,   257,     0,     0,     0,     0,   174,     0,
     142
};

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-284)))

#define yytable_value_is_error(Yytable_value) \
  YYID (0)

static const yytype_int16 yycheck[] =
{
     128,   177,   165,   166,   274,   141,   142,     3,     4,   172,
     293,   294,   222,     9,    61,   234,   128,    68,     3,    40,
      77,     4,   122,    19,    75,    18,     9,     4,   311,    86,
      77,    40,     9,    29,    19,   305,   306,   165,   166,    86,
     140,    62,   312,   128,   172,   315,    29,    43,    61,   177,
      46,   270,    29,   165,   166,    55,    52,     0,    58,    10,
     172,    71,    72,   273,    77,   177,    80,    81,    28,   169,
      71,    72,    68,   140,   174,    52,   212,   213,   214,    10,
     165,   166,    68,   293,   294,    68,    68,   172,   203,   204,
     253,    68,   177,    17,   222,   195,    56,    57,   274,   275,
     236,   311,   169,    68,    73,   305,   306,   174,   210,   211,
     222,    27,   312,    69,    67,   315,    39,   217,    78,    79,
      69,   297,    40,   223,    68,   253,   226,   303,   195,   305,
     306,    40,   101,    68,   234,   104,   312,   222,   107,   315,
     109,   253,    40,   112,    40,   273,   274,   275,    68,    70,
     217,   206,   207,   208,   209,    40,   223,    40,    73,   226,
      73,   273,   274,   275,    70,   293,   294,    68,   253,   297,
     270,    52,   272,    68,    68,   303,    68,   305,   306,    72,
      32,   293,   294,   311,   312,   297,    74,   315,   273,   274,
     275,   303,    68,   305,   306,   295,   296,    68,    73,   311,
     312,    74,    68,   315,   304,   272,    73,    53,   293,   294,
      73,    69,   297,    27,    44,    61,    73,    73,   303,    73,
     305,   306,    68,    82,    83,    84,   311,   312,   295,   296,
     315,    71,    78,    47,    68,    81,    74,   304,    74,    53,
      73,    69,    74,    74,     9,    74,    60,    61,    74,    71,
      71,    75,    69,    76,    68,    71,    69,    71,    68,    73,
      60,    59,    74,    72,    78,    76,    86,    81,    86,    68,
      35,    36,    73,     8,     4,    70,    73,    73,    71,    44,
      86,    86,    47,    97,    73,    68,     4,   101,    74,    74,
     104,     9,    86,   107,    68,   109,    71,    70,   112,    64,
      65,    66,    67,    68,    22,    71,    24,    70,    73,    70,
      74,    29,    71,    73,    72,    71,    81,    35,    36,    74,
      85,    74,    74,    41,    70,    74,    44,    16,    73,    47,
      73,    73,    16,    74,     4,    71,    54,    74,    71,     9,
      74,    16,    74,    19,    29,    38,    64,    65,    66,    67,
      68,    69,    22,    71,    24,    73,   175,    97,    50,    29,
      42,    56,     3,     4,   304,    35,    36,   202,     9,   237,
      -1,    41,    -1,    -1,    44,    -1,   201,    47,    19,    -1,
      -1,    -1,    -1,    -1,    54,    -1,    -1,    -1,    29,    -1,
      -1,    -1,    -1,    -1,    64,    65,    66,    67,    68,    69,
      -1,    71,    43,    73,    -1,    46,     3,     4,     3,     4,
      -1,    52,     9,    -1,     9,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    19,    -1,    19,    -1,    -1,    68,    -1,    35,
      36,    -1,    29,    -1,    29,    -1,    -1,    -1,    44,    -1,
      -1,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    46,
      -1,    46,    -1,    -1,    -1,    52,    -1,    52,    64,    65,
      66,    67,    68,    -1,    -1,    35,    36,    73,    -1,    -1,
      -1,    68,    -1,    68,    44,    81,    -1,    47,    -1,    85,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      35,    36,    -1,    -1,    64,    65,    66,    67,    68,    44,
      35,    36,    47,    73,    -1,    -1,    -1,    -1,    -1,    44,
      -1,    81,    47,    -1,    -1,    85,    -1,    -1,    -1,    64,
      65,    66,    67,    68,    -1,    -1,    -1,    -1,    73,    64,
      65,    66,    67,    68,    -1,    -1,    -1,    -1,    73,    -1,
      85
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    18,    88,    89,    92,    93,    40,     0,    40,    62,
      90,    93,     3,    19,    91,    91,    10,    10,    68,    68,
      17,    94,    94,    68,    27,    69,    67,    39,    96,    97,
      98,    69,     4,     9,    29,    68,    95,    40,   100,   101,
      98,    40,   102,   103,    68,    99,    68,    40,   101,   107,
     108,   109,    68,    40,   103,   112,   113,   114,    71,    72,
      73,     3,    19,    43,    46,    52,    68,    95,   110,   116,
      70,    40,   109,    73,     3,    19,    46,    68,   115,   116,
      70,    40,   114,    68,    95,   104,   105,   106,   116,    52,
     116,    68,   104,   116,    68,    68,    74,    72,    68,    32,
      68,    73,    74,    68,    73,    69,   106,    73,    73,    73,
     104,    71,    73,   104,    44,   104,    68,   111,   104,    74,
     104,    74,    73,    74,    68,    75,    74,    74,    69,    74,
      71,    35,    36,    44,    47,    64,    65,    66,    67,    68,
      73,    81,    85,   138,   139,   140,   141,   142,   143,   144,
     145,   146,   147,   148,   149,   150,   151,   152,   153,   154,
     155,   156,    71,    75,    76,    69,    69,    22,    24,    41,
      54,    68,    69,    71,    73,    95,   117,   118,   119,   120,
     121,   122,   123,   126,   128,   133,   134,   135,   136,   138,
     150,   151,   152,    71,    68,    77,     9,   139,    68,   146,
     146,    60,    59,    55,    58,    28,    56,    57,    78,    79,
      80,    81,    82,    83,    84,    86,    74,    72,    76,    68,
     117,   117,    73,    73,   137,   139,    73,    61,   117,   139,
      99,    70,   119,    71,    73,   139,    74,    74,   141,   142,
     143,   143,    68,   144,   144,   144,   144,   145,   145,   146,
     146,   146,    68,    71,   139,    70,    70,    68,   130,   131,
     135,   139,    71,   139,    70,    74,    71,   154,   146,   147,
      73,   117,    71,    72,    74,    74,    74,   154,    70,   132,
     139,   135,    22,    24,    54,   119,   121,   124,   125,   127,
     129,   119,    74,    71,    73,    73,    73,    16,   130,   130,
     139,   139,   119,    74,    71,    74,    74,   119,   132,   124,
     124,    71,    16,   130,   124,    74,   124
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  However,
   YYFAIL appears to be in use.  Nevertheless, it is formally deprecated
   in Bison 2.4.2's NEWS entry, where a plan to phase it out is
   discussed.  */

#define YYFAIL		goto yyerrlab
#if defined YYFAIL
  /* This is here to suppress warnings from the GCC cpp's
     -Wunused-macros.  Normally we don't worry about that warning, but
     some users do, and we want to make it easy for users to remove
     YYFAIL uses, which will produce warnings from Bison 2.5.  */
#endif

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))

/* Error token number */
#define YYTERROR	1
#define YYERRCODE	256


/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */
#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULL, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULL;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - Assume YYFAIL is not used.  It's too flawed to consider.  See
       <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
       for details.  YYERROR is fine as it does not invoke this
       function.
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULL, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YYUSE (yytype);
}




/* The lookahead symbol.  */
int yychar;


#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval YY_INITIAL_VALUE(yyval_default);

/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
/* Line 1787 of yacc.c  */
#line 98 "joos.y"
    {theclassfile = makeCLASSFILE((yyvsp[(2) - (2)].class),NULL);}
    break;

  case 3:
/* Line 1787 of yacc.c  */
#line 100 "joos.y"
    {theclassfile = (yyvsp[(1) - (1)].classfile);}
    break;

  case 6:
/* Line 1787 of yacc.c  */
#line 109 "joos.y"
    {(yyval.class) = makeCLASS((yyvsp[(4) - (10)].stringconst),(yyvsp[(5) - (10)].stringconst),0,NULL,(yyvsp[(2) - (10)].modifier),(yyvsp[(7) - (10)].field),(yyvsp[(8) - (10)].constructor),(yyvsp[(9) - (10)].method));}
    break;

  case 7:
/* Line 1787 of yacc.c  */
#line 113 "joos.y"
    {(yyval.modifier) = noneMod;}
    break;

  case 8:
/* Line 1787 of yacc.c  */
#line 115 "joos.y"
    {(yyval.modifier) = finalMod;}
    break;

  case 9:
/* Line 1787 of yacc.c  */
#line 117 "joos.y"
    {(yyval.modifier) = abstractMod;}
    break;

  case 10:
/* Line 1787 of yacc.c  */
#line 121 "joos.y"
    {(yyval.classfile) = makeCLASSFILE((yyvsp[(1) - (1)].class),NULL);}
    break;

  case 11:
/* Line 1787 of yacc.c  */
#line 123 "joos.y"
    {(yyval.classfile) = makeCLASSFILE((yyvsp[(2) - (2)].class),(yyvsp[(1) - (2)].classfile));}
    break;

  case 12:
/* Line 1787 of yacc.c  */
#line 128 "joos.y"
    {(yyval.class) = makeCLASS((yyvsp[(5) - (12)].stringconst),(yyvsp[(6) - (12)].stringconst),1,(yyvsp[(8) - (12)].stringconst),(yyvsp[(3) - (12)].modifier),NULL,(yyvsp[(10) - (12)].constructor),(yyvsp[(11) - (12)].method));}
    break;

  case 13:
/* Line 1787 of yacc.c  */
#line 132 "joos.y"
    {(yyval.stringconst) = NULL;}
    break;

  case 14:
/* Line 1787 of yacc.c  */
#line 134 "joos.y"
    {(yyval.stringconst) = (yyvsp[(2) - (2)].stringconst);}
    break;

  case 15:
/* Line 1787 of yacc.c  */
#line 138 "joos.y"
    {(yyval.type) = makeTYPEref((yyvsp[(1) - (1)].stringconst));}
    break;

  case 16:
/* Line 1787 of yacc.c  */
#line 140 "joos.y"
    {(yyval.type) = makeTYPEchar();}
    break;

  case 17:
/* Line 1787 of yacc.c  */
#line 142 "joos.y"
    {(yyval.type) = makeTYPEbool();}
    break;

  case 18:
/* Line 1787 of yacc.c  */
#line 144 "joos.y"
    {(yyval.type) = makeTYPEint();}
    break;

  case 19:
/* Line 1787 of yacc.c  */
#line 148 "joos.y"
    {(yyval.field) = NULL;}
    break;

  case 20:
/* Line 1787 of yacc.c  */
#line 150 "joos.y"
    {(yyval.field) = (yyvsp[(1) - (1)].field);}
    break;

  case 21:
/* Line 1787 of yacc.c  */
#line 154 "joos.y"
    {(yyval.field) = (yyvsp[(1) - (1)].field);}
    break;

  case 22:
/* Line 1787 of yacc.c  */
#line 156 "joos.y"
    {(yyval.field) = appendFIELD((yyvsp[(2) - (2)].field),(yyvsp[(1) - (2)].field));}
    break;

  case 23:
/* Line 1787 of yacc.c  */
#line 160 "joos.y"
    {(yyval.field) = makeFIELDlist((yyvsp[(3) - (4)].id),(yyvsp[(2) - (4)].type));}
    break;

  case 24:
/* Line 1787 of yacc.c  */
#line 164 "joos.y"
    {(yyval.id) = makeID((yyvsp[(1) - (1)].stringconst),NULL);}
    break;

  case 25:
/* Line 1787 of yacc.c  */
#line 166 "joos.y"
    {(yyval.id) = makeID((yyvsp[(3) - (3)].stringconst),(yyvsp[(1) - (3)].id));}
    break;

  case 26:
/* Line 1787 of yacc.c  */
#line 170 "joos.y"
    {(yyval.constructor) = (yyvsp[(1) - (1)].constructor);}
    break;

  case 27:
/* Line 1787 of yacc.c  */
#line 172 "joos.y"
    {(yyval.constructor) = (yyvsp[(2) - (2)].constructor); (yyval.constructor)->next = (yyvsp[(1) - (2)].constructor);}
    break;

  case 28:
/* Line 1787 of yacc.c  */
#line 177 "joos.y"
    {(yyval.constructor) = makeCONSTRUCTOR((yyvsp[(2) - (13)].stringconst),(yyvsp[(4) - (13)].formal),
                                    makeSTATEMENTsequence(
                                        makeSTATEMENTsupercons((yyvsp[(9) - (13)].argument)),
                                        (yyvsp[(12) - (13)].statement)
                                    ),
                                    NULL
                    );}
    break;

  case 29:
/* Line 1787 of yacc.c  */
#line 187 "joos.y"
    {(yyval.constructor) = (yyvsp[(1) - (1)].constructor);}
    break;

  case 30:
/* Line 1787 of yacc.c  */
#line 189 "joos.y"
    {(yyval.constructor) = (yyvsp[(2) - (2)].constructor); (yyval.constructor)->next = (yyvsp[(1) - (2)].constructor);}
    break;

  case 31:
/* Line 1787 of yacc.c  */
#line 193 "joos.y"
    {(yyval.constructor) = makeCONSTRUCTOR((yyvsp[(2) - (6)].stringconst),(yyvsp[(4) - (6)].formal),NULL,NULL);}
    break;

  case 32:
/* Line 1787 of yacc.c  */
#line 197 "joos.y"
    {(yyval.formal) = NULL;}
    break;

  case 33:
/* Line 1787 of yacc.c  */
#line 199 "joos.y"
    {(yyval.formal) = (yyvsp[(1) - (1)].formal);}
    break;

  case 34:
/* Line 1787 of yacc.c  */
#line 203 "joos.y"
    {(yyval.formal) = (yyvsp[(1) - (1)].formal);}
    break;

  case 35:
/* Line 1787 of yacc.c  */
#line 205 "joos.y"
    {(yyval.formal) = (yyvsp[(3) - (3)].formal); (yyval.formal)->next = (yyvsp[(1) - (3)].formal);}
    break;

  case 36:
/* Line 1787 of yacc.c  */
#line 209 "joos.y"
    {(yyval.formal) = makeFORMAL((yyvsp[(2) - (2)].stringconst),(yyvsp[(1) - (2)].type),NULL);}
    break;

  case 37:
/* Line 1787 of yacc.c  */
#line 213 "joos.y"
    {(yyval.method) = NULL;}
    break;

  case 38:
/* Line 1787 of yacc.c  */
#line 215 "joos.y"
    {(yyval.method) = (yyvsp[(1) - (1)].method);}
    break;

  case 39:
/* Line 1787 of yacc.c  */
#line 219 "joos.y"
    {(yyval.method) = (yyvsp[(1) - (1)].method);}
    break;

  case 40:
/* Line 1787 of yacc.c  */
#line 221 "joos.y"
    {(yyval.method) = (yyvsp[(2) - (2)].method); (yyval.method)->next = (yyvsp[(1) - (2)].method);}
    break;

  case 41:
/* Line 1787 of yacc.c  */
#line 225 "joos.y"
    {(yyval.method) = makeMETHOD((yyvsp[(4) - (10)].stringconst),(yyvsp[(2) - (10)].modifier),(yyvsp[(3) - (10)].type),(yyvsp[(6) - (10)].formal),(yyvsp[(9) - (10)].statement),NULL);}
    break;

  case 42:
/* Line 1787 of yacc.c  */
#line 227 "joos.y"
    {(yyval.method) = makeMETHOD((yyvsp[(3) - (9)].stringconst),noneMod,(yyvsp[(2) - (9)].type),(yyvsp[(5) - (9)].formal),(yyvsp[(8) - (9)].statement),NULL);}
    break;

  case 43:
/* Line 1787 of yacc.c  */
#line 229 "joos.y"
    {(yyval.method) = makeMETHOD((yyvsp[(4) - (8)].stringconst),abstractMod,(yyvsp[(3) - (8)].type),(yyvsp[(6) - (8)].formal),NULL,NULL);}
    break;

  case 44:
/* Line 1787 of yacc.c  */
#line 231 "joos.y"
    {(yyval.method) = makeMETHOD("main",staticMod,makeTYPEvoid(),NULL,(yyvsp[(9) - (10)].statement),NULL);}
    break;

  case 45:
/* Line 1787 of yacc.c  */
#line 235 "joos.y"
    {(yyval.modifier) = finalMod;}
    break;

  case 46:
/* Line 1787 of yacc.c  */
#line 237 "joos.y"
    {(yyval.modifier) = synchronizedMod;}
    break;

  case 47:
/* Line 1787 of yacc.c  */
#line 241 "joos.y"
    {if (strcmp((yyvsp[(1) - (4)].stringconst),"String")!=0) yyerror("type String expected");}
    break;

  case 48:
/* Line 1787 of yacc.c  */
#line 243 "joos.y"
    {if (strcmp((yyvsp[(1) - (4)].stringconst),"String")!=0) yyerror("type String expected");}
    break;

  case 49:
/* Line 1787 of yacc.c  */
#line 247 "joos.y"
    {(yyval.method) = NULL;}
    break;

  case 50:
/* Line 1787 of yacc.c  */
#line 249 "joos.y"
    {(yyval.method) = (yyvsp[(1) - (1)].method);}
    break;

  case 51:
/* Line 1787 of yacc.c  */
#line 253 "joos.y"
    {(yyval.method) = (yyvsp[(1) - (1)].method);}
    break;

  case 52:
/* Line 1787 of yacc.c  */
#line 255 "joos.y"
    {(yyval.method) = (yyvsp[(2) - (2)].method); (yyval.method)->next = (yyvsp[(1) - (2)].method);}
    break;

  case 53:
/* Line 1787 of yacc.c  */
#line 259 "joos.y"
    {(yyval.method) = makeMETHOD((yyvsp[(4) - (8)].stringconst),(yyvsp[(2) - (8)].modifier),(yyvsp[(3) - (8)].type),(yyvsp[(6) - (8)].formal),NULL,NULL);}
    break;

  case 54:
/* Line 1787 of yacc.c  */
#line 261 "joos.y"
    {(yyval.method) = makeMETHOD((yyvsp[(3) - (7)].stringconst),noneMod,(yyvsp[(2) - (7)].type),(yyvsp[(5) - (7)].formal),NULL,NULL);}
    break;

  case 55:
/* Line 1787 of yacc.c  */
#line 265 "joos.y"
    {(yyval.modifier) = finalMod;}
    break;

  case 56:
/* Line 1787 of yacc.c  */
#line 267 "joos.y"
    {(yyval.modifier) = abstractMod;}
    break;

  case 57:
/* Line 1787 of yacc.c  */
#line 269 "joos.y"
    {(yyval.modifier) = synchronizedMod;}
    break;

  case 58:
/* Line 1787 of yacc.c  */
#line 274 "joos.y"
    {(yyval.type) = makeTYPEvoid();}
    break;

  case 59:
/* Line 1787 of yacc.c  */
#line 276 "joos.y"
    {(yyval.type) = (yyvsp[(1) - (1)].type);}
    break;

  case 60:
/* Line 1787 of yacc.c  */
#line 280 "joos.y"
    {(yyval.statement) = NULL;}
    break;

  case 61:
/* Line 1787 of yacc.c  */
#line 282 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 62:
/* Line 1787 of yacc.c  */
#line 286 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 63:
/* Line 1787 of yacc.c  */
#line 288 "joos.y"
    {(yyval.statement) = makeSTATEMENTsequence((yyvsp[(1) - (2)].statement),(yyvsp[(2) - (2)].statement));}
    break;

  case 64:
/* Line 1787 of yacc.c  */
#line 292 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 65:
/* Line 1787 of yacc.c  */
#line 294 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 66:
/* Line 1787 of yacc.c  */
#line 296 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 67:
/* Line 1787 of yacc.c  */
#line 298 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 68:
/* Line 1787 of yacc.c  */
#line 300 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 69:
/* Line 1787 of yacc.c  */
#line 302 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 70:
/* Line 1787 of yacc.c  */
#line 306 "joos.y"
    {(yyval.statement) = makeSTATEMENTlocal(makeLOCALlist((yyvsp[(2) - (3)].id),(yyvsp[(1) - (3)].type)));}
    break;

  case 71:
/* Line 1787 of yacc.c  */
#line 310 "joos.y"
    {(yyval.statement) = makeSTATEMENTskip();}
    break;

  case 72:
/* Line 1787 of yacc.c  */
#line 312 "joos.y"
    {(yyval.statement) = makeSTATEMENTblock((yyvsp[(2) - (3)].statement));}
    break;

  case 73:
/* Line 1787 of yacc.c  */
#line 314 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 74:
/* Line 1787 of yacc.c  */
#line 316 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 75:
/* Line 1787 of yacc.c  */
#line 320 "joos.y"
    {(yyval.statement) = makeSTATEMENTif((yyvsp[(3) - (5)].exp),(yyvsp[(5) - (5)].statement));}
    break;

  case 76:
/* Line 1787 of yacc.c  */
#line 324 "joos.y"
    {(yyval.statement) = makeSTATEMENTifelse((yyvsp[(3) - (7)].exp),(yyvsp[(5) - (7)].statement),(yyvsp[(7) - (7)].statement));}
    break;

  case 77:
/* Line 1787 of yacc.c  */
#line 328 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 78:
/* Line 1787 of yacc.c  */
#line 330 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 79:
/* Line 1787 of yacc.c  */
#line 332 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 80:
/* Line 1787 of yacc.c  */
#line 334 "joos.y"
    {(yyval.statement) = (yyvsp[(1) - (1)].statement);}
    break;

  case 81:
/* Line 1787 of yacc.c  */
#line 339 "joos.y"
    {(yyval.statement) = makeSTATEMENTifelse((yyvsp[(3) - (7)].exp),(yyvsp[(5) - (7)].statement),(yyvsp[(7) - (7)].statement));}
    break;

  case 82:
/* Line 1787 of yacc.c  */
#line 343 "joos.y"
    {(yyval.statement) = makeSTATEMENTwhile((yyvsp[(3) - (5)].exp),(yyvsp[(5) - (5)].statement));}
    break;

  case 83:
/* Line 1787 of yacc.c  */
#line 347 "joos.y"
    {(yyval.statement) = makeSTATEMENTwhile((yyvsp[(3) - (5)].exp),(yyvsp[(5) - (5)].statement));}
    break;

  case 84:
/* Line 1787 of yacc.c  */
#line 359 "joos.y"
    {(yyval.statement) = makeSTATEMENTsequence((yyvsp[(3) - (9)].statement),makeSTATEMENTwhile((yyvsp[(5) - (9)].exp),makeSTATEMENTsequence((yyvsp[(9) - (9)].statement),(yyvsp[(7) - (9)].statement))));}
    break;

  case 85:
/* Line 1787 of yacc.c  */
#line 364 "joos.y"
    {(yyval.statement) = makeSTATEMENTsequence((yyvsp[(3) - (9)].statement),makeSTATEMENTwhile((yyvsp[(5) - (9)].exp),makeSTATEMENTsequence((yyvsp[(9) - (9)].statement),(yyvsp[(7) - (9)].statement))));}
    break;

  case 86:
/* Line 1787 of yacc.c  */
#line 368 "joos.y"
    { (yyval.statement) = NULL; }
    break;

  case 87:
/* Line 1787 of yacc.c  */
#line 369 "joos.y"
    { (yyval.statement) = (yyvsp[(1) - (1)].statement); }
    break;

  case 88:
/* Line 1787 of yacc.c  */
#line 371 "joos.y"
    { (yyval.statement) = makeSTATEMENTexp((yyvsp[(1) - (1)].exp)); }
    break;

  case 89:
/* Line 1787 of yacc.c  */
#line 373 "joos.y"
    { (yyval.statement) = makeSTATEMENTsequence((yyvsp[(1) - (3)].statement), makeSTATEMENTexp((yyvsp[(3) - (3)].exp))); }
    break;

  case 90:
/* Line 1787 of yacc.c  */
#line 375 "joos.y"
    { (yyval.exp) = makeEXPboolconst(1); }
    break;

  case 91:
/* Line 1787 of yacc.c  */
#line 376 "joos.y"
    { (yyval.exp) = (yyvsp[(1) - (1)].exp); }
    break;

  case 92:
/* Line 1787 of yacc.c  */
#line 387 "joos.y"
    {(yyval.exp) = makeEXPassign((yyvsp[(1) - (2)].stringconst), makeEXPplus(makeEXPid((yyvsp[(1) - (2)].stringconst)),makeEXPintconst(1))); }
    break;

  case 93:
/* Line 1787 of yacc.c  */
#line 396 "joos.y"
    {(yyval.statement) = makeSTATEMENTexp((yyvsp[(1) - (2)].exp));}
    break;

  case 94:
/* Line 1787 of yacc.c  */
#line 400 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 95:
/* Line 1787 of yacc.c  */
#line 402 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 96:
/* Line 1787 of yacc.c  */
#line 404 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 97:
/* Line 1787 of yacc.c  */
#line 405 "joos.y"
    { (yyval.exp) = (yyvsp[(1) - (1)].exp); }
    break;

  case 98:
/* Line 1787 of yacc.c  */
#line 409 "joos.y"
    {(yyval.statement) = makeSTATEMENTreturn((yyvsp[(2) - (3)].exp));}
    break;

  case 99:
/* Line 1787 of yacc.c  */
#line 413 "joos.y"
    {(yyval.exp) = NULL;}
    break;

  case 100:
/* Line 1787 of yacc.c  */
#line 415 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 101:
/* Line 1787 of yacc.c  */
#line 419 "joos.y"
    {(yyval.exp) = makeEXPassign((yyvsp[(1) - (3)].stringconst),(yyvsp[(3) - (3)].exp));}
    break;

  case 102:
/* Line 1787 of yacc.c  */
#line 423 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 103:
/* Line 1787 of yacc.c  */
#line 425 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 104:
/* Line 1787 of yacc.c  */
#line 429 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 105:
/* Line 1787 of yacc.c  */
#line 431 "joos.y"
    {(yyval.exp) = makeEXPor((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 106:
/* Line 1787 of yacc.c  */
#line 435 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 107:
/* Line 1787 of yacc.c  */
#line 437 "joos.y"
    {(yyval.exp) = makeEXPand((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 108:
/* Line 1787 of yacc.c  */
#line 441 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 109:
/* Line 1787 of yacc.c  */
#line 443 "joos.y"
    {(yyval.exp) = makeEXPeq((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 110:
/* Line 1787 of yacc.c  */
#line 445 "joos.y"
    {(yyval.exp) = makeEXPneq((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 111:
/* Line 1787 of yacc.c  */
#line 449 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 112:
/* Line 1787 of yacc.c  */
#line 451 "joos.y"
    {(yyval.exp) = makeEXPlt((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 113:
/* Line 1787 of yacc.c  */
#line 453 "joos.y"
    {(yyval.exp) = makeEXPgt((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 114:
/* Line 1787 of yacc.c  */
#line 455 "joos.y"
    {(yyval.exp) = makeEXPleq((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 115:
/* Line 1787 of yacc.c  */
#line 457 "joos.y"
    {(yyval.exp) = makeEXPgeq((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 116:
/* Line 1787 of yacc.c  */
#line 459 "joos.y"
    {(yyval.exp) = makeEXPinstanceof((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].stringconst));}
    break;

  case 117:
/* Line 1787 of yacc.c  */
#line 463 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 118:
/* Line 1787 of yacc.c  */
#line 465 "joos.y"
    {(yyval.exp) = makeEXPplus((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 119:
/* Line 1787 of yacc.c  */
#line 467 "joos.y"
    {(yyval.exp) = makeEXPminus((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 120:
/* Line 1787 of yacc.c  */
#line 473 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 121:
/* Line 1787 of yacc.c  */
#line 475 "joos.y"
    {(yyval.exp) = makeEXPtimes((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 122:
/* Line 1787 of yacc.c  */
#line 477 "joos.y"
    {(yyval.exp) = makeEXPdiv((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 123:
/* Line 1787 of yacc.c  */
#line 479 "joos.y"
    {(yyval.exp) = makeEXPmod((yyvsp[(1) - (3)].exp),(yyvsp[(3) - (3)].exp));}
    break;

  case 124:
/* Line 1787 of yacc.c  */
#line 483 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 125:
/* Line 1787 of yacc.c  */
#line 485 "joos.y"
    {(yyval.exp) = makeEXPuminus((yyvsp[(2) - (2)].exp));}
    break;

  case 126:
/* Line 1787 of yacc.c  */
#line 490 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 127:
/* Line 1787 of yacc.c  */
#line 492 "joos.y"
    {(yyval.exp) = makeEXPnot((yyvsp[(2) - (2)].exp));}
    break;

  case 128:
/* Line 1787 of yacc.c  */
#line 494 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 129:
/* Line 1787 of yacc.c  */
#line 497 "joos.y"
    {if ((yyvsp[(2) - (4)].exp)->kind!=idK) yyerror("identifier expected");
                  (yyval.exp) = makeEXPcast((yyvsp[(2) - (4)].exp)->val.idE.name,(yyvsp[(4) - (4)].exp));}
    break;

  case 130:
/* Line 1787 of yacc.c  */
#line 500 "joos.y"
    {(yyval.exp) = makeEXPcharcast((yyvsp[(4) - (4)].exp));}
    break;

  case 131:
/* Line 1787 of yacc.c  */
#line 504 "joos.y"
    {(yyval.exp) = makeEXPid((yyvsp[(1) - (1)].stringconst));}
    break;

  case 132:
/* Line 1787 of yacc.c  */
#line 506 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 133:
/* Line 1787 of yacc.c  */
#line 510 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 134:
/* Line 1787 of yacc.c  */
#line 512 "joos.y"
    {(yyval.exp) = makeEXPthis();}
    break;

  case 135:
/* Line 1787 of yacc.c  */
#line 514 "joos.y"
    {(yyval.exp) = (yyvsp[(2) - (3)].exp);}
    break;

  case 136:
/* Line 1787 of yacc.c  */
#line 516 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 137:
/* Line 1787 of yacc.c  */
#line 518 "joos.y"
    {(yyval.exp) = (yyvsp[(1) - (1)].exp);}
    break;

  case 138:
/* Line 1787 of yacc.c  */
#line 522 "joos.y"
    {(yyval.exp) = makeEXPnew((yyvsp[(2) - (5)].stringconst),(yyvsp[(4) - (5)].argument));}
    break;

  case 139:
/* Line 1787 of yacc.c  */
#line 526 "joos.y"
    {(yyval.exp) = makeEXPinvoke((yyvsp[(1) - (6)].receiver),(yyvsp[(3) - (6)].stringconst),(yyvsp[(5) - (6)].argument));}
    break;

  case 140:
/* Line 1787 of yacc.c  */
#line 530 "joos.y"
    {(yyval.receiver) = makeRECEIVERobject(makeEXPid((yyvsp[(1) - (1)].stringconst)));}
    break;

  case 141:
/* Line 1787 of yacc.c  */
#line 532 "joos.y"
    {(yyval.receiver) = makeRECEIVERobject((yyvsp[(1) - (1)].exp));}
    break;

  case 142:
/* Line 1787 of yacc.c  */
#line 534 "joos.y"
    {(yyval.receiver) = makeRECEIVERsuper();}
    break;

  case 143:
/* Line 1787 of yacc.c  */
#line 538 "joos.y"
    {(yyval.argument) = NULL;}
    break;

  case 144:
/* Line 1787 of yacc.c  */
#line 540 "joos.y"
    {(yyval.argument) = (yyvsp[(1) - (1)].argument);}
    break;

  case 145:
/* Line 1787 of yacc.c  */
#line 544 "joos.y"
    {(yyval.argument) = makeARGUMENT((yyvsp[(1) - (1)].exp),NULL);}
    break;

  case 146:
/* Line 1787 of yacc.c  */
#line 546 "joos.y"
    {(yyval.argument) = makeARGUMENT((yyvsp[(3) - (3)].exp),(yyvsp[(1) - (3)].argument));}
    break;

  case 147:
/* Line 1787 of yacc.c  */
#line 550 "joos.y"
    {(yyval.exp) = makeEXPintconst((yyvsp[(1) - (1)].intconst));}
    break;

  case 148:
/* Line 1787 of yacc.c  */
#line 552 "joos.y"
    {(yyval.exp) = makeEXPboolconst((yyvsp[(1) - (1)].boolconst));}
    break;

  case 149:
/* Line 1787 of yacc.c  */
#line 554 "joos.y"
    {(yyval.exp) = makeEXPcharconst((yyvsp[(1) - (1)].charconst));}
    break;

  case 150:
/* Line 1787 of yacc.c  */
#line 556 "joos.y"
    {(yyval.exp) = makeEXPstringconst((yyvsp[(1) - (1)].stringconst));}
    break;

  case 151:
/* Line 1787 of yacc.c  */
#line 558 "joos.y"
    {(yyval.exp) = makeEXPnull();}
    break;


/* Line 1787 of yacc.c  */
#line 2742 "y.tab.c"
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}


