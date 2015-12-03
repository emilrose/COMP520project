# assumes sablecc script is on your PATH

# to compile the code
joosc: clean
	$(PEEPDIR)/joosc *.java

java: clean
	javac *.java

check:	
	./check.py


test: clean count run

clean:	
	rm -rf *.class *.j

count:
	# WITHOUT OPTIMIZATIONS
	$(PEEPDIR)/joosc *.java
	wc -l *.j
	# WITH OPTIMIZATIONS
	$(PEEPDIR)/joosc -O *.java
	wc -l *.j

run:
	java Main < in1
