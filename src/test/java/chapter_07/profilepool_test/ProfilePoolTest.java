package chapter_07.profilepool_test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfilePoolTest {
	private Profile smeltInc;
	private Profile langrSoft;
	private BooleanQuestion doTheyReimburseTuition;
	private ProfilePool profilePool;

	@Before
	public void create() {
		smeltInc = new Profile("Smelt");
		langrSoft = new Profile("LangSoft");
		doTheyReimburseTuition = new BooleanQuestion(1, "Reimburses tuition?");
		profilePool = new ProfilePool();
	}

	@Test
	public void answersResultInScoredOrder() {
		// smelt사에는 부정적인 답변 추가
		smeltInc.add(new Answer(doTheyReimburseTuition, Bool.FALSE));
		profilePool.add(smeltInc);
		// langrSoft사에는 부정적인 답변 추가
		langrSoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
		profilePool.add(langrSoft);

		profilePool.score(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));
		List<Profile> ranked = profilePool.ranked();

		// ranked 메소드를
        // Collections.sort(profiles, (p1, p2) -> ((Integer) p1.score()).compareTo(p2.score())); 에서
		// Collections.sort(profiles, (p1, p2) -> ((Integer) p2.score()).compareTo(p1.score())); 으로 변경
		assertThat(ranked.toArray(), equalTo(new Profile[]{langrSoft, smeltInc}));
	}

	private Criteria soleNeed(Question question, int value, Weight weight) {
		Criteria criteria = new Criteria();
		criteria.add(new Criterion(new Answer(question, value), weight));
		return criteria;
	}
}
