package chapter_02;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {
	private Profile profile; // add 4
	private Question question; // add 4
	private Criteria criteria; // add 4

	// add 5
	@Before
	public void create() {
		profile = new Profile("Bull Hockey");
		question = new BooleanQuestion(1, "Got bonuses?");
		criteria = new Criteria();
	}

	@Test
//	public void test() {
	public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
//		Profile profile = new Profile("Bull Hockey, Inc. "); // add 6
//		Question question = new BooleanQuestion(1, "Got bonuses"); // add 6

		// add 1
//		Answer profileAnswer = new Answer(question, Bool.FALSE); // add 8
//		profile.add(profileAnswer); // add 8

		// add 9
		profile.add(new Answer(question, Bool.FALSE));

//		Criteria criteria = new Criteria(); // add 6
//		Answer criteriaAnswer = new Answer(question, Bool.TRUE); // add 10
//		Criterion criterion = new Criterion(criteriaAnswer, Weight.MustMatch); // add 10

//		criteria.add(criterion); // add 10

		// add 11
		criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

		// add 2
		boolean matches = profile.matches(criteria);
		assertFalse(matches);
	}

	// add 3
	@Test
	public void matchAnswersTrueForAnyDontCareCriteria() {
//		Profile profile = new Profile("Bull Hockey, Inc. "); // add 7
//		Question question = new BooleanQuestion(1, "Got milk"); // add 7

//		Answer profileAnswer = new Answer(question, Bool.FALSE); // add 12
//		profile.add(profileAnswer); // add 12

		// add 13
		profile.add((new Answer(question, Bool.FALSE)));

//		Criteria criteria = new Criteria(); // add 7
//		Answer criteriaAnswer = new Answer(question, Bool.TRUE); // add 14
//		Criterion criterion = new Criterion(criteriaAnswer, Weight.DontCare); // add 14

//		criteria.add(criterion); // add 14

		// add 15
		criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

		boolean matches = profile.matches(criteria);

		assertTrue(matches);
	}

}
