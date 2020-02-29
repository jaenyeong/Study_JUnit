package chapter_07.transmission_test;

public class Transmission {
	private Gear gear;
	private Moveable moveable;

	public Transmission(Moveable moveable) {
		this.moveable = moveable;
	}

	public void shift(Gear gear) {
		if (moveable.currentSpeedInMph() > 0 && gear == Gear.PARK) {
			return;
		}

		this.gear = gear;
	}

	public Gear getGear() {
		return gear;
	}
}
