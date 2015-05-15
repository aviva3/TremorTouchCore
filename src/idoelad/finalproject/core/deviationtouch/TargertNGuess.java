package idoelad.finalproject.core.deviationtouch;

import idoelad.finalproject.core.touch.Circle;


public class TargertNGuess {
	private Circle target;
	private Circle guess;
	public Circle getTarget() {
		return target;
	}
	public void setTarget(Circle target) {
		this.target = target;
	}
	public Circle getGuess() {
		return guess;
	}
	public void setGuess(Circle guess) {
		this.guess = guess;
	}
	public TargertNGuess(Circle target, Circle guess) {
		super();
		this.target = target;
		this.guess = guess;
	}
	
	
}
