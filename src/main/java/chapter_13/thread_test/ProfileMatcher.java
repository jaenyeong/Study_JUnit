package chapter_13.thread_test;

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
	// [step3-1] 테스트를 위하여 재작업(재설계) executor를 필드로 추출
	private ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

	public void add(Profile profile) {
		profiles.put(profile.getId(), profile);
	}

	// [step3-3] 테스트를 위하여 재작업(재설계) findMatchingProfiles() 메소드 파라미터 변경
	// process() 메소드 동작을 스텁 처리하기 위해 findMatchingProfiles() 메소드를 오버로딩
//	public void findMatchingProfiles(Criteria criteria, MatchListener listener) {
	public void findMatchingProfiles(
			Criteria criteria,
			MatchListener listener,
			List<MatchSet> matchSets,
			BiConsumer<MatchListener, MatchSet> processFunction) {

		// [step3-1] 테스트를 위하여 재작업(재설계) executor를 필드로 추출
//		ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

		// [step1] MatchSet 인스턴스를 모으는 로직을 추출
		// 어플리케이션 로직과 스레드 로직을 분리하기 위함
//		List<MatchSet> matchSets = profiles.values()
//				.stream()
//				.map(profile -> profile.getMatchSet(criteria))
//				.collect(Collectors.toList());

		// [step1] MatchSet 인스턴스를 모으는 메소드 호출
//		for (MatchSet set : matchSets) {
		// [step3-1] 테스트를 위하여 재작업(재설계)
//		for (MatchSet set : collectMatchSets(criteria)) {
		for (MatchSet set : matchSets) {

			// [step3-4] 테스트를 위하여 재작업(재설계) runnable 바인딩 변경
//			// 컬렉션에 있는 각 요소들에 대해 스레드를 생성하는 제네릭 메소드를 생성할 수 있으나 여기선 하지 않음
//			Runnable runnable = () -> {
//
//				// [step2] 유사하게 매칭된 profile 정보를 listener로 넘기는 어플리케이션 로직 추출
////				if (set.matches()) {
////					listener.foundMatch(profiles.get(set.getProfileId()), set);
////				}
//
//				process(listener, set);
//			};

			// [step3-5] 테스트를 위하여 재작업(재설계) runnable 바인딩 변경
			Runnable runnable = () -> processFunction.accept(listener, set);
			executor.execute(runnable);
		}
		executor.shutdown();
	}

	// [step3-6] 테스트를 위하여 재작업(재설계) findMatchingProfiles () 메소드 오버로딩, 기존 메소드 호출
	// process() 메소드 동작을 스텁 처리하기 위해 findMatchingProfiles() 메소드를 오버로딩, 기존 메소드의 원형을 갖는 메소드 다시 작성
	public void findMatchingProfiles(Criteria criteria, MatchListener listener) {
		findMatchingProfiles(criteria, listener, collectMatchSets(criteria), this::process);
	}

	// [step1] MatchSet 인스턴스를 모으는 메소드 작성
	List<MatchSet> collectMatchSets(Criteria criteria) {
		List<MatchSet> matchSets = profiles.values()
				.stream()
				.map(profile -> profile.getMatchSet(criteria))
				.collect(Collectors.toList());
		return matchSets;
	}

	// [step2] 유사하게 매칭된 profile 정보를 listener로 넘기는 메소드 작성
	// step2에서 테스트를 했기 때문에 안전하게 동작한다고 가정, findMatchingProfiles() 테스트 시 로직 무시
	void process(MatchListener listener, MatchSet set) {
		if (set.matches()) {
			listener.foundMatch(profiles.get(set.getProfileId()), set);
		}
	}

	// [step3-2] 테스트를 위하여 재작업(재설계) executor getter 작성
	// 테스트 코드에서 ExecutorService 인스턴스에 접근할 필요가 있기 때문에 getter 작성
	ExecutorService getExecutor() {
		return executor;
	}
}
