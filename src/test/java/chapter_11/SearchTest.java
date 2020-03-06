package chapter_11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import static chapter_11.util.ContainsMatches.containsMatches;
import static org.junit.Assert.*;

public class SearchTest {
	// [step6] 매직 리터럴을 상수로 대체
	private static final String A_TITLE = "1";

	// [step9] 군더더기 제거를 위해 @Before, @After 메소드 생성을 위하여 변수 선언
	private InputStream stream;

	// [step9] log off와 같은 군더더기 제거를 위한 @Before 메소드 선언
	@Before
	public void turnOffLogging() {
		Search.LOGGER.setLevel(Level.OFF);
	}

	// [step9] Stream 닫기 같은 군더더기 제거를 위한 @Before 메소드 선언
	@After
	public void closeResources() throws IOException {
		stream.close();
	}

	// 리팩토링 후 테스트
	@Test
	public void returnsMatchesShowingContextWhenSearchStringInContentAfterRefactor() {
		stream = streamOn("rest of text here"
				+ "1234567890search term1234567890"
				+ "more rest of text");
		Search search = new Search(stream, "search term", A_TITLE);
		search.setSurroundingCharacterCount(10);

		search.execute();

		assertThat(search.getMatches(), containsMatches(
				new Match[]{
						new Match(A_TITLE,
								"search term",
								"1234567890search term1234567890")
				})
		);
	}

	// 리팩토링 후 테스트
	@Test
	public void noMatchesReturnedWhenSearchStringNotInContentAfterRefactor() {
		stream = streamOn("any text");
		Search search = new Search(stream, "text that doesn't match", A_TITLE);

		search.execute();

		assertTrue(search.getMatches().isEmpty());
	}

	// [step8] 다수의 단언을 별개의 테스트로 분할
	// returnsMatchesShowingContextWhenSearchStringInContent()
	// noMatchesReturnedWhenSearchStringNotInContent()
//	@Test
//	public void testSearch() throws IOException {
	@Test
	public void returnsMatchesShowingContextWhenSearchStringInContent() {

		// [step1] 테스트에서 명시적인 try/catch는 테스트에서 의미 없기 때문에 제거
		// 예외를 기대하지 않은 경우라면 예외 발생시 예외가 발생하게 둘 것 메소드에 throws IOException 추가
//		try {

		// [step7] InputStream 객체를 생성하는 도우미 메소드 작성, 호출로 변경
//		String pageContent = "There are certain queer times and occasions "
//				+ "in this strange mixed affair we call life when a man "
//				+ "takes this whole universe for a vast practical joke, "
//				+ "though the wit thereof he but dimly discerns, and more "
//				+ "than suspects that the joke is at nobody's expense but "
//				+ "his own.";
//
//		byte[] bytes = pageContent.getBytes();
//		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

		// [step7] InputStream 객체를 생성하는 도우미 메소드 호출
		// [step9] 군더더기제거
//		InputStream stream =

		// [step11] 테스트 데이터를 명시적으로 변경
//		stream =
//				streamOn("There are certain queer times and occasions "
//						+ "in this strange mixed affair we call life when a man "
//						+ "takes this whole universe for a vast practical joke, "
//						+ "though the wit thereof he but dimly discerns, and more "
//						+ "than suspects that the joke is at nobody's expense but "
//						+ "his own.");
		stream = streamOn("rest of text here"
				+ "1234567890search term1234567890"
				+ "more rest of text");

		// 검색
//		Search search = new Search(stream, "practical joke", "1");

		// [step6] 매직 리터럴을 상수로 대체
//		Search search = new Search(stream, "practical joke", A_TITLE);

		// [step11] 테스트 데이터를 명시적으로 변경
		Search search = new Search(stream, "search term", A_TITLE);

		// [step9] 군더더기제거
//		Search.LOGGER.setLevel(Level.OFF);

		search.setSurroundingCharacterCount(10);

		// [step10] 빈 줄 삽입을 삽입하여 AAA 구분
		search.execute();

		// [step9] 군더더기제거
//		assertFalse(search.errored());

		// [step3] search.getMatches() 호출에서 반환된 매칭 목록에 대한 단언 5줄 제거
//		List<Match> matches = search.getMatches();

		// [step2] 테스트에서 not-null 검사는 의미 없기 때문에 제거
		// 변수를 역으로 참조하기 전에 null 검사를 하는 것은 프로덕션 코드에선 맞겠지만 여기선 이점 없음
//		assertThat(matches, is(notNullValue()));

		// [step3] search.getMatches() 호출에서 반환된 매칭 목록에 대한 단언 5줄 제거
//		assertTrue(matches.size() >= 1);
//		Match match = matches.get(0);
//		assertThat(match.searchString, equalTo("practical joke"));
//		assertThat(match.surroundingContext, equalTo("or a vast practical joke, though t"));

		// [step4] step3에서 제거한 단언문 5줄을 포괄하는 사용자 정의 단언문 작성
		// [step11] 테스트 데이터를 명시적으로 변경
//		assertThat(search.getMatches(), containsMatches(new Match[]{
//				new Match(
//						// [step6] 매직 리터럴을 상수로 대체
////						"1",
//						A_TITLE,
//						"practical joke",
//						"or a vast practical joke, though t")
//		}));

		// [step11] 테스트 데이터를 명시적으로 변경
		assertThat(search.getMatches(), containsMatches(
				new Match[]{
						new Match(A_TITLE,
								"search term",
								"1234567890search term1234567890")
				})
		);

		// [step9] 군더더기제거
//		stream.close();

		// [step8] 다수의 단언을 별개의 테스트로 분할
		// noMatchesReturnedWhenSearchStringNotInContent() 메소드로 추출

//		// 부정적
//		URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
//		InputStream inputStream = connection.getInputStream();
//		// [step6] 매직 리터럴을 상수로 대체
////		search = new Search(inputStream, "smelt", "http://bit.ly/15sYPA7");
//		search = new Search(inputStream, "smelt", A_TITLE);
//		search.execute();
//
//		// [step5] 테스트의 두 번째 덩어리에 대체 추상화를 도입
//		// 누락된 추상화 '비어 있음' 개념
////		assertThat(search.getMatches().size(), equalTo(0));
//		assertTrue(search.getMatches().isEmpty());
//
//		stream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("exception thrown in test" + e.getMessage());
//		}
	}

	// [step7] InputStream 객체를 생성하는 도우미 메소드 작성
	private InputStream streamOn(String pageContent) {
		return new ByteArrayInputStream(pageContent.getBytes());
	}

	// [step13] 새로운 테스트 추가
	@Test
	public void returnsErroredWhenUnableToReadStream() {
		stream = createStreamThrowingErrorWhenRead();
		Search search = new Search(stream, "", "");

		search.execute();

		assertTrue(search.errored());
	}

	// [step13] 새로운 테스트 추가
	private InputStream createStreamThrowingErrorWhenRead() {
		return new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException();
			}
		};
	}

	// [step13] 새로운 테스트 추가
	@Test
	public void erroredReturnedFalseWhenReadSucceeds() {
		stream = streamOn("");
		Search search = new Search(stream, "", "");

		search.execute();

		assertFalse(search.errored());
	}

	// [step8] 다수의 단언을 별개의 테스트로 분할
//	@Test
//	public void testSearch() throws IOException {

	@Test
	public void noMatchesReturnedWhenSearchStringNotInContent() {
		// [step12] URL 입력 스트림을 사용한 테스트라 느림
		// 단위 테스트로 변경
		stream = streamOn("any text");
		Search search = new Search(stream, "text that doesn't match", A_TITLE);

		// [step12] URL 입력 스트림을 사용한 테스트라 느림
		// 단위 테스트로 변경
//		URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
//		// [step9] 군더더기제거
////		InputStream inputStream = connection.getInputStream();
//		stream = connection.getInputStream();
//
//		// [step9] 군더더기제거
////		Search search = new Search(inputStream, "smelt", A_TITLE);
//		Search search = new Search(stream, "smelt", A_TITLE);

		// [step10] 빈 줄 삽입을 삽입하여 AAA 구분
		search.execute();

		// [step10] 빈 줄 삽입을 삽입하여 AAA 구분
		assertTrue(search.getMatches().isEmpty());

		// [step9] 군더더기제거
//		inputStream.close();
	}
}
