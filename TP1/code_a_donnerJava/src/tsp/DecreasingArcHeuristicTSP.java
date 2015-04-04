package tsp;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Collections;
import tsp.Arc;
/**
 * This heuristic sorts the arcs by increasing value and 
 * considers each arc in turn for insertion
 * An arc is inserted if and only if it does not create a subtour.
 * The method stops when a tour is obtained.
 */

public class DecreasingArcHeuristicTSP implements HeuristicTSP{



	/** TODO coder cette méthode */
	public double computeSolution(double[][] matrix, List<Integer> solution) {
		double value = 0.0;
		int i,j;
		int n = matrix[1].length;
		int m = n*(n - 1);
		int min = 100000;
		//List<Point> tri_arcs = new List<>(); 
		//		for (i=0; i<
		//Point[] tab = new Point[m];
		ArrayList<Arc> list = new ArrayList<Arc>();
		for (i=0; i<n; i++) {
		    for (j= 0; j< n; j++) {
			if (i != j) {
			    list.add(new Arc(matrix[i][j], i, j));
			    Collections.sort(list);
			}
		    }
		}

		ArrayList<Arc> arclist = new ArrayList<Arc>();
		// //list.sort();
		// //ArrayList<Integer> list2 = newArrayList<Integer>();
		solution.add(list.get(0).getStart());
		solution.add(list.get(0).getFinish());
		value = list.get(0).getValue();
		Arc arc;
		int a = 1;
		int k=0;
		while(list.size() != a && solution.size() != n){
		    arc = list.get(a);
		    for(i=0; i<arclist.size(); i++){
			if(arclist.get(i).getStart() == arc.getStart())
			    k=1;
		    }
		    if(!solution.contains(arc.getFinish()) && k==0) {
			solution.add(arc.getFinish());
			arclist.add(arc);
			value += arc.getValue();
		    }
		    a= a+1;
		    k =0;
		}
		return value;
	}

}
