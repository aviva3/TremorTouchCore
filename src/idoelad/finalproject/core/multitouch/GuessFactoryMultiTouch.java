package idoelad.finalproject.core.multitouch;

import idoelad.finalproject.core.touch.Circle;
import idoelad.finalproject.core.touch.Consts;
import idoelad.finalproject.core.touch.Guess;
import idoelad.finalproject.core.touch.GuessFactory;
import idoelad.finalproject.core.touch.Point;
import idoelad.finalproject.core.touch.Touch;

import java.util.ArrayList;
import java.util.Collections;

public class GuessFactoryMultiTouch extends GuessFactory {

	public static Guess firstTouch(ArrayList<Touch> touches){
		Touch firstTouch = touches.get(0);
		Circle circle = new Circle(firstTouch.getPoint(), firstTouch.getSize()*Consts.TOUCH_SIZE_FACTOR);
		return new Guess(circle,1);
	}
	
	public static Guess maxAvgPressure(ArrayList<Touch> touches){
		int maxPointerId = getMaxAvgPressurePointer(touches);
		return avarage(getPointerTouches(touches, maxPointerId));
	}
	
	
	public static Guess maxTime(ArrayList<Touch> touches){
		int maxPointerId = getMaxTimePointer(touches);
		return avarage(getPointerTouches(touches, maxPointerId));
	}
	
	
	public static Guess structure(ArrayList<Touch> touches){
		int selectedPointerId = getStructurePointer(touches);
		return avarage(getPointerTouches(touches, selectedPointerId));
	}

	//////////////////////// find Pointer /////////////////
	public static int getMaxAvgPressurePointer(ArrayList<Touch> touches){
		int maxPointer = getMaxPointer(touches);
		double[] totPressures = new double[maxPointer+1];
		int[] pointersCount = new int[maxPointer+1];

		for (Touch t : touches){
			int pointerId = t.getPointerId();
			pointersCount[pointerId]++;
			totPressures[pointerId] += t.getPressure();
		}
				
		int maxPointerId = 0;
		double maxAvg = 0.0;
		for (int i=1;i<pointersCount.length;i++){
			double avg = totPressures[i]/pointersCount[i];
			if (avg >= maxAvg){
				maxAvg = avg;
				maxPointerId = i;
			}
		}
		return maxPointerId;
	}
	
	public static int getMaxTimePointer(ArrayList<Touch> touches){
		int maxPointer = getMaxPointer(touches);
		long[] lastTime = new long[maxPointer+1];
		for (Touch t : touches){
			lastTime[t.getPointerId()] = t.getTimeSinceStart();
		}
		
		int maxPointerId = 0;
		long maxTime = 0;
		for (int i=1;i<lastTime.length;i++){
			if (lastTime[i] >= maxTime){
				maxTime = lastTime[i];
				maxPointerId = i;
			}
		}
		return maxPointerId;
	}
	
	public static int getStructurePointer(ArrayList<Touch> touches){
		ArrayList<MinXPointer> minXs = new ArrayList<MinXPointer>();
		MinXPointer zeroPointerId = new MinXPointer(0);
		zeroPointerId.setY(0.0);
		minXs.add(zeroPointerId);
		
		for (int i=1;i<=getMaxPointer(touches);i++){
			minXs.add(new MinXPointer(i));
		}
		
		if (minXs.size() == 2){ //Only one pointer
			return minXs.get(1).getPointerId();
		}
		
		
		for (Touch t : touches){
			int pointerId = t.getPointerId();
			double x = t.getPoint().getX();
			if (x < minXs.get(pointerId).getMinX()){
				minXs.get(pointerId).setMinX(x);
				minXs.get(pointerId).setY(t.getPoint().getY());
			}
		}
		
		Collections.sort(minXs);
		
		Point p1, p2;
		if (minXs.size() == 3){ //Only two pointer
			p1 = new Point(minXs.get(0).getMinX(), minXs.get(0).getY());
			p2 = new Point(minXs.get(1).getMinX(), minXs.get(1).getY());
			double dist = Point.distance(p1, p2);
			if (dist > Consts.THUMB_MIN_DIST){
				return minXs.get(1).getPointerId();
			}
			return minXs.get(0).getPointerId();
		}
		
		p1 = new Point(minXs.get(0).getMinX(), minXs.get(0).getY());
		double totDist = 0.0;
		for (int i=1;i<minXs.size()-1;i++){
			p2 = new Point(minXs.get(i).getMinX(), minXs.get(i).getY());
			totDist += Point.distance(p1, p2);
			p1 = p2;
		}
		
		double avgDist = totDist/(minXs.size()-1);
		
		
		//Get most left finger and 2nd most left finget
		double x0 = minXs.get(0).getMinX();
		double y0 = minXs.get(0).getY();
		double x1 = minXs.get(1).getMinX();
		double y1 = minXs.get(1).getY();
		
		Point p0d = new Point(x0, y0);
		Point p1d = new Point(x1, y1);

		int selectedPointerId;
		if (Point.distance(p0d, p1d) > avgDist*Consts.THUMB_FACTOR){ //TODO left handed?
			selectedPointerId = minXs.get(1).getPointerId();
		}
		else{
			selectedPointerId = minXs.get(0).getPointerId();
		}
		return selectedPointerId;
	}

	
	/////////////////////////////// Filters /////////////////////////
	private static ArrayList<Touch> getPointerTouches(ArrayList<Touch> touches, int pointerId){
		ArrayList<Touch> pTouches = new ArrayList<Touch>();
		for (Touch t : touches){
			if (t.getPointerId() == pointerId){
				pTouches.add(t);
			}
		}
		return pTouches;
	}
	
	/////////////////////////////// Helpers ///////////////////////
	
	public static int getMaxPointer(ArrayList<Touch> touches){

		int max = 0;
		int pointers;
		for (Touch t : touches){
			pointers = t.getPointerId();
			if (pointers > max){
				max = pointers;
			}
		}
		
		return max;
	}

	
}
