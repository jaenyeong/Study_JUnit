package chapter_06;

import org.junit.Test;

import static java.lang.Math.abs;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class NewtonTest {
	static class Newton {
		private static final double TOLERANCE = 1E-16;

		public static double squareRoot(double n) {
			double approx = n;
			while (abs(approx - n / approx) > TOLERANCE * approx) {
				approx = (n / approx + approx) / 2.0;
			}
			return approx;
		}
	}

	@Test
	public void squareRoot() {
		double result = Newton.squareRoot(250.0);
		assertThat(result * result, closeTo(250.0, Newton.TOLERANCE));
	}

	@Test
	public void squareRootVerifiedUsingLibrary() {
		assertThat(
				Newton.squareRoot(1969.0),
				closeTo(Math.sqrt(1969.0),
						Newton.TOLERANCE)
		);
	}
}
