package chapter_09;

/**
 * Profile 클래스는 책임 두 개를 정의
 * - 프로파일에 관한 정보 추적하기
 * - 조건 집합이 프로파일에 매칭되는지 혹은 그 정도를 판단하기
 * <p>
 * SRP를 위반, 따라서 MatchSet 클래스 생성, 리팩토링 하여 매칭 책임 코드 이동
 */
public class Profile {
	// [step14] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
//	private Map<String, Answer> answers = new HashMap<>();
	private AnswerCollection answers = new AnswerCollection();

	// [step10] score 필드 제거
//	private int score;
	private String name;

	public Profile(String name) {
		this.name = name;
	}

	/**
	 * matches() method Before Refactor
	 */
//	public boolean beforeMatch(chapter_08.Criteria criteria) {
//		calculateScore(criteria);
//		if (doesNotMeetAnyMustMatchCriterion(criteria)) {
//			return false;
//		}
//		return anyMatches(criteria);
//	}

	// [step13] matches() 메소드 제거 > getMatchSet() 메소드로 matchSet 인스턴스 반환
//	public boolean matches(Criteria criteria) {
//		// [step1] matchSet 클래스로 해당 메소드(매칭 책임에 관한 코드)를 이동
////		calculateScore(criteria);
//
//		// [step4] step1처리 리팩토링 하면서 새로 생성한(변경한) 로직 부분을 수정
////		score = new MatchSet(answers, criteria).getScore();
//		MatchSet matchSet = new MatchSet(answers, criteria);
//
//		// [step3] 해당 로직을 MatchSet 클래스로 이동
////		if (doesNotMeetAnyMustMatchCriterion(criteria)) {
////			return false;
////		}
//		// [step5]
//		// [step12] score 필드 제거
////		score = matchSet.getScore();
//
//		// [step4] 해당 로직을 MatchSet 클래스로 이동
////		return anyMatches(criteria);
//		// [step6]
//		return matchSet.matches();
//	}

	/**
	 * matches() method After Refactor
	 */
//	public boolean matchesAfterRefactor(Criteria criteria) {
//		MatchSet matchSet = new MatchSet(answers, criteria);
////		score = matchSet.getScore();
//		return matchSet.matches();
//	}

	// [step7] MatchSet 클래스로 이동
//	private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
//		for (Criterion criterion : criteria) {
//			boolean match = criterion.matches(answerMatching(criterion));
//			if (!match && criterion.getWeight() == Weight.MustMatch) {
//				return true;
//			}
//		}
//		return false;
//	}

	// [step2] matchSet 클래스로 해당 메소드(매칭 책임에 관한 코드)를 이동
//	private void calculateScore(Criteria criteria) {
//		score = 0;
//		for (Criterion criterion : criteria) {
//			if (criterion.matches(answerMatching(criterion))) {
//				score += criterion.getWeight().getValue();
//			}
//		}
//	}

	// [step8] MatchSet 클래스로 이동
//	private boolean anyMatches(Criteria criteria) {
//		boolean anyMatches = false;
//		for (Criterion criterion : criteria) {
//			anyMatches |= criterion.matches(answerMatching(criterion));
//		}
//		return anyMatches;
//	}

	// [step3] 해당 메소드를 Profile 클래스와 MatchSet 클래스 모두에서 사용할 때 이것을 한 곳에 위치할 방법을 고민해야 함
	// MatchSet 클래스로 메소드 추출 리팩토링
//	private Answer answerMatching(Criterion criterion) {
//		return answers.get(criterion.getAnswer().getQuestionText());
//	}

	// [step11] score 필드 제거
//	public int score() {
//		return score;
//	}

//	public List<Answer> classicFind(Predicate<Answer> pred) {
//		List<Answer> results = new ArrayList<>();
//		for (Answer answer : answers.values()) {
//			if (pred.test(answer)) {
//				results.add(answer);
//			}
//		}
//		return results;
//	}
	public String getName() {
		return name;
	}

	public void add(Answer answer) {
		// [step15] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
//		answers.put(answer.getQuestionText(), answer);
		answers.add(answer);
	}

	// [step9] 명령-질의 분리 원칙
	// 클라이언트가 원할 때 MatchSet 객체를 다루도록 결정, MatchSet을 반환하는 메소드 추가
	public MatchSet getMatchSet(Criteria criteria) {
		// [step16] answers 컬렉션 필드를 클래스로 추출하여 리팩토링
//		return new MatchSet(answers, criteria);
		return new MatchSet(answers, criteria);
	}

	@Override
	public String toString() {
		return name;
	}

//	public List<Answer> find(Predicate<Answer> pred) {
//		return answers.values()
//				.stream()
//				.filter(pred)
//				.collect(Collectors.toList());
//	}
}
