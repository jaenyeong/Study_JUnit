package chapter_13.database_test.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ProfileMatcher {
	private Map<String, Profile> profiles = new HashMap<>();
	private static final int DEFAULT_POOL_SIZE = 4;
	private ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

	public void add(Profile profile) {
		profiles.put(profile.getId(), profile);
	}

	public void findMatchingProfiles(
			Criteria criteria,
			MatchListener listener,
			List<MatchSet> matchSets,
			BiConsumer<MatchListener, MatchSet> processFunction) {

		for (MatchSet set : matchSets) {
			Runnable runnable = () -> processFunction.accept(listener, set);
			executor.execute(runnable);
		}
		executor.shutdown();
	}

	public void findMatchingProfiles(Criteria criteria, MatchListener listener) {
		findMatchingProfiles(criteria, listener, collectMatchSets(criteria), this::process);
	}

	List<MatchSet> collectMatchSets(Criteria criteria) {
		List<MatchSet> matchSets = profiles.values()
				.stream()
				.map(profile -> profile.getMatchSet(criteria))
				.collect(Collectors.toList());
		return matchSets;
	}

	void process(MatchListener listener, MatchSet set) {
		if (set.matches()) {
			listener.foundMatch(profiles.get(set.getProfileId()), set);
		}
	}

	ExecutorService getExecutor() {
		return executor;
	}
}
