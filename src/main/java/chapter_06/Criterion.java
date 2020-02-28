package chapter_06;

public class Criterion implements Scoreable {
	private Weight weight;
	private Answer answer;
	private int score;

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
