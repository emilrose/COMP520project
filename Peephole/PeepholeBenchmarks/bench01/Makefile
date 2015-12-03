all: clean
	$(PEEPDIR)/joosc BacktrackSolver.java SudokuSolver.java Benchmark.java

java:
	javac BacktrackSolver.java SudokuSolver.java Benchmark.java

clean:	
	rm -rf *.class *.j *~

test: clean count run

count:
	# WITHOUT OPTIMIZATIONS
	$(PEEPDIR)/joosc *.java
	wc -l *.j
	# WITH OPTIMIZATIONS
	$(PEEPDIR)/joosc -O *.java
	wc -l *.j

run:
	java Benchmark < in1


