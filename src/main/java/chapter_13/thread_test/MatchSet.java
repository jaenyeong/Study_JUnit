package chapter_13.thread_test;

import java.util.Map;

public class MatchSet implements Comparable<MatchSet> {
	private Map<String, Answer> answers;
	private Criteria criteria;
	private int score = Integer.MIN_VALUE;
	private String profileId;

	public MatchSet(String profileId, Map<String, Answer> answers, Criteria criteria) {
		this.profileId = profileId;
		this.answers = answers;
		this.criteria = criteria;
	}

	public String getProfileId() {
		return profileId;
	}

	public int getScore() {
		if (score == Integer.MIN_VALUE) {
			calculateScore();
		}
		return score;
	}

	private void calculateScore() {
		score = 0;
		for (Criterion criterion : criteria) {
			if (criterion.matches(answerMatching(criterion))) {
				score += criterion.getWeight().getValue();
			}
		}
	}

	private Answer answerMatching(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	public boolean matches() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (doesNotMeetAnyMustMatchCriterion()) {
			return false;
		}
		return anyMatches();
	}

	private boolean doesNotMeetAnyMustMatchCriterion() {
		for (Criterion criterion : criteria) {
			boolean match = criterion.matches(answerMatching(criterion));

			if (!match && criterion.getWeight() == Weight.MustMatch) {
				return true;
			}
		}
		return false;
	}

	private boolean anyMatches() {
		boolean anyMatches = false;
		for (Criterion criterion : criteria) {
			anyMatches |= criterion.matches(answerMatching(criterion));
		}
		return anyMatches;
	}

	@Override
	public int compareTo(MatchSet that) {
		// deprecated [new Integer(int primitive type)]
//		return new Integer(getScore()).compareTo(new Integer(that.getScore()));

		return Integer.valueOf(getScore()).compareTo(that.getScore());
		// or
//		return ((Integer) getScore()).compareTo(that.getScore());
	}
}
