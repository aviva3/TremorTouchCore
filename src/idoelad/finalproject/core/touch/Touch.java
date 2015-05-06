package idoelad.finalproject.core.touch;

public class Touch implements Comparable<Touch>{
	private Point point;
	private String type;
	private double size;
	private double pressure;
	private long timeSinceStart;
	private int numOfPointers;
	private int pointerId;
	
	public Touch(Point point, String type, double size, double pressure,
			long timeSinceStart, int numOfPointers, int pointerId) {
		this.point = point;
		this.type = type;
		this.size = size;
		this.pressure = pressure;
		this.timeSinceStart = timeSinceStart;
		this.numOfPointers = numOfPointers;
		this.pointerId = pointerId;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public long getTimeSinceStart() {
		return timeSinceStart;
	}
	public void setTimeSinceStart(long timeSinceStart) {
		this.timeSinceStart = timeSinceStart;
	}
	
	
	public int getNumOfPointers() {
		return numOfPointers;
	}
	public void setNumOfPointers(int numOfPointers) {
		this.numOfPointers = numOfPointers;
	}
		
	public int getPointerId() {
		return pointerId;
	}
	public void setPointerId(int pointerId) {
		this.pointerId = pointerId;
	}
	
	@Override
	public int compareTo(Touch o) {
		if (this.pressure > o.getPressure()){
			return 1;
		}
		if (this.pressure < o.getPressure()){
			return -1;
		}
		return 0;
	}
	
	
	
}
