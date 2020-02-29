package chapter_07.profilepool_test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfilePool {
	private List<Profile> profiles = new ArrayList<>();

	public void add(Profile profile) {
		profiles.add(profile);
	}

	public void score(Criteria criteria) {
		for (Profile profile : profiles) {
			profile.matches(criteria);
		}
	}

	public List<Profile> ranked() {
		// 실패
//		Collections.sort(profiles,
//				(p1, p2) -> ((Integer) p1.score()).compareTo(p2.score()));
		// 성공
		Collections.sort(profiles,
				(p1, p2) -> ((Integer) p2.score()).compareTo(p1.score()));
		return profiles;
	}
}
