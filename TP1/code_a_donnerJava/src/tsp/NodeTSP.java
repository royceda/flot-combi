package tsp;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

//import tsp.Arc;
import branchAndBound.Node;
import tsp.Arc;

public class NodeTSP implements Node<List<Integer>> {
    //    List<Arc> arcs;
    boolean[][] allowed; //matrice d'interdit
    Node child;
    Node child1;
    Node child2;
    double[][] mc;
    double[][] reg;
    double lb;
    double value;



    
    public static double infinity = 1E308;
    public static double reflb    = infinity;
 

    /** to create the first node ==> root note */
    public NodeTSP(double[][] matrix) {
	/*TODO*/
	infinity = matrix[1][1];
	System.out.println("   on construit un noeud Racine");

	mc = matrix;
	value = 0;

	int n = matrix.length;

	//LB
	double[][]    tmp = matrix;
	LowerBoundTSP lbt = new LowerBoundTSP();
	lb = lbt.lowerBoundValue(tmp);

	
	//if (isLeaf())
	//  if(lb<reflb){
		reflb = lb;
		//value = lb;
		//  }

	//else if (isFeasible()){//amelioration possible
	    allowed  = new boolean[n][n];
	    reg      = new double[n][n];
	    	    
	    /*Some init*/
	    for(int i = 0; i < mc.length; i++){
		for(int j = 0; j < mc.length; j++){
		    allowed[i][j] = true;
		    reg[i][j] = 0;
		}
	    }
	    
	    /*traitement de la matrice courante en ligne*/
	    for(int i = 0; i < mc.length; i++){
		double minim = minTabLine(i);
		value += minim;
		for(int j = 0; j < mc.length; j++){
		    if( i!=j)
			mc[i][j] -= minim;
		 }		
	    }
	    
	    /*traitement de la matrice courante en colonne*/
	    for(int i = 0; i < mc.length; i++){
		double minim2 = minTabRow(i);
		value += minim2;
		for(int j = 0; j < mc.length; j++){
		    if( i!=j)
			mc[j][i] -= minim2;
		}
	    }

	    /*Traitement des regrets 1/2 */
	    for(int i = 0; i < mc.length; i++){
		double minim = minTabLine(i);
		for(int j = 0; j < mc.length; j++){
		    if(mc[i][j] == 0)
		    reg[i][j] = minim;
		}
	    }

	    /*Traitement des regrets 2/2 */
	    for(int i = 0; i < mc.length; i++){
		double minim2 = minTabRow(i);
		for(int j = 0; j < mc.length; j++){
		    if(mc[j][i] == 0)
			reg[j][i] += minim2;
		}
	    }

	    /*Selection*/
	    int    maxi   = 0;
	    int    maxj   = 0;
	    double maxreg = 0.0;

	    for(int i=0; i< mc.length; i++){
		for(int j=0; j< mc.length; j++){
		    if(reg[i][j] > maxreg && isAllowed(i,j)) {
			maxreg = reg[i][j];
			maxi = i;
			maxj = j;
		    }
		}
	    }
	    
	    /*fils*/
	    child1 = new NodeTSP(this, maxi, maxj, true);
	    child2 = new NodeTSP(this, maxi, maxj, false);
	    //	}
	    //	else{
	    //	    child  = null;
	    //	}
    }

    /** useful to create the children */
    private NodeTSP(NodeTSP father, int u, int v, boolean selected) {
	System.out.println("     on construit un noeud fils");
	allowed = father.allowed;
	mc = father.mc;

	
	if(!selected){
	    lb = father.getLB() + father.reg[u][v];
	    //	    mc[u][v] = infinity;
	    allowed[u][v] = false;

	    if(isLeaf()){
		if(lb<reflb)
		    reflb = lb;
	    }
	    
	    else if(isFeasible() && !isLeaf()) {
		child1 = new NodeTSP(mc);
	    }
	    else{//fin
		

	    }
	}
	else{
	    lb = father.getLB();

	    for(int i= 0; i< mc.length; i++){
		for(int j=0; j< mc.length; j++){
		    if(i!= u && j!= v)
			mc[i][j] = father.mc[i][j];
		    else
			allowed[i][j] = false;
			//	mc[i][j] = infinity;
		}
	    }
	    LowerBoundTSP lbt = new LowerBoundTSP();
	    lb += lbt.lowerBoundValue(mc);
	    if(isLeaf()){
		if(lb<reflb)
		    reflb = lb;
	    }
	    else {
		father.child2 = new NodeTSP(father, u, v, false);
		//traitement
	    }
	}
    }
    

    public String toString() {
	String s = "NODE TSP\n";
	return s;
    }

    public boolean isAllowed(int u, int v){
	return allowed[u][v];
    }


    /**Simple recherche de minimum dans une ligne d'un tableau de double
     *
     */
    double minTabLine(int j){
	double[] tab   = mc[j];
	int      index = 0;
	double   min   = tab[0];
	int      n     = tab.length;
	
	for(int i = 0; i<n; ++i){
	    if(tab[i] < min){
		min   = tab[i];
		index = i;
	    }
	}
	//System.out.println("n = "+n+" \nindex: "+index);
	return min;
    }

    /**Simple recherche de minimum dans une colonne de tableau de double
     *
     */
    double minTabRow(int j){
	double[] tab   = mc[j];
	int      index = 0;
	double   min   = mc[0][j];
	int      n     = tab.length;
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
     * 
     *
     */
    public double getLB() {
	return lb;
    }
    
	
    public double getValue() {
	return value;
    }

    public List<Integer> getSolution() {
	List<Integer> listCustomers = new ArrayList<Integer>();
	/* TODO */
	return listCustomers;
    }

    public boolean isFeasible() {
	if( lb < reflb)
	    return true;
	else
	    return false;
    }

    public boolean isLeaf() {
	for(int i=0; i<mc.length; i++){
	    for(int j=0; j<mc.length; j++){
		if(mc[i][j]>0 && isAllowed(i,j) )
		    return false;
	    }
	}
	return true;
    }

    public boolean hasNextChild() {
	/*	if( child1 == null && child2 == null)
	    return true;
	else
	return false;*/
	return false;
    }

    public Node<List<Integer>> getNextChild() {
	//Node<List<Integer>> child = null;
	/*	if(hasNextChild()){
	    if(child1 != null)
		return child1;
	    else
		return child2;
	}
	else
	return null;*/

	return null;
    }
}
