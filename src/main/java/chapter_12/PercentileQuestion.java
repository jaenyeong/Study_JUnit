package chapter_12;

public class PercentileQuestion extends Question {

	public PercentileQuestion(int id, String text, String[] answerChoices) {
		super(id, text, answerChoices);
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected <= actual;
	}
}
