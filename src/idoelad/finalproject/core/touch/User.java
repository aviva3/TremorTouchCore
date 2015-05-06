package idoelad.finalproject.core.touch;
import idoelad.finalproject.core.bigtouch.UserParamsBigTouch;
import idoelad.finalproject.core.multitouch.UserParamsMultiTouch;

import java.util.ArrayList;




public class User {
	private String name;
	private ArrayList<Test> tests;
	private double avgTouchSize;
	private double totTouchSize = 0;
	private double totTouches = 0;
	private UserParamsBigTouch userParamsBigTouch;
	private UserParamsMultiTouch userParamsMultiTouch;
	
	public User(String name) {
		this.name = name;
		tests = new ArrayList<Test>();
	}
	
	public void addTests(ArrayList<Test> newTests){
		for (Test t : newTests){
			tests.add(t);
			reCalcTouchSize(t);
		}
	}
	
	private void reCalcTouchSize(Test t){
		for (Touch touch : t.getTouches()){
			totTouches++;
			totTouchSize += touch.getSize()*Consts.TOUCH_SIZE_FACTOR;
		}
		avgTouchSize = totTouchSize/totTouches;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Test> getTests() {
		return tests;
	}

	public void setTests(ArrayList<Test> tests) {
		this.tests = tests;
	}

	public double getAvgTouchSize() {
		return avgTouchSize;
	}

	public void setAvgTouchSize(double avgTouchSize) {
		this.avgTouchSize = avgTouchSize;
	}

	public UserParamsBigTouch getUserParamsBigTouch() {
		return userParamsBigTouch;
	}

	public void setUserParamsBigTouch(UserParamsBigTouch userParams) {
		this.userParamsBigTouch = userParams;
	}

	public UserParamsMultiTouch getUserParamsMultiTouch() {
		return userParamsMultiTouch;
	}

	public void setUserParamsMultiTouch(UserParamsMultiTouch userParamsMultiTouch) {
		this.userParamsMultiTouch = userParamsMultiTouch;
	}
	
	
	
	
	
	
	
}
