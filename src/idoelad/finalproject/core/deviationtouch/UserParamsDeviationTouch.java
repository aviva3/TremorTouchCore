package idoelad.finalproject.core.deviationtouch;

public class UserParamsDeviationTouch {
	private double xDev;
	private double yDev;
	private double xWeight;
	private double yWeight;
	public UserParamsDeviationTouch(double xDev, double yDev, double xWeight,
			double yWeight) {
		super();
		this.xDev = xDev;
		this.yDev = yDev;
		this.xWeight = xWeight;
		this.yWeight = yWeight;
	}
	public double getxDev() {
		return xDev;
	}
	public void setxDev(double xDev) {
		this.xDev = xDev;
	}
	public double getyDev() {
		return yDev;
	}
	public void setyDev(double yDev) {
		this.yDev = yDev;
	}
	public double getxWeight() {
		return xWeight;
	}
	public void setxWeight(double xWeight) {
		this.xWeight = xWeight;
	}
	public double getyWeight() {
		return yWeight;
	}
	public void setyWeight(double yWeight) {
		this.yWeight = yWeight;
	}
	
	
	
	
}
