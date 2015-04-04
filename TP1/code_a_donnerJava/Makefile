all: cbuild

cbuild: arc
	javac -cp bin -sourcepath src -d bin src/tsp/MainTSP.java
	javac -cp bin -sourcepath src -d bin src/tsp/LowerBoundTSP.java

test: test-h1 test-h2

test-h1: cbuild
	java -cp bin/ tsp.MainTSP -h1 ../data/instances/kroD100.tsp
	echo -o "test Berlin"
	java -cp bin/ tsp.MainTSP -h2 ../data/instances/berlin52.tsp


test-h2: cbuild
	java -cp bin/ tsp.MainTSP -h2 ../data/instances/kroD100.tsp
	echo -o "Test Berlin"
	java -cp bin/ tst.MainTSP -h2 ../data/instaces/berlin52.tsp

arc:
	javac -cp bin -sourcepath src -d bin src/tsp/Arc.java
