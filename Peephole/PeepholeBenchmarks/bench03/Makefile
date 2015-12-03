clean:
	rm *.class *.j

test: count run

count:
	$(PEEPDIR)/joosc *.java *.joos
	wc -l *.j
	$(PEEPDIR)/joosc -O *.java *.joos
	wc -l *.j

run:
	java Main < in1