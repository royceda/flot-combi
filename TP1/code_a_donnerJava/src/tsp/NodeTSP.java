package tsp;

import java.util.List;
import java.util.ArrayList;

//import tsp.Arc;
import branchAndBound.Node;
import tsp.Arc;

public class NodeTSP implements Node<List<Integer>> {
    List<Arc> arcs;
    boolean[][] interdit; //matrice d'interdit
    Node child1;
    Node child2;
    double[][] mc;

    double infinity = 100000000E08;


    /**
     * Verifie si un arc est disponible
     *
     */ 
    public boolean ArcDispo(double[][] matrix, Arc arc){
	/*code*/

	return true;
    }



    /** to create the first node ==> root note */
    public NodeTSP(double[][] matrix) {
	/*TODO*/
	
	System.out.println("   on construit un noeud Racine");

	int n = matrix.length;

	mc = matrix;
	arcs = new ArrayList<>();
	interdit = new boolean[n][n];
	
	/*traitement de la mtrice*/
	for(int i = 0; i < mc.length; ++i){
	    for(int j = 0; j < mc.length; ++j)
		if( i!=j)
		    mc[i][j] -= minTab(i);
	}

	for( int i = 0; i < mc.length; ++i)
	    for( int j = 0; j < mc.length; ++j)
		if(i != j){		    
		    child1 = new NodeTSP(this, 1, 2, true);
		    child2 = new NodeTSP(this, 1, 2, false);
		    }	
    }





    /** useful to create the children */
    private NodeTSP(NodeTSP father, int u, int v, boolean selected) {
	//System.out.println("on construit un noeud fils");

	arcs = father.arcs;
	mc = father.mc;
	if (selected){
	    interdit = father.interdit;
	    interdit[u][v] = true;
	    Arc first = new Arc(mc[u][v], u, v);
	    arcs.add(first);
	    
	    for( int i = 0; i < mc.length; ++i)
		for( int j = 0; j < mc.length; ++j)
		    if(i != j && !interdit[i][j]){
			//modif mc
			mc[i][v] = infinity;
			mc[u][j] = infinity;
			
			child1 = new NodeTSP(this, i, j, true);
			child2 = new NodeTSP(this, i, j, false);
		    }
	}
	else {
	    int j;
	}
       

	


	/*
	  Node<List<Integer>> mylist = new List<Integer>;
	  mylist.add(new NodeTSP(this, u+1, v, true));
	  mylist.add(new NodeTSP(this, u+1, v, false));
	  return mylist;
	*/
    }


    public String toString() {
	String s = "NODE TSP\n";

	return s;
    }



    /**Simple recherche de minimum dans un tableau de double
     *
     */
    double minTab(int j){
	double[] tab = mc[j];
	int    index = 0;
	double min   = tab[0];
	int    n     = tab.length;
	
	for(int i = 0; i<n; ++i){
	    if(tab[i] < min){
		min   = tab[i];
		index = i;
	    }
	}
	//System.out.println("n = "+n+" \nindex: "+index);
	return min;
    }

    /**
     * Pour chaque ligne on prend le minimum et on addition (1ere etape)
     *
     */
    public double getLB() {
	double lb = 0;
	for(int i = 0; i < mc.length; ++i){
	    lb += minTab(i);
	} 
	return lb;
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
	if( child1 == null && child2 == null)
	    return false;
	else
	    return false;
    }

    public Node<List<Integer>> getNextChild() {
	Node<List<Integer>> child = null;
	return child;
    }

}
