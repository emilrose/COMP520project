all: grammar code

grammar: golite.sablecc
	sablecc golite.sablecc

grammar2: golite.sablecc
	java -jar ~/sablecc-3.7/lib/sablecc.jar golite.sablecc

# if above doesn’t work use
# $ java -jar SABLECCDIR/lib/sablecc.jar golite.sablecc
# where SABLECCDIR is the path for sablecc-3.7

code:
	javac golite/lexer/*.java golite/parser/*.java golite/node/*.java golite/analysis/*.java golite/weeder/*.java golite/pretty/*.java golite/golite_lexer/*.java golite/symbol/*.java golite/codegen/*.java golite/Main.java

clean: clean_sablecc clean_pretty clean_symtable clean_c

clean_sablecc:
	rm -rf golite/lexer golite/parser golite/node golite/analysis 
	rm -f golite/*.class golite/weeder/*.class golite/pretty/*.class golite/golite_lexer/*.class golite/symbol/*.class golite/codegen/*.class

clean_pretty:
	rm -f ../programs/*/*.pretty.go
	rm -f ../programs/*/*/*.pretty.go
	rm -f ../programs/*/*.pptype.go
	rm -f ../programs/*/*/*.pptype.go
	rm -f *.pretty.go

clean_symtable:
	rm -f ../programs/valid/*.symtab
	rm -f ../programs/valid/*/*.symtab
	rm -f ../programs/invalid/*/*.symtab
	rm -f *.symtab

clean_c:
	rm -f ../programs/*/*.c
	rm -f ../programs/*/*/*.c
	rm -f *.c

