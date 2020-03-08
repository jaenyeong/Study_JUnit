package chapter_13.thread_test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnswerCollection {
	private Map<String, Answer> answers = new HashMap<>();

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

	public Answer answerMatching(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	public List<Answer> find(Predicate<Answer> pred) {
		return answers.values()
				.stream()
				.filter(pred)
				.collect(Collectors.toList());
	}
}
