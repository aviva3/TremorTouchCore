package idoelad.finalproject.core.multitouch;
import idoelad.finalproject.core.touch.Consts;
import idoelad.finalproject.core.touch.Guess;
import idoelad.finalproject.core.touch.Test;
import idoelad.finalproject.core.touch.Touch;
import idoelad.finalproject.core.touch.TouchType;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class MultiTouch extends TouchType{
	public static UserParamsMultiTouch getMultiTouchParams(ArrayList<Test> tests){

		double totDistFirst = 0.0;
		double totDistMaxPress = 0.0;
		double totDistMaxTime = 0.0;
		double totDistStructure = 0.0;
		
		for (Test test : tests){
			
			//First Touch
			Guess firstTouchGuess = GuessFactoryMultiTouch.firstTouch(test.getTouches());
			totDistFirst+= GuessFactoryMultiTouch.getDistanceFromCenter(test.getCircle(), firstTouchGuess.getCircle());

			Guess maxPressureGuess = GuessFactoryMultiTouch.maxAvgPressure(test.getTouches());
			totDistMaxPress+= GuessFactoryMultiTouch.getDistanceFromCenter(test.getCircle(), maxPressureGuess.getCircle());

			Guess maxTimeGuess = GuessFactoryMultiTouch.maxTime(test.getTouches());
			totDistMaxTime+= GuessFactoryMultiTouch.getDistanceFromCenter(test.getCircle(), maxTimeGuess.getCircle());

			Guess structureGuess = GuessFactoryMultiTouch.structure(test.getTouches());
			totDistStructure+= GuessFactoryMultiTouch.getDistanceFromCenter(test.getCircle(), structureGuess.getCircle());

		}

		double numOfTests = tests.size();
		double avgDistFirst = totDistFirst/numOfTests;
		double avgDistPress = totDistMaxPress/numOfTests;
		double avgDistTime = totDistMaxTime/numOfTests;
		double avgDistStructure = totDistStructure/numOfTests;

		ArrayList<Double> avgs = new ArrayList<Double>();
		avgs.add(avgDistFirst);
		avgs.add(avgDistPress);
		avgs.add(avgDistTime);
		avgs.add(avgDistStructure);

		ArrayList<Double> weights = getWeights(avgs);
		
		return new UserParamsMultiTouch(weights.get(0), weights.get(1), weights.get(2), weights.get(3));
	}

	public static int guessFingure(ArrayList<Touch> touches, UserParamsMultiTouch params){
		TreeMap<Integer, Double> probs = new TreeMap<Integer, Double>();
		for (int i=1;i<=GuessFactoryMultiTouch.getMaxPointer(touches);i++){
			probs.put(i, 0.0);
		}

		int first = touches.get(0).getPointerId();
		int press = GuessFactoryMultiTouch.getMaxAvgPressurePointer(touches);
		int time = GuessFactoryMultiTouch.getMaxTimePointer(touches);
		int structure = GuessFactoryMultiTouch.getStructurePointer(touches);
		
		if (Consts.DEBUG){
			System.out.print("|first = "+first);
			System.out.print("|press = "+press);
			System.out.print("|time = "+time);
			System.out.print("|structure = "+structure);
		}
		

		
		probs.put(first, probs.get(first)+params.getwFirst());
		probs.put(press, probs.get(press)+params.getwMaxPressure());
		probs.put(time, probs.get(time)+params.getwMaxTime());
		probs.put(structure, probs.get(structure)+params.getwStructure());
		
		Map.Entry<Integer, Double> maxEntry = null;

		for (Map.Entry<Integer, Double> entry : probs.entrySet())
		{
		    if (maxEntry == null || entry.getValue() > maxEntry.getValue())
		    {
		        maxEntry = entry;
		    }
		}
		return maxEntry.getKey();
	}
	
	public static ArrayList<Touch> filterTouchesByFinger(ArrayList<Touch> touches, int pointerId){
		ArrayList<Touch> fTouches = new ArrayList<Touch>();
		for (Touch t : touches){
			if (t.getPointerId() == pointerId){
				fTouches.add(t);
			}
		}
		return fTouches;
	}
	
}
