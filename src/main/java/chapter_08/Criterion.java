package chapter_08;

public class Criterion implements Scoreable {
	private Answer answer;
	private Weight weight;
	private int score;

	public Criterion(Answer answer, Weight weight) {
		this.answer = answer;
		this.weight = weight;
	}

	/**
	 * [step2] matches 메소드를 Profile 클래스로부터 Criterion 클래스로 이동
	 * Criterion 객체는 이미 Answer 객체를 알고 있으나 역은 성립하지 않음
	 * 즉 Criterion 클래스는 Answer 클래스에 의존하나 Answer 클래스는 Criterion에 의존하지 않음
	 * 따라서 matches 메소드를 Answer 클래스로 이동했다면 양방향 의존 관계가 되기 때문에 좋지 않음
	 * 접근 제한자와 파라미터 변경
	 */
	public boolean matches(Answer answer) {
		return getWeight() == Weight.DontCare || answer.match(getAnswer());
	}

	@Override
	public int getSource() {
		return 0;
	}

	public Answer getAnswer() {
		return answer;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
}
