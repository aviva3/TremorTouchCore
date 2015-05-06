package idoelad.finalproject.core.touch;


public class Guess {
	private Circle circle;
	private int nTouches;
	public Guess(Circle circle, int nTouches) {
		super();
		this.circle = circle;
		this.nTouches = nTouches;
	}
	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	public int getnTouches() {
		return nTouches;
	}
	public void setnTouches(int nTouches) {
		this.nTouches = nTouches;
	}
	
	
}
