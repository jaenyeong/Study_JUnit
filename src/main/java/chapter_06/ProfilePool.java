package chapter_06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfilePool {
	private List<Profile> profiles = new ArrayList<Profile>();

	public void add(Profile profile) {
		profiles.add(profile);
	}

	public void score(Criteria criteria) {
		for (Profile profile : profiles) {
			profile.matches(criteria);
		}
	}

	public List<Profile> ranked() {
		Collections.sort(profiles,
				(p1, p2) -> ((Integer) p1.score()).compareTo(p2.score()));
		return profiles;
	}
}
