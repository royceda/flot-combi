
all: cbuild

cbuild: arc
	javac -cp bin -sourcepath src -d bin src/tsp/MainTSP.java
	javac -cp bin -sourcepath src -d bin src/tsp/LowerBoundTSP.java

test: test-h1 test-h2

test-h1: cbuild
	java -cp bin/ tsp.MainTSP -h1 ../data/instances/kroD100.tsp
	test Berlin
	java -cp bin/ tsp.MainTSP -h1 ../data/instances/berlin52.tsp


test-h2: cbuild
	java -cp bin/ tsp.MainTSP -h2 ../data/instances/kroD100.tsp
	test Berlin
	java -cp bin/ tsp.MainTSP -h2 ../data/instances/berlin52.tsp

test-l1: cbuild
	java -cp bin/ tsp.MainTSP -l1 ../data/instances/kroD100.tsp
	test Berlin
	java -cp bin/ tsp.MainTSP -l1 ../data/instances/berlin52.tsp

test-e: cbuild
	java -cp bin/ tsp.MainTSP -e ../data/instances/kroD100.tsp 2
	test Berlin
	java -cp bin/ tsp.MainTSP -e ../data/instances/berlin52.tsp 2
	

arc:
	javac -cp bin -sourcepath src -d bin src/tsp/Arc.java
