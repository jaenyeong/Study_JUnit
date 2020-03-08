package chapter_13.thread_test;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProfileMatcherTest {
	// [step1] collectMatchSet() 메소드 추출 후 테스트 최초 작성
	private BooleanQuestion question;
	private Criteria criteria;
	private ProfileMatcher matcher;
	private Profile matchingProfile;
	private Profile nonMatchingProfile;
	// [step2-1] Profile 정보를 listener로 넘기는 process 메소드 테스트를 위하여 필드에 listener 작성
	private MatchListener listener;

	@Before
	public void create() {
		question = new BooleanQuestion(1, "");
		criteria = new Criteria();
		criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
		matchingProfile = createMatchingProfile("matching");
		nonMatchingProfile = createNonMatchingProfile("nonMatching");
	}

	private Profile createMatchingProfile(String name) {
		Profile profile = new Profile(name);
		profile.add(matchingAnswer());
		return profile;
	}

	private Profile createNonMatchingProfile(String name) {
		Profile profile = new Profile(name);
		profile.add(nonMatchingAnswer());
		return profile;
	}

	private Answer matchingAnswer() {
		return new Answer(question, Bool.TRUE);
	}

	private Answer nonMatchingAnswer() {
		return new Answer(question, Bool.FALSE);
	}

	@Before
	public void createMatcher() {
		matcher = new ProfileMatcher();
	}

	// [step2-2] process 메소드 테스트를 위한 listener 목 세팅
	@Before
	public void createMatchListener() {
		// 모키토의 mock() 메소드를 사용, MatchListener의 목 인스턴스를 생성
		listener = mock(MatchListener.class);
	}

	// [step1-2] MatchSet 인스턴스를 모으는 collectMatchSet() 메소드 테스트
	@Test
	public void collectsMatchSets() {
		matcher.add(matchingProfile);
		matcher.add(nonMatchingProfile);

		List<MatchSet> matchSets = matcher.collectMatchSets(criteria);

		assertThat(matchSets
						.stream()
						.map(matchSet -> matchSet.getProfileId())
						.collect(Collectors.toSet())
				, equalTo(
						new HashSet<>(Arrays.asList(
								matchingProfile.getId(),
								nonMatchingProfile.getId()))));
	}

	// [step2-3] Profile 정보를 listener로 넘기는 process 메소드 테스트
	@Test
	public void processNotifiesListenerOnMatch() {
		// (1) 매칭되는 Profile(주어진 조건에 매칭될 것으로 기대되는)을 matcher 변수에 추가
		matcher.add(matchingProfile);
		// (2) 주어진 조건 집합에 매칭되는 Profile에 대한 MatchSet 객체를 요청
		MatchSet matchSet = matchingProfile.getMatchSet(criteria);

		// (3) 목 리스너와 MatchSet 객체를 넘겨 matcher 변수에 매칭 처리를 지시
		matcher.process(listener, matchSet);

		// (4) 모키토를 활용하여 목으로 만든 리스너 객체에 foundMatch() 메소드가 호출 되었는지 확인
		// (4) 이때 매칭 Profile과 MatchSet 객체를 인수로 넘김
		// (4) 기대 사항이 맞지 않으면 모키토에 의해 테스트 실패
		verify(listener).foundMatch(matchingProfile, matchSet);
	}

	// [step2-4] Profile 정보를 listener로 넘기는 process 메소드 테스트
	@Test
	public void processDoesNotifiesListenerWhenNoMatch() {
		matcher.add(nonMatchingProfile);
		MatchSet matchSet = nonMatchingProfile.getMatchSet(criteria);

		matcher.process(listener, matchSet);

		verify(listener, never()).foundMatch(nonMatchingProfile, matchSet);
	}

	// [step3-1] 스레드 로직을 위해 테스트 작성
	@Test
	public void gathersMatchingProfiles() {
		// (1) 리스너가 수신하는 MatchSet 객체들의 Profile ID 목록을 저장할 문자열 Set 객체를 생성
		Set<String> processedSets = Collections.synchronizedSet(new HashSet<>());
		BiConsumer<MatchListener, MatchSet> processFunction =
				// (2) processFunction() 함수 정의, 이 함수는 process() 메소드의 프로덕션 버전을 대신함
				(listener, set) -> {
					// (3) 리스너에 대한 각 콜백에서 MatchSet 객체의 Profile ID를 processedSets 변수에 추가
					processedSets.add(set.getProfileId());
				};
		// (4) createMatchSets() 도우미 메소드를 사용, 테스트용 MatchSet 객체들을 생성
		List<MatchSet> matchSets = createMatchSets(100);

		// (5) 인수로 함수를 갖는 findMatchingProfiles() 메소드를 호출, 인수로 processFunction() 구현을 넘김
		matcher.findMatchingProfiles(criteria, listener, matchSets, processFunction);

		// (6) Matcher에서 ExecutorService 객체를 얻어와 모든 스레드의 실행이 완료될 때까지 반복문 실행
		while (!matcher.getExecutor().isTerminated())
			;

		// (7) processedSets 컬렉션(리스너에 포착된 Profile ID 목록)이 테스트에서 생성된 모든 MatchSet 객체의 ID와 매칭되는지 검증
		assertThat(processedSets,
				equalTo(matchSets
						.stream()
						.map(MatchSet::getProfileId)
						.collect(Collectors.toSet())));
	}

	// [step3-2] gathersMatchingProfiles() 테스트에서 사용하는 메소드 작성
	private List<MatchSet> createMatchSets(int count) {
		List<MatchSet> sets = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			sets.add(new MatchSet(String.valueOf(i), null, null));
		}
		return sets;
	}
}
