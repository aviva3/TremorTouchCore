package idoelad.finalproject.core.multitouch;

public class MinXPointer implements Comparable<MinXPointer> {
	private double minX;
	private int pointerId;
	private double y;
	
	public MinXPointer(int pointerId) {
		this.pointerId = pointerId;
		minX = Double.MAX_VALUE;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public int getPointerId() {
		return pointerId;
	}

	public void setPointerId(int pointerId) {
		this.pointerId = pointerId;
	}
	
	

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int compareTo(MinXPointer o) {
		if (this.minX < o.minX){
			return -1;
		}
		if (this.minX > o.minX){
			return 1;
		}
		return 0;
	}
	
	
	
	
}
