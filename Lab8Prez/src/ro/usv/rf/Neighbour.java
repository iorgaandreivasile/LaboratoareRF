package ro.usv.rf;


public class Neighbour {
	public Neighbour(int position, double value, String className) {
		this.position = position;
		this.value = value;
		this.className = className;

	}

	private int position = 0;
	private double value = 0;
	private String className = null;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getClass_num() {
		return className;
	}

	public void setClass_num(String className) {
		this.className = className;
	}

}