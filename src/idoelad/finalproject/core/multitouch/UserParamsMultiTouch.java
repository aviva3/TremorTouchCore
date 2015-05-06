package idoelad.finalproject.core.multitouch;

public class UserParamsMultiTouch {
	private double wFirst;
	private double wMaxPressure;
	private double wMaxTime;
	private double wStructure;
	public UserParamsMultiTouch(double wFirst, double wMaxPressure,
			double wMaxTime, double wStructure) {
		this.wFirst = wFirst;
		this.wMaxPressure = wMaxPressure;
		this.wMaxTime = wMaxTime;
		this.wStructure = wStructure;
	}
	public double getwFirst() {
		return wFirst;
	}
	public void setwFirst(double wFirst) {
		this.wFirst = wFirst;
	}
	public double getwMaxPressure() {
		return wMaxPressure;
	}
	public void setwMaxPressure(double wMaxPressure) {
		this.wMaxPressure = wMaxPressure;
	}
	public double getwMaxTime() {
		return wMaxTime;
	}
	public void setwMaxTime(double wMaxTime) {
		this.wMaxTime = wMaxTime;
	}
	public double getwStructure() {
		return wStructure;
	}
	public void setwStructure(double wStructure) {
		this.wStructure = wStructure;
	}

	
}
