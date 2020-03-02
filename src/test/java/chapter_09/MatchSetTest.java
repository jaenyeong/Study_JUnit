package chapter_09;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatchSetTest {
	private Criteria criteria;
	private Question questionReimbursesTuition;

	private Question questionIsThereRelocation;
	private Answer answerThereIsRelocation;
	private Answer answerThereIsNoRelocation;

	private Answer answerReimbursesTuition;
	private Answer answerDoesNotReimburseTuition;

	private Question questionOnsiteDaycare;
	private Answer answerHasOnsiteDaycare;
	private Answer answerNoOnsiteDaycare;


	private Map<String, Answer> answers;

	@Before
	public void createAnswers() {
		answers = new HashMap<>();
	}

	@Before
	public void createCriteria() {
		criteria = new Criteria();
	}

	@Before
	public void createQuestionAndAnswers() {
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

	private void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

	private MatchSet createMatchSet() {
		return new MatchSet(answers, criteria);
	}

	@Test
	public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

		assertFalse(createMatchSet().matches());
	}

	@Test
	public void matchAnswersTrueForAnyDontCareCriteria() {
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

		assertTrue(createMatchSet().matches());
	}
}
