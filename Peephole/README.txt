
Setup:
export PEEPDIR=[path to /Peephole]
export CLASSPATH=.:$PEEPDIR/jooslib.jar:$CLASSPATH
export PATH=$PATH:$JOOSDIR


Add optimizations to JOOSA-src/patterns.h and make compiler. 


To test the optimizer, go into a benchmark folder (e.g. /PeepholeBenchmarks/bench01) and
$ make test
This outputs 3 things:
1) the line count for each .j file when compiled without peephole optimizations
2) the line count for each .j file when compiled with peephole optimizations
3) program output (exceptions mean there are unsound peephole patterns)

To output just 1) and 2) do:
$ make count 

