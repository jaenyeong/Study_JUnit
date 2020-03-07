package chapter_12;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ProfileTest {
	// [step3] Profile 객체 인스턴스화를 공통 초기화로 추출
	private Profile profile;

	// [step4] BooleanQuestion 객체 인스턴스화를 공통 초기화로 추출
	private BooleanQuestion questionIsThereRelocation;

	// [step5] Answer 객체 인스턴스화를 공통 초기화로 추출
	private Answer answerThereIsRelocation;

	// [step6] Profile 인스턴스가 매칭되는 Answer 객체가 없을 때 matches() 메소드가 false를 반환
	private Answer answerThereIsNotRelocation;

	// [step7] 다수의 응답을 포함하는 테스트
	private BooleanQuestion questionReimbursesTuition;
	private Answer answerDoesNotReimburseTuition;

	// [step8]
	private Answer answerReimbursesTuition;

	// [step10] 깨끗한 테스트를 위해 Criteria 로컬 변수를 초기화하는 필드 추가
	private Criteria criteria;

	// [step3] Profile 객체 인스턴스화를 공통 초기화로 추출
	@Before
	public void createProfile() {
		profile = new Profile();
	}

	// [step4] BooleanQuestion 객체 인스턴스화를 공통 초기화로 추출
//	@Before
//	public void createQuestion() {
//		questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
//	}

	// [step5] Answer 객체 인스턴스화를 공통 초기화로 추출
	@Before
	public void createQuestionAndAnswer() {
		questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
		answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);

		// [step6] Profile 인스턴스가 매칭되는 Answer 객체가 없을 때 matches() 메소드가 false를 반환
		answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

		// [step7] 다수의 응답을 포함하는 테스트
		questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
		answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);

		// [step8]
		answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
	}

	// [step10] 깨끗한 테스트를 위해 Criteria 로컬 변수를 초기화
	@Before
	public void createCriteria() {
		criteria = new Criteria();
	}

	// [step1]
	// [step14] 리팩토링을 하면서 테스트가 망가짐
	// doesNotMatchWhenNoMatchingAnswer() 테스트에서 같은 것을 보여주고 있기 때문에 이 테스트는 제거
//	@Test
//	public void matchesNothingWhenProfileEmpty() {
//		// [step3] Profile 객체 인스턴스화를 공통 초기화로 추출
////		Profile profile = new Profile();
//
//		// [step4] BooleanQuestion 객체 인스턴스화를 공통 초기화로 추출
////		Question question = new BooleanQuestion(1, "Relocation package?");
////		Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);
//
//		// [step5] Answer 객체 인스턴스화를 공통 초기화로 추출
////		Criterion criterion = new Criterion(new Answer(questionIsThereRelocation, Bool.TRUE), Weight.DontCare);
//		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);
//
//		// [step11] result 임시 변수 제거
////		boolean result = profile.matches(criterion);
////		assertFalse(result);
//		assertFalse(profile.matches(criterion));
//	}

	// [step2] 테스트 메소드 추가
	@Test
	public void matchesWhenProfileContainsMatchingAnswer() {
		// [step3] Profile 객체 인스턴스화를 공통 초기화로 추출
//		Profile profile = new Profile();

		// [step4] BooleanQuestion 객체 인스턴스화를 공통 초기화로 추출
//		Question question = new BooleanQuestion(1, "Relocation package?");
//		Answer answer = new Answer(question, Bool.TRUE);

		// [step5] Answer 객체 인스턴스화를 공통 초기화로 추출
//		Answer answer = new Answer(questionIsThereRelocation, Bool.TRUE);
//		profile.add(answer);
//		Criterion criterion = new Criterion(answer, Weight.Important);
		profile.add(answerThereIsRelocation);
		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

		// [step11] result 임시 변수 제거
//		boolean result = profile.matches(criterion);
//		assertTrue(result);
		assertTrue(profile.matches(criterion));
	}

	// [step6] Profile 인스턴스가 매칭되는 Answer 객체가 없을 때 matches() 메소드가 false를 반환
	// 테스트 추가
	@Test
	public void doesNotMatchWhenNoMatchingAnswer() {
		profile.add(answerThereIsNotRelocation);
		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

		// [step11] result 임시 변수 제거
//		boolean result = profile.matches(criterion);
//		assertFalse(result);
		assertFalse(profile.matches(criterion));
	}

	// [step7] 다수의 응답을 포함하는 테스트
	@Test
	public void matchesWhenContainsMultipleAnswers() {
		profile.add(answerThereIsRelocation);
		profile.add(answerDoesNotReimburseTuition);
		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

		// [step11] result 임시 변수 제거
//		boolean result = profile.matches(criterion);
//		assertTrue(result);
		assertTrue(profile.matches(criterion));
	}

	// [step8] Criteria 객체를 넘기는 테스트 작성
	@Test
	public void doesNotMatchWhenNoneOfMultipleCriteriaMatch() {
		profile.add(answerDoesNotReimburseTuition);
		Criteria criteria = new Criteria();

		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

		// [step11] result 임시 변수 제거
//		boolean result = profile.matches(criteria);
//		assertFalse(result);
		assertFalse(profile.matches(criteria));
	}

	// [step9] Criteria 객체에 다수의 Criterion 객체를 추가하고 Profile에 있는 Answer 객체를 매칭하는 테스트 작성
	@Test
	public void matchesWhenAnyOfMultipleCriteriaMatch() {
		profile.add(answerThereIsRelocation);
		// [step10] Criteria 로컬 변수를 테스트 객체 내에 필드로 이동
//		Criteria criteria = new Criteria();

		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

		// [step11] result 임시 변수 제거
//		boolean result = profile.matches(criteria);
//		assertTrue(result);
		assertTrue(profile.matches(criteria));
	}

	// [step12] 특별한 사례 테스트 추가
	@Test
	public void doesNotMatchWhenAnyMustMeetCriteriaNotMet() {
		profile.add(answerThereIsRelocation);
		profile.add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

		assertFalse(profile.matches(criteria));
	}

	// [step13] 테스트 추가
	@Test
	public void matchesWhenCriterionIsDontCare() {
		profile.add(answerDoesNotReimburseTuition);
		Criterion criterion = new Criterion(answerReimbursesTuition, Weight.DontCare);

		assertTrue(profile.matches(criterion));
	}

	// [step15] 테스트 추가
	@Test
	public void scoreIsZeroWhenThereAreNoMatches() {
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		ProfileMatch match = profile.match(criteria);

		assertThat(match.getScore(), equalTo(0));
	}
}
