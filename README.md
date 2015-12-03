# CS520 Group Project

This is a compiler for a subset of Go, targeting C.

This project was done in collaboration with Zoe Guan and Samuel Laferriere for the McGill course COMP520 - Compiler Design.

I worked primarily on the parser and AST, weeder, and type checker.

This project is provided publically as portfolio work with the permission of Laure Hendren. If you are a current student of COMP520, please do not copy this code or use it as a reference outside of the bounds of what Professor Hendren allows.
  
The Makefile and test scripts are in /src.  
  
To make the compiler, use the Makefile:  
$ make  
  
**MILESTONE 4:**  
  
To compile foo.go:  
$ ./run foo.go -flags  
  
Flags:  
1) "-dumpsymtab" prints foo.go's symbol table to foo.symtab  
2) "-pptype" pretty prints foo.go with type information to foo.pptype.go  
3) "-pp" pretty prints foo.go to foo.pretty.go  
4) "-codegen" generates C code and prints it to foo.c  
5) "-v" prints the version of the compiler (1.0)  
6) "-h" prints a menu of the possible flags  
  
**MILESTONE 3:**  
  
To generate C code for foo.go:  
$ ./run foo.go codegen  
  
To generate C code for programs in /programs/valid/codegen:  
$ ./run codegen  
  
**MILESTONE 2:**  
  
To parse and typecheck foo.go (the flags are optional):  
$ ./run foo.go -dumpsymtab -pptype  
  
**MILESTONE 1:**  
  
To parse foo.go and pretty print it to foo.pretty.go:  
$ ./run foo.go -pp  
  
**OTHER TESTING OPTIONS:**  
  
To test programs in /programs, use either "run_all" or "run" with one of the arguments listed below. "run_all" outputs file names and errors for all cases tested while "run" only outputs file names of failed test cases (i.e. when the compiler accepts an invalid program or fails to parse a valid program).  
  
To test all programs in /programs:  
$ ./run_all  
or  
$ ./run all  
  
To test all programs in /programs/valid or /programs/invalid:  
$ ./run_all valid  
$ ./run_all invalid  
or  
$ ./run valid  
$ ./run invalid  
  
To test all programs in /programs/invalid/types  
$ ./run_all types  
or  
$ ./run types  
  
To remove all pretty print files in /programs:  
$ make clean_pretty  
