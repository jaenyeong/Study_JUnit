package chapter_06;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ScoreCollectionTest {
	private ScoreCollection collection;

	@Before
	public void create() {
		collection = new ScoreCollection();
	}

	@Test
	public void answersArithmeticMeanOfTwoNumbers() {
		collection.add(() ->  5);
		collection.add(() ->  7);

		int actualResult = collection.arithmeticMean();

		assertThat(actualResult, equalTo(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsExceptionWhenAddingNull() {
		collection.add(null);
	}

	@Test
	public void answersZeroWhenNoElementsAdded() {
		assertThat(collection.arithmeticMean(), equalTo(0));
	}

	@Test
	public void dealsWithIntegerOverflow() {
		collection.add(() -> Integer.MAX_VALUE);
		collection.add(() -> 1);

		int expectedValue = 1073741824;
		// Integer.MAX_VALUE의 값은 2147483647
		// 따라서 2147483647에서 1을 더한 값은 1073741824보다 커야함
		// 해결하기 위해 arithmeticMean 메소드의 연산을 long 타입으로 연산 후 int 타입으로 다운 캐스팅

		assertThat(collection.arithmeticMean(), equalTo(expectedValue));
	}

	@Test
	public void doesNotProperlyHandlerIntegerOverflow() {
		collection.add(() -> Integer.MAX_VALUE);
		collection.add(() -> 1);

		assertTrue(collection.arithmeticMean() < 0);
	}
}
