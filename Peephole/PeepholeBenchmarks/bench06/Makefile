all:
	$(PEEPDIR)/joosc *.java

clean:
	rm -f *.j *.class

test: clean count run

count:
	# WITHOUT OPTIMIZATIONS
	$(PEEPDIR)/joosc *.java
	wc -l *.j
	# WITH OPTIMIZATIONS
	$(PEEPDIR)/joosc -O *.java
	wc -l *.j

run:
	java Main < in1
