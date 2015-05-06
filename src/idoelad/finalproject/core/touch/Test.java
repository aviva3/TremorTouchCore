package idoelad.finalproject.core.touch;
import java.util.ArrayList;

public class Test {
	private Circle circle;
	private ArrayList<Touch> touches;
	private String type;
	public Test(Circle circle) {
		this.circle = circle;
		this.touches = new ArrayList<Touch>();
	}

	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	public ArrayList<Touch> getTouches() {
		return touches;
	}
	public void setTouches(ArrayList<Touch> touches) {
		this.touches = touches;
	}
	
	public void addTouch(Touch t){
		touches.add(t);
	}
	
	
	
	
}
