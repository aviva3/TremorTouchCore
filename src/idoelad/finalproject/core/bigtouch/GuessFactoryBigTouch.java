package idoelad.finalproject.core.bigtouch;
import idoelad.finalproject.core.touch.Circle;
import idoelad.finalproject.core.touch.Consts;
import idoelad.finalproject.core.touch.Guess;
import idoelad.finalproject.core.touch.GuessFactory;
import idoelad.finalproject.core.touch.Point;
import idoelad.finalproject.core.touch.Touch;

import java.util.ArrayList;
import java.util.Collections;






public class GuessFactoryBigTouch extends GuessFactory{

	public static Guess time(ArrayList<Touch> touches, long start, long end){
		ArrayList<Touch> subTouches = subTime(touches, start, end);
		return avarage(subTouches);
	}

	public static Guess percntPressure(ArrayList<Touch> touches, double percentile){
		return avarage(pressurePercentile(touches, percentile));
	}

	public static Guess maxRadius(ArrayList<Touch> touches, double radius){
		return avarage(underMaxRadius(touches,radius));
	}

	public static Guess onlyDown(ArrayList<Touch> touches){
		return avarage(onlyDownTouches(touches));
	}



	////////  Filters /////////////
	private static ArrayList<Touch> subTime(ArrayList<Touch> touches, long start, long end){
		ArrayList<Touch> subTouches = new ArrayList<Touch>();
		for (Touch touch : touches){
			if (touch.getTimeSinceStart() >= end){
				break;
			}
			if (touch.getTimeSinceStart() >= start){
				subTouches.add(touch);
			}
		}
		return subTouches;
	}

	private static ArrayList<Touch> minPressure(ArrayList<Touch> touches, double minPressure){
		ArrayList<Touch> subTouches = new ArrayList<Touch>();
		for (Touch t : touches){
			if (t.getPressure() >= minPressure){
				subTouches.add(t);
			}
		}
		return subTouches;
	}


	private static ArrayList<Touch> pressurePercentile(ArrayList<Touch> touches, double percentile){
		int i;
		double sum, tot;
		int n = touches.size();

		Collections.sort(touches);
		for (i=0, sum=0; i<n; i++) sum += touches.get(i).getPressure();
		tot = sum;
		for (i=0, sum=0; i<n && sum <= percentile*tot; i++) sum += touches.get(i).getPressure();
		double minPressure = touches.get(i-1).getPressure();
		return minPressure(touches, minPressure);
	}


	private static ArrayList<Touch> underMaxRadius(ArrayList<Touch> touches, double radius){
		ArrayList<Touch> subTouches = new ArrayList<Touch>();
		double totX = 0;
		double totY = 0;

		for (Touch touch : touches){
			totX += touch.getPoint().getX();
			totY += touch.getPoint().getY();
		}

		double xAv = totX/touches.size();
		double yAv = totY/touches.size();

		for (Touch touch : touches){
			if (touch.getPoint().getX() > xAv + radius) continue;
			if (touch.getPoint().getY() > yAv + radius) continue;
			subTouches.add(touch);
		}
		return subTouches;
	}



	private static ArrayList<Touch> onlyDownTouches(ArrayList<Touch> touches) {
		ArrayList<Touch> subTouches = new ArrayList<Touch>();
		for (Touch touch : touches){
			if (touch.getType().equals("DOWN")){
				subTouches.add(touch);
			}
		}
		return subTouches;
	}


}
