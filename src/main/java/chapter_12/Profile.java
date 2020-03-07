package chapter_12;

import java.util.HashMap;
import java.util.Map;

public class Profile {
	// [step3] matches() 기능 구현을 위해 추가
//	private Answer answer;
	// [step6] 다수의 Answer 객체 갖기
	// 질문 텍스트를 키로 하고 연관된 Answer 객체를 값으로 Map에 저장
	private Map<String, Answer> answers = new HashMap<>();

	// [step1] 테스트 코드에 작성한 matches() 메소드 구현하여 테스트 통과
	public boolean matches(Criterion criterion) {
		// [step3] 테스트 통과를 위하여 하드 코딩
//		return true;

		// [step4] matches() 메소드의 올바른 기능 구현
//		return answer != null;
		// [step5] matches() 메소드 기능 수정 (매칭 되지 않았을 경우)
//		return answer != null && answer.match(criterion.getAnswer());

		// [step6] 다수의 Answer 객체중에서 해당 Answer 객체를 꺼내 값 반환
		// [step11] 추가된 테스트를 위해 변경
//		Answer answer = getMatchingProfileAnswer(criterion);

		// [step7] null 검사를 Answer 클래스로 이동후 제거
//		return answer != null && answer.match(criterion.getAnswer());
		// [step11] 추가된 테스트를 위해 변경
//		return criterion.getAnswer().match(answer);

		// [step11] 추가된 테스트를 위해 변경
		return criterion.getWeight() == Weight.DontCare ||
				criterion.getAnswer().match(getMatchingProfileAnswer(criterion));
	}

	// [step8] Criteria를 파라미터로 받는 matches() 메소드 작성
	public boolean matches(Criteria criteria) {
		// [step10] 추가된 테스트를 통과 시키기 위해 추가
		boolean matches = false;

		// [step9] Criteria 객체에서 각 Criterion 객체를 순회하는 반복문 포함
		for (Criterion criterion : criteria) {
			// [step10] 추가된 테스트를 통과 시키기 위해 수정
//			if (matches(criterion)) {
//				return true;
//			}
			if (matches(criterion)) {
				matches = true;
			} else if (criterion.getWeight() == Weight.MustMatch) {
				return false;
			}
		}
		// [step10] 추가된 테스트를 통과 시키기 위해 추가
//		return false;
		return matches;
	}

	// [step12] ProfileMatch 객체를 반환하는 match 메소드 작성
	public ProfileMatch match(Criteria criteria) {
		return new ProfileMatch();
	}

	// [step6] 다수의 Answer 객체중에서 해당 Answer 객체 꺼내는 메소드 작성
	private Answer getMatchingProfileAnswer(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	// [step2] 테스트 코드에 작성한 add() 메소드 구현하여 테스트 통과
	public void add(Answer answer) {
		// [step6] 다수의 Answer 객체를 담는 맵에 담기
//		this.answer = answer;
		answers.put(answer.getQuestionText(), answer);
	}
}
