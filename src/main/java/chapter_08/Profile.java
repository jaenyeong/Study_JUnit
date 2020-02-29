package chapter_08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
	private Map<String, Answer> answers = new HashMap<>();

	private int score;
	private String name;

	public Profile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

	/**
	 * matches() method Before Refactor
	 */
	public boolean matchesBeforeRefactor(Criteria criteria) {
		score = 0;

		boolean kill = false;
		boolean anyMatches = false;
		for (Criterion criterion : criteria) {
			Answer answer = answers.get(
					criterion.getAnswer().getQuestionText());
			boolean match =
					criterion.getWeight() == Weight.DontCare ||
							answer.match(criterion.getAnswer());

			if (!match && criterion.getWeight() == Weight.MustMatch) {
				kill = true;
			}
			if (match) {
				score += criterion.getWeight().getValue();
			}
			anyMatches |= match;
		}
		if (kill)
			return false;
		return anyMatches;
	}

	/**
	 * matches() method After Refactor
	 */
	public boolean matchesAfterRefactor(Criteria criteria) {
		calculateScore(criteria);
		if (doesNotMeetAnyMustMatchCriterion(criteria)) {
			return false;
		}
		return anyMatches(criteria);
	}

	public boolean matches(Criteria criteria) {
		// [step9] 유사하게 모든 매칭의 전체 가중치를 계산하는 코드 추출
//		score = 0;
		calculateScore(criteria);

		// [step11] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
//		boolean kill = false;

		// [step6] anyMatches 변수 값 반환을 메소드의 결과를 반환하는 것으로 변경
//		boolean anyMatches = false;

		// [step12] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
//		for (Criterion criterion : criteria) {

		// origin
		// 디메테르(디미터 또는 데메테르)의 법칙 위반 (the Law of Demeter - 다른 객체로 전파되는 연쇄적인 메소드 호출을 피하기)
//			Answer answer = answers.get(
//					criterion.getAnswer().getQuestionText());

		// [step3] answer 변수 값 바인딩을 메소드로 추출
		// [step4] answer 객체(임시변수)는 코드의 명확성을 높이지 않고 한번만 사용하기 때문에 제거
		// IDEA 변수 리팩토링 단축키 : answer 객체(임시변수) 드래그 후 option + command + N 키
//			Answer answer = answerMatching(criterion);

		// origin
//			boolean match =
//					criterion.getWeight() == Weight.DontCare ||
//							answer.match(criterion.getAnswer());

		// [step1] match 변수 값 바인딩을 메소드로 추출
//			boolean match = matches(criterion, answer);

		// [step2] matches 메소드를 Profile 클래스로부터 Criterion 클래스로 이동
//			boolean match = criterion.matches(answer);

		// [step5] answer 객체(임시변수)를 제거한 후 바로 matches 메소드 안에 인라인 바인딩
		// [step13] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
//			boolean match = criterion.matches(answerMatching(criterion));

		// [step14] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
//			if (!match && criterion.getWeight() == Weight.MustMatch) {
//				kill = true;
//			}

		// [step10] 유사하게 모든 매칭의 전체 가중치를 계산하는 코드 추출
//			if (match) {
//				score += criterion.getWeight().getValue();
//			}

		// [step7] anyMatches 변수 값 반환을 메소드의 결과를 반환하는 것으로 변경
//			anyMatches |= match;

//		} // for

		// [step15] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
//		if (kill) {
//			return false;
//		}

		// [step12,13,14,15] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
		if (doesNotMeetAnyMustMatchCriterion(criteria)) {
			return false;
		}

		// [step8] anyMatches 변수 값 반환을 메소드의 결과를 반환하는 것으로 변경 > 메소드 추출
//		return anyMatches;
		return anyMatches(criteria);
	}

	/**
	 * [step12,13,14,15] 매치되지 않는 어떤 필수 조건(must-meet)이 있는지 여부를 결정하는 로직 추출
	 */
	private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
		for (Criterion criterion : criteria) {
			boolean match = criterion.matches(answerMatching(criterion));
			if (!match && criterion.getWeight() == Weight.MustMatch) {
				return true;
			}
		}
		return false;
	}

	/**
	 * [step9,10] 유사하게 모든 매칭의 전체 가중치를 계산하는 코드 추출
	 */
	private void calculateScore(Criteria criteria) {
		score = 0;
		for (Criterion criterion : criteria) {
			if (criterion.matches(answerMatching(criterion))) {
				score += criterion.getWeight().getValue();
			}
		}
	}

	/**
	 * [step6,7,8] anyMatches 변수 값의 반환 작업을 메소드로 추출
	 */
	private boolean anyMatches(Criteria criteria) {
		boolean anyMatches = false;
		for (Criterion criterion : criteria) {
			// 리팩토링 후 테스트 코드를 실행 시키기 위해서 인위적으로 실수(에러)를 집어 넣은 것으로 보임
//			anyMatches = criterion.matches(answerMatching(criterion));
			anyMatches |= criterion.matches(answerMatching(criterion));
		}
		return anyMatches;
	}

	/**
	 * [step3] answer 변수 값 바인딩을 메소드로 추출
	 * - 디메테르(디미터 또는 데메테르)의 법칙 위반 (the Law of Demeter - 다른 객체로 전파되는 연쇄적인 메소드 호출을 피하기)
	 */
	private Answer answerMatching(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	/**
	 * [step1] match 변수 값 바인딩을 메소드로 추출
	 * - 조건문은 잘 읽히지 않음, 따라서 메소드로 추출하여 복잡성을 고립시킴
	 * [step2] matches 메소드를 Profile 클래스로부터 Criterion 클래스로 이동
	 */
//	private boolean matches(Criterion criterion, Answer answer) {
//		return criterion.getWeight() == Weight.DontCare ||
//				answer.match(criterion.getAnswer());
//	}
	public int score() {
		return score;
	}

	public List<Answer> classicFind(Predicate<Answer> pred) {
		List<Answer> results = new ArrayList<Answer>();
		for (Answer answer : answers.values())
			if (pred.test(answer))
				results.add(answer);
		return results;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<Answer> find(Predicate<Answer> pred) {
		return answers.values()
				.stream()
				.filter(pred)
				.collect(Collectors.toList());
	}
}
