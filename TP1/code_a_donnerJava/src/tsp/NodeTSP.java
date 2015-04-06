package tsp;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

//import tsp.Arc;
import branchAndBound.Node;
import tsp.Arc;

public class NodeTSP implements Node<List<Integer>> {
    List<Arc> arcs;
    boolean[][] interdit; //matrice d'interdit
    Node child;
    Node child1;
    Node child2;
    double[][] mc;
    double[][] reg;
    double lb;




    public static double infinity = 0;


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
	infinity = matrix[1][1];
	System.out.println("   on construit un noeud Racine");

	int n = matrix.length;

	mc = matrix;
	double[][] tmp = matrix;
	LowerBoundTSP lbt = new LowerBoundTSP();
	lb = lbt.lowerBoundValue(tmp);

	arcs = new ArrayList<>();
	interdit = new boolean[n][n];
	
	reg = new double[n][n];


	for(int i = 0; i < mc.length; i++){
	    for(int j = 0; j < mc.length; j++){
		interdit[i][j] = false;
		reg[i][j] = 0;
	    }
	}

	/*traitement de la matrice*/
	for(int i = 0; i < mc.length; i++){
	    double minim = minTabLine(i);
	    for(int j = 0; j < mc.length; j++){
		if( i!=j)
		    mc[i][j] -= minim;
		if(mc[i][j] == 0)
		    reg[i][j] = minim;
	    }
	}


	for(int i = 0; i < mc.length; i++){
	    double minim2 = minTabRow(i);
	    for(int j = 0; j < mc.length; j++){
		if( i!=j)
		    mc[j][i] -= minim2;
		if(mc[j][i] == 0)
		    reg[j][i] += minim2;
	    }
	}

	int maxi = 0;
	int maxj = 0;
	double maxreg = 0.0;
	for(int i=0; i< mc.length; i++){
	    for(int j=0; j< mc.length; j++){
		if(reg[i][j] > maxreg) {
		    maxreg = reg[i][j];
		    maxi = i;
		    maxj = j;
		}
	    }
	}

	child1 = new NodeTSP(this, maxi, maxj, true);
	child2 = new NodeTSP(this, maxi, maxj, false);
    }





    /** useful to create the children */
    private NodeTSP(NodeTSP father, int u, int v, boolean selected) {
	//System.out.println("on construit un noeud fils");
	interdit = father.interdit;
	mc = father.mc;

	if(!selected){
	    lb = father.getLB() + father.reg[u][v];
	}
	else{
	    lb = father.getLB();
	    for(int i= 0; i< mc.length; i++){
		for(int j=0; j< mc.length; j++){
		    if(i!= u && j!= v)
			mc[i][j] = father.mc[i][j];
		    else
			mc[i][j] = infinity;
		}
	    }
	    Node child = new NodeTSP(mc);
	    
	}
	
	
	/*	arcs = father.arcs;
	mc = father.mc;
	if (selected){
	    interdit = father.interdit;
	    interdit[u][v] = true;
	    Arc first = new Arc(mc[u][v], u, v);
	    arcs.add(first);
	    
	    for( int i = 0; i < mc.length; ++i)
		for( int j = 0; j < mc.length; ++j)
		    if(i != j && !interdit[i][j] && i != u && j != v){
			//modif mc
			mc[i][v] = infinity;
			mc[u][j] = infinity;
			//test de circuit < n ?
			
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
    double minTabLine(int j){
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



    /**Simple recherche de minimum dans un tableau de double
     *
     */
    double minTabRow(int j){
	
	
	double[] tab = mc[j];

	int    index = 0;
	double min   = mc[0][j];
	int    n     = tab.length;
	
	for(int i = 0; i<n; ++i){
	    if(tab[i] < min){
		min   = mc[i][j];
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
