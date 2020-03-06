package chapter_10;

import chapter_10.util.Http;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

public class AddressRetrieverTest {
	// [step4] mockito의 DI 기능(@Mock)을 이용하여 목 인스턴스 생성
	@Mock
	private Http http;
	// [step5] mockito의 DI 기능(@InjectMocks)을 이용하여 목 인스턴스를 주입할 객체 지정
	@InjectMocks
	private AddressRetriever retriever;

	// [step6] mockito의 DI 기능(@mock)을 이용하여 목 인스턴스 생성
	@Before
	public void createRetriever() {
		retriever = new AddressRetriever();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void answersAppropriateAddressForValidCoordinates() throws IOException, ParseException {
		// [step1] 람다를 통해 바인딩
//		Http http = (String url) ->
//				"{\"address\":{"
//						+ "\"house_number\":\"324\","
//						+ "\"road\":\"North Tejon Street\","
//						+ "\"city\":\"Colorado Springs\","
//						+ "\"state\":\"Colorado\","
//						+ "\"postcode\":\"80903\","
//						+ "\"country_code\":\"us\"}"
//						+ "}";

		// [step1] 익명 클래스를 통해 바인딩
//		Http http = new Http() {
//			@Override
//			public String get(String url) throws IOException {
//				return "{\"address\":{"
//						+ "\"house_number\":\"324\","
//						+ "\"road\":\"North Tejon Street\","
//						// ...
//						+ "\"city\":\"Colorado Springs\","
//						+ "\"state\":\"Colorado\","
//						+ "\"postcode\":\"80903\","
//						+ "\"country_code\":\"us\"}"
//						+ "}";
//			}
//		};

		// [step2] stub의 지능 넣기 (mock에 가까운 형태)
		// mock은 의도적으로 흉내낸 동작을 제공하고 수신한 인지가 모두 정상인지 여부를 검증하는 일을 하는 테스트 구조물
//		Http http = (String url) -> {
//
//			if (!url.contains("lat=38.000000&lon=-104.000000")) {
//				// String params = String.format("lat=%.6flon=%.6f", latitude, longitude);
//				// & 문자열이 없어서 에러
//				fail("url " + url + " does not contain correct params");
//			}
//
//			return "{\"address\":{"
//					+ "\"house_number\":\"324\","
//					+ "\"road\":\"North Tejon Street\","
//					+ "\"city\":\"Colorado Springs\","
//					+ "\"state\":\"Colorado\","
//					+ "\"postcode\":\"80903\","
//					+ "\"country_code\":\"us\"}"
//					+ "}";
//		};

		// [step3] mockito 추가
//		Http http = mock(Http.class);
//		when(http.get(contains("lat=38.000000&lon=-104.000000")))
//				.thenReturn("{\"address\":{"
//						+ "\"house_number\":\"324\","
//						+ "\"road\":\"North Tejon Street\","
//						+ "\"city\":\"Colorado Springs\","
//						+ "\"state\":\"Colorado\","
//						+ "\"postcode\":\"80903\","
//						+ "\"country_code\":\"us\"}"
//						+ "}");

//		AddressRetriever retriever = new AddressRetriever(http);

		// [step5] 위에서 생성한 mock 인스턴스 사용으로 조건만 설정
		when(http.get(contains("lat=38.000000&lon=-104.000000")))
				.thenReturn("{\"address\":{"
						+ "\"house_number\":\"324\","
						+ "\"road\":\"North Tejon Street\","
						+ "\"city\":\"Colorado Springs\","
						+ "\"state\":\"Colorado\","
						+ "\"postcode\":\"80903\","
						+ "\"country_code\":\"us\"}"
						+ "}");

		Address address = retriever.retrieve(38.0, -104.0);

		assertThat(address.houseNumber, equalTo("324"));
		assertThat(address.road, equalTo("North Tejon Street"));
		assertThat(address.city, equalTo("Colorado Springs"));
		assertThat(address.state, equalTo("Colorado"));
		assertThat(address.zip, equalTo("80903"));
	}
}
