package chapter_05.domain;

import chapter_05.controller.QuestionController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatCompiler {
	private QuestionController controller = new QuestionController();

	private Map<Boolean, AtomicInteger> createNewHistogram() {
		Map<Boolean, AtomicInteger> histogram;
		histogram = new HashMap<>();
		histogram.put(Boolean.FALSE, new AtomicInteger(0));
		histogram.put(Boolean.TRUE, new AtomicInteger(0));
		return histogram;
	}

	private Map<Boolean, AtomicInteger> getHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses, int id) {
		Map<Boolean, AtomicInteger> histogram = null;

		if (responses.containsKey(id))
			histogram = responses.get(id);
		else {
			histogram = createNewHistogram();
			responses.put(id, histogram);
		}
		return histogram;
	}

	private void incrementHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses, BooleanAnswer answer) {
		Map<Boolean, AtomicInteger> histogram = getHistogram(responses, answer.getQuestionId());
		histogram.get(Boolean.valueOf(answer.getValue())).getAndIncrement();
	}

	private Map<String, Map<Boolean, AtomicInteger>> convertHistogramIdsToText(
			Map<Integer, Map<Boolean, AtomicInteger>> responses, Map<Integer, String> questions) {

		Map<String, Map<Boolean, AtomicInteger>> textResponses = new HashMap<>();
//		responses.keySet().stream().forEach(id -> textResponses.put(controller.find(id).getText(), responses.get(id)));
		responses.keySet().stream().forEach(id -> textResponses.put(questions.get(id), responses.get(id)));
		return textResponses;
	}

	public Map<String, Map<Boolean, AtomicInteger>> responsesByQuestion(List<BooleanAnswer> answers,
	                                                                    Map<Integer, String> questions) {
		Map<Integer, Map<Boolean, AtomicInteger>> responses = new HashMap<>();
		answers.stream().forEach(answer -> incrementHistogram(responses, answer));
		return convertHistogramIdsToText(responses, questions);
	}

	public Map<Integer, String> questionText(List<BooleanAnswer> answers) {
		Map<Integer, String> questions = new HashMap<>();
		answers.stream().forEach(answer -> {
			if (!questions.containsKey(answer.getQuestionId()))
				questions.put(answer.getQuestionId(), controller.find(answer.getQuestionId()).getText());
		});
		return questions;
	}

}
