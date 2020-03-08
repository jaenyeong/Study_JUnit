package chapter_13.database_test.domain;

import chapter_13.database_test.controller.QuestionController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class StatCompilerTest {
	@Mock
	private QuestionController controller;

	@InjectMocks
	private StatCompiler stats;

	@Before
	public void initialize() {
		stats = new StatCompiler();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void questionTextDoesStuff() {
		when(controller.find(1)).thenReturn(new BooleanQuestion("text1"));
		when(controller.find(2)).thenReturn(new BooleanQuestion("text2"));

		List<BooleanAnswer> answers = new ArrayList<>();
		answers.add(new BooleanAnswer(1, true));
		answers.add(new BooleanAnswer(2, true));

		Map<Integer, String> questionText = stats.questionText(answers);
		Map<Integer, String> expected = new HashMap<>();

		expected.put(1, "text1");
		expected.put(2, "text2");

		assertThat(questionText, equalTo(expected));
	}
}
