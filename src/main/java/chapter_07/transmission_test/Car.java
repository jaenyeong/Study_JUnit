package chapter_07.transmission_test;

public class Car implements Moveable {
	private int mph;

	@Override
	public int currentSpeedInMph() {
		return mph;
	}

	public void accelerateTo(int mph) {
		this.mph = mph;
	}

	public void brakeToStop() {
		mph = 0;
	}
}
