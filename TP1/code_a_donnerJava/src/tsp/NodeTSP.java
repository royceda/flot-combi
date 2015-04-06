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
    public static int    size     = 0;

    /** to create the first node ==> root note */
    public NodeTSP(double[][] matrix) {
	/*TODO*/
	infinity = matrix[1][1];
	System.out.println("   on construit un noeud Racine");

	mc = matrix;
	value = 0;

	size = matrix.length;

	//LB
	double[][]    tmp = matrix;
	LowerBoundTSP lbt = new LowerBoundTSP();
	lb = lbt.lowerBoundValue(tmp);

	

	reflb = lb;

	allowed  = new boolean[size][size];
	reg      = new double[size][size];
	    	    
	/*Some init*/
	for(int i = 0; i < mc.length; i++){
	    for(int j = 0; j < mc.length; j++){
		allowed[i][j] = true;
		reg[i][j] = 0;
	    }
	}
	traitment();
    }

    /** useful to create the children */
    private NodeTSP(NodeTSP father, int u, int v, boolean selected) {
	System.out.println("     on construit un noeud fils");
	allowed = father.allowed;
	mc = father.mc;
	reg = father.reg;

	
	if(!selected){ 

	    lb = father.getLB() + father.reg[u][v];
	    mc[u][v] = infinity;
	    allowed[u][v] = false;

	    if(isLeaf() && lb < reflb)
		reflb = lb;
	    else if(isFeasible() && !isLeaf()) 
		traitment();
	    else{
		/*end*/
	    }
	}
	else{
	    /* selected*/

	    lb = father.getLB();

	    /*copy*/
	    for(int i= 0; i< mc.length; i++){
		for(int j=0; j< mc.length; j++){
		    //if(i!= u && j!= v)
			mc[i][j] = father.mc[i][j];
			/*else{
			allowed[i][j] = false;
			mc[i][j] = infinity;
			}*/
		}
	    }

	    /*interdiction*/
	    for(int i= 0; i< mc.length; i++){
		allowed[u][i] = false;
		allowed[i][v] = false;
	    }

	    LowerBoundTSP lbt = new LowerBoundTSP();
	    lb += lbt.lowerBoundValue(mc);
	    
	    if(isLeaf() && lb< reflb)
		reflb = lb;

	    traitment();   
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
	System.out.println("isleaf");
	for(int i=0; i<size; i++){
	    for(int j=0; j<size; j++){
		if( allowed[i][j] == true )
		    System.out.println("test is: "+allowed[i][j]);
		    return false;
	    }
	}
	System.out.println("test is: true");
	return true;
    }

    public boolean hasNextChild() {
	if( child1 == null && child2 == null)
	    return true;
	else
	    return false;	
    }

    public Node<List<Integer>> getNextChild() {
	//Node<List<Integer>> child = null;
	if(hasNextChild()){
	    if(child1 != null)
		return child1;
	    else
		return child2;
	}
	else
	    return null;
    }


    public void traitment(){
	
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
	if(this.isLeaf() == false){
	    child1 = new NodeTSP(this, maxi, maxj, true);
	    child2 = new NodeTSP(this, maxi, maxj, false);
	}
    }
}
