package chapter_06;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ProfileTest {
	private Profile profile;
//	private Criteria criteria;

	@Before
	public void createProfile() {
		profile = new Profile("Jaenyeong.dev");
	}

//	@Before
//	public void createCriteria() {
//		criteria = new Criteria();
//	}

	int[] ids(Collection<Answer> answers) {
		return answers.stream().mapToInt(a -> a.getQuestion().getId()).toArray();
	}

	@Test
	public void findsAnswersBasedOnPredicate() {
		profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
		profile.add(new Answer(new PercentileQuestion(2, "2", new String[]{}), 0));
		profile.add(new Answer(new PercentileQuestion(3, "3", new String[]{}), 0));

		List<Answer> answers = profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);
		assertThat(ids(answers), equalTo(new int[]{2, 3}));

		List<Answer> answersComplement = profile.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);

		List<Answer> allAnswers = new ArrayList<>();
		allAnswers.addAll(answersComplement);
		allAnswers.addAll(answers);

		assertThat(ids(allAnswers), equalTo(new int[]{1, 2, 3}));
	}

	@Test
	public void findAnswers() {
		int dataSize = 5000;
		for (int i = 0; i < dataSize; i++) {
			profile.add(new Answer(new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
		}
		profile.add(new Answer(new PercentileQuestion(dataSize, String.valueOf(dataSize), new String[]{}), 0));

		int numberOfTimes = 1000;
		long elapsedMs = run(numberOfTimes,
				() -> profile.find(
						a -> a.getQuestion().getClass() == PercentileQuestion.class
				)
		);

		assertTrue(elapsedMs < 1000);
	}

	private long run(int times, Runnable func) {
		long start = System.nanoTime();
		for (int i = 0; i < times; i++) {
			func.run();
		}

		long stop = System.nanoTime();

		return (stop - start) / 1000000;
	}
}
