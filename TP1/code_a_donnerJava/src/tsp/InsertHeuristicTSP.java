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
	double min   = 100E9;
	int    n     = tab.length;

	for(int i = 0; i<n; ++i){
	    if(tab[i] < min){
		min   = tab[i];
		index = i;
	    }
	}
	System.out.println("n = "+n+" \nindex: "+index);
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
	double    min     = 100E9;
	int       k       = -1;
	int       etat    = 3;
	int       index   = 0;
	double[]  values  = new double[n];  // sauvegarde des valeurs de chemins
	boolean[] witness = new boolean[n]; //le sommet n a ete parcouru?


	
	solution.add(0);

	//Optimisation: 2 thread
	for(etat = 0; etat < n; ++etat){ //pour tous
	    for(int i = 0; i < n; ++i)
		witness[i] = true;
	    
	    witness[etat] = false;
	    for(int i = 0; i < n; ++i){ //on itere au nombre de noeuds
		min = matrix[i][0];
		for(int j = 0; j < n; j++){ //recherche du plus proche
		    if(matrix[etat][j] < min && etat != j && witness[j]){
			min = matrix[etat][j];
			k = j;
			witness[k] = false;
		    }
		}
		value     += min;
	    }
	    values[etat] = value;
	}

	for(int i = 0; i<n; ++i)
	    System.out.println(i+" value: "+values[i]);
	

	/* reprendre le chemin qui marche le mieu*/
	etat = minTab(values);
	value = 0.0;
	//etat = 2;
	for(int i = 0; i < n; ++i){ //on itere au nombre de noeuds
	    min = matrix[i][0];
	    for(int j = 0; j < n; j++){ //recherche du plus proche
		if(matrix[etat][j] < min && etat != j && !solution.contains(j)){
		    min = matrix[etat][j];
		    k = j;
		}
	    }
	    
	    solution.add(k);
	    value += min;
	    etat   = k;
	}
	
	return value;
    }
}
