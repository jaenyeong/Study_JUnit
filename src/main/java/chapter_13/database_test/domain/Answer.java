package chapter_13.database_test.domain;

public class Answer {
	private Question question;
	private int i;

	public Answer(Question question, int i) {
		this.question = question;
		this.i = i;
	}

	public Answer(Question question, String matchingValue) {
		this.question = question;
		this.i = question.indexOf(matchingValue);
	}

	public String getQuestionText() {
		return question.getText();
	}

	@Override
	public String toString() {
		return String.format("%s %s", question.getText(), question.getAnswerChoice(i));
	}

	public boolean match(int expected) {
		return question.match(expected, i);
	}

	public boolean match(Answer otherAnswer) {
		if (otherAnswer == null) {
			return false;
		}

		return question.match(i, otherAnswer.i);
	}

	public Question getQuestion() {
		return question;
	}
}
