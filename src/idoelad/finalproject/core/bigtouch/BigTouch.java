package idoelad.finalproject.core.bigtouch;

import idoelad.finalproject.core.touch.Circle;
import idoelad.finalproject.core.touch.Consts;
import idoelad.finalproject.core.touch.Guess;
import idoelad.finalproject.core.touch.Point;
import idoelad.finalproject.core.touch.Test;
import idoelad.finalproject.core.touch.Touch;
import idoelad.finalproject.core.touch.TouchType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BigTouch extends TouchType{
	public static UserParamsBigTouch getBigTouchParams(ArrayList<Test> tests){

		double totDistAvg = 0;
		double totDistDown = 0;
		double totDistTime = 0;
		double totTime = 0;
		double totDistPress = 0;
		double totPressPerc = 0;
		double totDistRadius = 0;
		double totRadius = 0;

		for (Test test : tests){

			double distAvg = Double.MAX_VALUE;
			double distDown = Double.MAX_VALUE;
			double minDistTime = Double.MAX_VALUE;
			double bestTime = 0;
			double minDistPressure = Double.MAX_VALUE;
			double bestPercentage = 0;
			double minDistRadius = Double.MAX_VALUE;
			double bestRadius = 0;


			//Avg.
			Guess guessAvg = GuessFactoryBigTouch.avarage(test.getTouches());
			distAvg = GuessFactoryBigTouch.getDistanceFromCenter(test.getCircle(), guessAvg.getCircle());
			totDistAvg+=distAvg;

			//Down
			Guess guessDown = GuessFactoryBigTouch.onlyDown(test.getTouches());
			distDown= GuessFactoryBigTouch.getDistanceFromCenter(test.getCircle(), guessDown.getCircle());
			totDistDown+=distDown;

			//Time
			Guess guessTime;
			double distTime;
			for (int i=0;i<test.getTouches().size();i++){
				guessTime = GuessFactoryBigTouch.time(test.getTouches(),0,test.getTouches().get(i).getTimeSinceStart());
				if (guessTime == null){
					continue;
				}
				distTime = GuessFactoryBigTouch.getDistanceFromCenter(test.getCircle(), guessTime.getCircle());
				if (distTime < minDistTime){
					minDistTime = distTime;
					bestTime = test.getTouches().get(i).getTimeSinceStart();
				}
			}
			totDistTime+=minDistTime;
			totTime+=bestTime;


			//Above percentage pressure
			Guess guessPercPressure;
			double distPres;
			for (double p=0.1;p<1;p+=0.05){
				guessPercPressure = GuessFactoryBigTouch.percntPressure(test.getTouches(), p);
				distPres = GuessFactoryBigTouch.getDistanceFromCenter(test.getCircle(), guessPercPressure.getCircle());
				if (distPres < minDistPressure){
					minDistPressure = distPres;
					bestPercentage = p;
				}
			}
			totDistPress+=minDistPressure;
			totPressPerc+=bestPercentage;


			//Above Max radius
			Guess guessRadius;
			double distRadius;
			for (double r=5;r<500;r++){
				guessRadius = GuessFactoryBigTouch.maxRadius(test.getTouches(), r);
				if (guessRadius == null){
					continue;
				}
				distRadius = GuessFactoryBigTouch.getDistanceFromCenter(test.getCircle(), guessRadius.getCircle());
				if (distRadius < minDistRadius){
					minDistRadius = distRadius;
					bestRadius = r;
				}
			}
			totDistRadius+=minDistRadius;
			totRadius+=bestRadius;	
		}

		double numOfTests = tests.size();

		long bestTimeAvg = (long) (totTime/numOfTests);
		double bestPressAvg = totPressPerc/numOfTests;
		double bestRadiusAvg = Math.round(totRadius/numOfTests);

		double avgAvg = totDistAvg/numOfTests;
		double downAvg = totDistDown/numOfTests;
		double timeAvg = totDistTime/numOfTests;
		double pressAvg = totDistPress/numOfTests;
		double radiusAvg = totDistRadius/numOfTests;

		ArrayList<Double> avgs = new ArrayList<Double>();
		avgs.add(avgAvg);
		avgs.add(downAvg);
		avgs.add(timeAvg);
		avgs.add(pressAvg);
		avgs.add(radiusAvg);			

		ArrayList<Double> weights = getWeights(avgs);

		return new UserParamsBigTouch(weights.get(0), weights.get(1), weights.get(2), weights.get(3), weights.get(4), 0, bestTimeAvg, bestPressAvg, bestRadiusAvg);
	}


	public static Circle guessCircleBigTouch(ArrayList<Touch> touches, UserParamsBigTouch params){
		Guess gAvg = GuessFactoryBigTouch.avarage(touches);
		Guess gDown = GuessFactoryBigTouch.onlyDown(touches);
		Guess gTime =  GuessFactoryBigTouch.time(touches, params.getTimeStart(), params.getTimeEnd());
		Guess gPressure = GuessFactoryBigTouch.percntPressure(touches, params.getPressurePercentage());
		Guess gRadius = GuessFactoryBigTouch.maxRadius(touches, params.getMaxRadius());
		
		double totX = 0;
		double totY = 0;
		double totRad = 0;
		double totWeights = 0;

		if (gAvg != null){
			Circle avg = gAvg.getCircle();
			totX += avg.getCenter().getX()*params.getwAvg();
			totY += avg.getCenter().getY()*params.getwAvg();
			totRad += avg.getRadius()*params.getwAvg();
			totWeights += params.getwAvg();
		}

		if (gDown != null){
			Circle down = gDown.getCircle();
			totX += down.getCenter().getX()*params.getwDown();
			totY += down.getCenter().getY()*params.getwDown();
			totRad += down.getRadius()*params.getwDown();
			totWeights += params.getwDown();
		}

		if (gTime != null){
			Circle time = gTime.getCircle();
			totX += time.getCenter().getX()*params.getwTime();
			totY += time.getCenter().getY()*params.getwTime();
			totRad += time.getRadius()*params.getwAvg();
			totWeights += params.getwTime();
		}

		if (gPressure != null){
			Circle pressure = gPressure.getCircle();
			totX += pressure.getCenter().getX()*params.getwPressure();
			totY += pressure.getCenter().getY()*params.getwPressure();
			totRad += pressure.getRadius()*params.getwPressure();
			totWeights += params.getwPressure();
		}

		if (gRadius != null){
			Circle radius = gRadius.getCircle();
			totX += radius.getCenter().getX()*params.getwRadius();
			totY += radius.getCenter().getY()*params.getwRadius();
			totRad += radius.getRadius()*params.getwRadius();
			totWeights += params.getwRadius();
		}

		return new Circle(new Point(totX/totWeights, totY/totWeights), totRad/totWeights);
	}
}
