package chapter_13.database_test.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
	private Map<String, Answer> answers = new HashMap<>();
	private String id;

	public Profile(String id) {
		this.id = id;
	}

	public boolean matches(Criterion criterion) {
		return criterion.getWeight() == Weight.DontCare ||
				criterion.getAnswer().match(getMatchingProfileAnswer(criterion));
	}

	public boolean matches(Criteria criteria) {
		boolean matches = false;

		for (Criterion criterion : criteria) {
			if (matches(criterion)) {
				matches = true;
			} else if (criterion.getWeight() == Weight.MustMatch) {
				return false;
			}
		}
		return matches;
	}

	public ProfileMatch match(Criteria criteria) {
		return new ProfileMatch();
	}

	public MatchSet getMatchSet(Criteria criteria) {
		return new MatchSet(id, answers, criteria);
	}

	private Answer getMatchingProfileAnswer(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

	public List<Answer> find(Predicate<Answer> pred) {
		return answers.values()
				.stream()
				.filter(pred)
				.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}
}
