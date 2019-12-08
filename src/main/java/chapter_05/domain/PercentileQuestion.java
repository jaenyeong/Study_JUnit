package chapter_05.domain;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue(value = "percentile")
public class PercentileQuestion extends Question {
	@ElementCollection
	@CollectionTable(name = "AnswerChoice", joinColumns = @JoinColumn(name = "question_id"))
	private List<String> answerChoices;

	public PercentileQuestion() {
	}

	public PercentileQuestion(String text, String[] answerChoices) {
		super(text);
		this.answerChoices = Arrays.asList(answerChoices);
	}

	@Override
	public List<String> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<String> answerChoices) {
		this.answerChoices = answerChoices;
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected <= actual;
	}
}
