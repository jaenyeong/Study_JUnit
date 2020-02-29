package chapter_07.profilepool_test;

public class BooleanQuestion extends Question {

	public BooleanQuestion(int id, String text) {
		// super constructor param : (int id, String text, String[] answerChoices)
		super(id, text, new String[]{"No", "Yes"});
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected == actual;
	}
}
