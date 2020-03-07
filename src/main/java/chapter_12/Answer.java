package chapter_12;

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
		// [step1] Answer null 검사
		if (otherAnswer == null) {
			return false;
		}

		return question.match(i, otherAnswer.i);
	}

	public Question getQuestion() {
		return question;
	}
}
