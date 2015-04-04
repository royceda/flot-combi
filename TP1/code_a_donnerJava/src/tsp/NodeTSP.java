package tsp;

import java.util.List;

import branchAndBound.Node;

public class NodeTSP implements Node<List<Integer>> {


    /** to create the first node ==> root note */
    public NodeTSP(double[][] matrix) {
	/*TODO*/
	Node<List<Integer>> list= new Node<List<Integer>>;
	int n = matrix[0].size();
	for(int i=0; i< n; i++){
	    for(int j; j< n; j++){
		if(i != j){
		    list.add(new NodeTSP(this, i, j, true));
		    list.add(new NodeTSP(this, j, i, true));
		}
	    }		
	}
	
    }

    /** useful to create the children */
    private NodeTSP(NodeTSP father, int u, int v, boolean selected) {
	Node<List<Integer>> mylist = new List<Integer>;
	mylist.add(new NodeTSP(this, u+1, v, true));
	mylist.add(new NodeTSP(this, u+1, v, false));
	return mylist;

    }


    public String toString() {
	String s = "NODE TSP\n";

	return s;
    }

    public double getLB() {
	/*TODO*/

	return 0;
    }

	
    public double getValue() {
	/*TODO*/



	return -1;
    }

    public List<Integer> getSolution() {
	List<Integer> listCustomers = new ArrayList<Integer>();
	/* TODO */




	return listCustomers;
    }

    public boolean isFeasible() {
	return false;
    }

    public boolean isLeaf() {
	return true;
    }

    public boolean hasNextChild() {
	return false;
    }

    public Node<List<Integer>> getNextChild() {
	Node<List<Integer>> child = null;
	return child;
    }

}
