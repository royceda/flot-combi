package tsp;

public class Arc implements Comparable<Arc>{
    private double value;
    private int start;
    private int finish;

    public Arc(double val, int st, int fin){
	value = val;
	start = st;
	finish = fin;
    }

    public double getValue(){
	return value;
    }
    
    public int getStart() {
	return start;
    }

    public int getFinish(){
	return finish;
    }

    public int compareTo(Arc arc){
	if (value < arc.getValue())
	    return -1;
	else if (value > arc.getValue())
	    return 1;
	else 
	    return 0;
    }


    public String toString(){
        return "Arc going from "+start+" to "+finish+" weight: "+value;
    }
}
