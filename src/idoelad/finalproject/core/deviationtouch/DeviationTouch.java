package idoelad.finalproject.core.deviationtouch;

import idoelad.finalproject.core.touch.Circle;
import idoelad.finalproject.core.touch.TouchType;

import java.util.ArrayList;
import java.util.Collections;


public class DeviationTouch extends TouchType {
	
	public static final double X_TRSHOLD = 150.0; //TODO get from calibration?
	public static final double Y_TRSHOLD = 150.0;

	public static UserParamsDeviationTouch getDeviationTouchParams(ArrayList<TargertNGuess> tngs){
		
		//Calc avg. deviation
		double totXDev = 0;
		double totYDev = 0;
		
		int xCount = 0;
		int yCount = 0;
		
		//Weight should be calculated according to the sparsing of the deviation
		ArrayList<Double> xDists = new ArrayList<Double>();
		ArrayList<Double> yDists = new ArrayList<Double>();
		
		for (TargertNGuess tng : tngs){
			double[] dev = getDev(tng.getTarget(),tng.getGuess());
			if (Math.abs(dev[0]) <=X_TRSHOLD){
				totXDev += dev[0];
				xCount++;
				xDists.add(dev[0]);
			}
			if (Math.abs(dev[1]) <=Y_TRSHOLD){
				totYDev += dev[1];
				yCount++;
				yDists.add(dev[1]);
			}
		}
		
		double xDev = totXDev/(double)xCount;
		double yDev = totYDev/(double)yCount;

		
		//Calc weights
		double xWeight = calcWeight(xDists);
		double yWeight = calcWeight(yDists);
		
		if (xWeight < 0 || yWeight < 0 || tngs.size() < 2){ //To much sparse 
			xWeight = 0;
			yWeight = 0;
		}
		
		return new UserParamsDeviationTouch(xDev, yDev, xWeight, yWeight);
		
	}
	
	private static double[] getDev(Circle target, Circle guess){
		double[] dev = new double[2];
		dev[0] = guess.getCenter().getX() - target.getCenter().getX();
		dev[1] = guess.getCenter().getY() - target.getCenter().getY();
		return dev;
	}
	
	private static double calcWeight(ArrayList<Double> dists){
		if (dists.isEmpty()){
			return -1;
		}
		Collections.sort(dists);
		double avgDist = avgDist(dists);
		double weight = getWeight(avgDist);
		return weight > 0 ? weight : -1;
	}
	
	private static double avgDist(ArrayList<Double> dists){
		if (dists.isEmpty()){
			return 0;
		}
		
		double totDist = 0;
		
		double first = dists.get(0);
		for (int i=1;i<dists.size();i++){
			totDist += Math.abs(dists.get(i) - dists.get(i-1));
		}
		return totDist/(double)dists.size();
	}
	

	private static double getWeight(double avgDist){
		double a = 10.0 - avgDist;
		if (a <= 0 ){
			return -1;
		}
		return a/10.0;
	}
	
	public static double[] getNewLocation(double x, double y, UserParamsDeviationTouch upDev){
		double[] newLocation = new double[2];
		newLocation[0] = x + upDev.getxDev()*upDev.getxWeight();
		newLocation[1] = y + upDev.getyDev()*upDev.getyWeight();
		return newLocation;
	}
	
}
