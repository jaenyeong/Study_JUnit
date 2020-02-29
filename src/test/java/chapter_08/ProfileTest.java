package chapter_08;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProfileTest {
	private Profile profile;
	private Criteria criteria;

	private Question questionIsThereRelocation;
	private Answer answerThereIsRelocation;
	private Answer answerThereIsNoRelocation;

	private Question questionReimbursesTuition;
	private Answer answerReimbursesTuition;
	private Answer answerDoesNotReimburseTuition;

	private Question questionOnsiteDaycare;
	private Answer answerNoOnsiteDaycare;
	private Answer answerHasOnsiteDaycare;

	@Before
	public void createProfile() {
		profile = new Profile("Jaenyeong.dev");
	}

	@Before
	public void createCriteria() {
		criteria = new Criteria();
	}

	@Before
	public void createQuestionsAndAnswers() {
		questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
		answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
		answerThereIsNoRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

		questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
		answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
		answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);

		questionOnsiteDaycare = new BooleanQuestion(1, "Onsite daycare?");
		answerHasOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.TRUE);
		answerNoOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.FALSE);
	}

	@Test
	public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
		profile.add(answerThereIsRelocation);
		profile.add(answerDoesNotReimburseTuition);

		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

		boolean matches = profile.matches(criteria);

		assertTrue(matches);
	}
}
