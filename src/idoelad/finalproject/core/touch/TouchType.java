package idoelad.finalproject.core.touch;

import java.util.ArrayList;

public abstract class TouchType {
	
	protected static ArrayList<Double> getWeights(ArrayList<Double> values){
		ArrayList<Double> weights = new ArrayList<Double>();
		
		double invSum = 0;
		for (double d : values){
			invSum += (1.0/d);
		}
		for (double d : values){
			weights.add((1.0/d)/invSum);
		}
		return weights;
	}
}
