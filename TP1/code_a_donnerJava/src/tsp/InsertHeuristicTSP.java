package tsp;
import java.util.List;

/**
 * 
 * This heuristic iteratively appends a customer
 * to the current solution until a tour is obtained
 *
 */

public class InsertHeuristicTSP implements HeuristicTSP {

    /**Simple recherche de minimum dans un tableau de double
     *
     */
    int minTab(double[] tab){

	int    index = 0;
	double min   = tab[0];
	
	for(int i = 0; i<tab.length; ++i){
	    if(tab[i] < min){
		min   = tab[i];
		index = i;
	    }
	}
	return index;
    }
    


    /** TODO : coder cette méthode 
     *
     *
     *
     */
    public double computeSolution(double[][] matrix, List<Integer> solution) {

	double    value   = 0.0;
	int       n       = matrix[0].length;		
	double    min     = 100000000;
	int       k       = -1;
	int       etat    = 3;
	double[]  values  = new double[n];
	boolean[] witness = new boolean[n];

	for(int i = 0; i < n; ++i)
	    witness[i] = false;

	solution.add(0);

	//Thread? chacun fait 20 iter
	for(etat = 0; etat < n; etat++){ //pour tous
	    for(int i = 0; i < n; ++i){ //on itere au nombre de noeuds
		for(int j = 0; j < n; j++){ //recherche du plus proche
		    if(matrix[etat][j] < min && etat != j && !witness[j]){
			min = matrix[etat][j];
			k = j;
			witness[k] = true;
		    }
		    
		}
		
		
		value     += min;
		min        = 10000000;
		etat       = k;
	    }
	    for(int i = 0; i < n; ++i)
		witness[i] = false;

	    values[etat] = value;	    
	}

	/* reprendre le chemin qui marche le mieu*/
	etat = minTab(values);

	for(int i = 0; i < n; ++i){ //on itere au nombre de noeuds
	    for(int j = 0; j < n; j++){ //recherche du plus proche
		if(matrix[etat][j] < min && etat != j && !solution.contains(j)){
		    min = matrix[etat][j];
		    k = j;
		}
	    }
	    
	    solution.add(k);
	    value += min;
	    min    = 10000000;
	    etat   = k;
	}
	
	return value;
    }
}
