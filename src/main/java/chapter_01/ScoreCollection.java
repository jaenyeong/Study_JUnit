package chapter_01;

import java.util.ArrayList;
import java.util.List;

public class ScoreCollection {
	private List<Scoreable> scores = new ArrayList<>();

	public void add(Scoreable scoreable) {
		scores.add(scoreable);
	}

	public int arithmeticMean() {
		int total = scores.stream().mapToInt(Scoreable::getSource).sum();
		return total;
	}
}
