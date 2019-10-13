package chapter_01;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ScoreCollectionTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void answerArithmeticMeanOfTwoNumbers() {

		// arrange (준비)
		ScoreCollection collection = new ScoreCollection();

		collection.add(() -> 5);
		collection.add(() -> 7);

		// act (실행)
		int actualResult = collection.arithmeticMean();

		// assert (단언)
		assertThat(actualResult, equalTo(6));
	}
}
