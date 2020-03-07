package chapter_12;

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
