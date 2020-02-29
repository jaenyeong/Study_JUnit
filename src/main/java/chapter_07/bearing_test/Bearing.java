package chapter_07.bearing_test;

public class Bearing {
	public static final int MAX = 359;
	private int value;

	public Bearing(int value) {
		if (value < 0 || value > MAX) {
			throw new BearingOutOfRangeException();
		}
		this.value = value;
	}

	public int value() {
		return value;
	}

	public int angleBetween(Bearing bearing) {
		return value - bearing.value;
	}
}
