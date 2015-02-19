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
	//System.out.println("n = "+n+" \nindex: "+index);
	return index;
    }
    


    int plusProcheDispo(double[] tab, List<Integer> solution){
      	int    index = 0;
	double min   = tab[0];
	int    n     = tab.length;
	
	for(int i = 1; i<n; ++i){
	    if(tab[i] < min && !solution.contains(i)){
		min   = tab[i];
		index = i;
	    }
	}
	//System.out.println("n = "+n+" \nindex: "+index);
	return index;
    }


    /** 
     *
     *
     */
    public double method1(int sommet, double[][] matrix, List<Integer> solution){
	double    value   = 0.0;
	int       tmp     = 0;
	int       n       = matrix[0].length;		
	int       etat    = sommet;

	solution.clear();
	solution.add(0);
	for(int i = 0; i < n; ++i){
	    tmp = plusProcheDispo(matrix[etat], solution);
	    solution.add(etat);
	    value += matrix[etat][tmp];
	    etat = tmp;
	    System.out.println(i+"   etape: "+etat);
	}
	solution.add(0);
	return value;
    }

    /**
     *
     *
     */
    public double method2(double[][] matrix, List<Integer> solution){
	
	int n = matrix[0].length;
	double[] values = new double[n];
	int min = 0;

	solution.clear();

	for(int i = 0; i<n; ++i)
	    values[i] = method1(i, matrix, solution);
	
	
	min = minTab(values);
	return method1(min, matrix, solution);
    }
    

    /** TODO : coder cette méthode 
     *
     *
     *
     */
    public double computeSolution(double[][] matrix, List<Integer> solution) {
	
	return method2(matrix, solution);
	
    }
}
