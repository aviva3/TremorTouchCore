package idoelad.finalproject.core.bigtouch;


public class UserParamsBigTouch {
	private double wAvg;
	private double wDown;
	private double wTime;
	private double wPressure;
	private double wRadius;
	
	private long timeStart;
	private long timeEnd;
	private double pressurePercentage;
	private double maxRadius;
	
	
	public UserParamsBigTouch(double wAvg, double wDown, double wTime,
					  double wPressure, double wRadius, long timeStart, long timeEnd,
					  double pressurePercentage, double maxRadius) {
		this.wAvg = wAvg;
		this.wDown = wDown;
		this.wTime = wTime;
		this.wPressure = wPressure;
		this.wRadius = wRadius;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.pressurePercentage = pressurePercentage;
		this.maxRadius = maxRadius;
	}

	public double getwAvg() {
		return wAvg;
	}

	public void setwAvg(double wAvg) {
		this.wAvg = wAvg;
	}

	public double getwDown() {
		return wDown;
	}

	public void setwDown(double wDown) {
		this.wDown = wDown;
	}

	public double getwTime() {
		return wTime;
	}

	public void setwTime(double wTime) {
		this.wTime = wTime;
	}

	public double getwPressure() {
		return wPressure;
	}

	public void setwPressure(double wPressure) {
		this.wPressure = wPressure;
	}

	public double getwRadius() {
		return wRadius;
	}

	public void setwRadius(double wRadius) {
		this.wRadius = wRadius;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public double getPressurePercentage() {
		return pressurePercentage;
	}

	public void setPressurePercentage(int pressurePercentage) {
		this.pressurePercentage = pressurePercentage;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setMaxRadius(int maxRadius) {
		this.maxRadius = maxRadius;
	}
	
	
	
	
	
}
