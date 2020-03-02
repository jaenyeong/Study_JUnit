package chapter_09;

/**
 * 점수 관련 코드는 MatchSet 클래스로 이동
 * 기존 Profile 클래스에 matches() 메소드에 있는 나머지 코드는 메소드의 두 번째 목표를 나타냄
 * - if (doesNotMeetAnyMustMatchCriterion(criteria)) {
 * return false;
 * }
 * - return anyMatches(criteria);
 * 위 책임을 MatchSet 클래스로 위임
 * criteria 필드를 생성자에서 주입 받은 후 다른 메소드들의 criteria 파라미터 제거
 */
public class MatchSet {
	// [step5] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
//	private Map<String, Answer> answers;
	private AnswerCollection answers;

	// [step3] 생성자에서 score를 계산하지 않기 때문에 score 변수를 제거
//	private int score = 0;
	// [step1] criteria를 MatchSet 내부 필드로 선언한 뒤 이를 파라미터로 받는 메소드 수정
	private Criteria criteria;

	// [step6] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
//	public MatchSet(Map<String, Answer> answers, Criteria criteria) {
//		this.answers = answers;
//		this.criteria = criteria;
//		// [step2] 생성자에서 score를 계산하는 것은 사용하지 않을 경우 리소스 낭비
////		calculateScore();
//	}

	// [step7] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
	// 파라미터 변경
	public MatchSet(AnswerCollection answers, Criteria criteria) {
		this.answers = answers;
		this.criteria = criteria;
	}

	// [step4] getScore() 메소드로 이동(인라인)
//	private void calculateScore() {
//		score = 0;
//		for (Criterion criterion : criteria) {
//			if (criterion.matches(answerMatching(criterion))) {
//				score += criterion.getWeight().getValue();
//			}
//		}
//	}

	// [step8] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
	// answerMatching() 메소드를 AnswerCollection 클래스로 추출하여 리팩토링
//	private Answer answerMatching(Criterion criterion) {
//		return answers.get(criterion.getAnswer().getQuestionText());
//	}

	public boolean matches() {
		if (doesNotMeetAnyMustMatchCriterion()) {
			return false;
		}
		return anyMatches();
	}

	// [step9] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
	// answerMatching() 메소드를 AnswerCollection 클래스로 추출하여 리팩토링
	private boolean doesNotMeetAnyMustMatchCriterion() {
		for (Criterion criterion : criteria) {
			boolean match = criterion.matches(answers.answerMatching(criterion));
			if (!match && criterion.getWeight() == Weight.MustMatch) {
				return true;
			}
		}
		return false;
	}

	// [step10] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
	// answerMatching() 메소드를 AnswerCollection 클래스로 추출하여 리팩토링
	private boolean anyMatches() {
		boolean anyMatches = false;
		for (Criterion criterion : criteria) {
			anyMatches |= criterion.matches(answers.answerMatching(criterion));
		}
		return anyMatches;
	}

	// getScore() 메소드를 매번 호출할 때 마다 점수를 계산하는 것이 성능 저하로 이어진다면 지연 초기화를 사용
	// [step11] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
	// answerMatching() 메소드를 AnswerCollection 클래스로 추출하여 리팩토링
	public int getScore() {
		int score = 0;
		for (Criterion criterion : criteria) {
			if (criterion.matches(answers.answerMatching(criterion))) {
				score += criterion.getWeight().getValue();
			}
		}
		return score;
	}
}
