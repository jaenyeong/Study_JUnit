package chapter_13.database_test.domain;

public class Criterion implements Scoreable {
	private Answer answer;
	private Weight weight;
	private int score;

	public Criterion(Answer answer, Weight weight) {
		this.answer = answer;
		this.weight = weight;
	}

	public boolean matches(Answer answer) {
		return getWeight() == Weight.DontCare || answer.match(getAnswer());
	}

	public Answer getAnswer() {
		return answer;
	}

	public Weight getWeight() {
		return weight;
	}

	@Override
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
