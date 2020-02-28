package chapter_06;

import java.util.ArrayList;
import java.util.List;

public class ScoreCollection {
	private List<Scoreable> scores = new ArrayList<>();

	public void add(Scoreable scoreable) {
		// 경계조건 null 확인
		if (scoreable == null) {
			throw new IllegalArgumentException();
		}

		scores.add(scoreable);
	}

	public int arithmeticMean() {
		// 경계조건 0 확인
		if (scores.size() == 0) {
			return 0;
		}

//		int total = scores.stream().mapToInt(Scoreable::getSource).sum();
//		return total / scores.size();

		// add 메소드가 개별 입력값을 int 타입으로 한정하고 개수만큼 나누면 int 최대값 보다 작아지기 때문에 int 타입의 범위를 넘어서지 않음
		long total = scores.stream().mapToLong(Scoreable::getSource).sum();
		return (int) (total / scores.size());
	}
}
