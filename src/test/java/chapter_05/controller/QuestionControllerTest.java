package chapter_05.controller;

import chapter_05.domain.Question;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuestionControllerTest {
	private QuestionController controller;

	@Before
	public void create() {
		controller = new QuestionController();
		controller.deleteAll();
	}

	@Test
	public void questionAnswersDateAdded() {
		Instant now = new Date().toInstant();
		controller.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
		int id = controller.addBooleanQuestion("text");

		Question question = controller.find(id);
		// EntityManager에서 해당 ID로 값을 찾지 못해 Quesion 클래스가 NULL로 떨어짐
		assertThat(question.getCreateTimestamp(), equalTo(now));
	}

}
