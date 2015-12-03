all: clean
	$(PEEPDIR)/joosc *.java

java:
	javac *.java

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
	java Game < in1


