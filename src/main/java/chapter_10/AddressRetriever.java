package chapter_10;

import chapter_10.util.Http;
import chapter_10.util.HttpImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class AddressRetriever {
	private static final String REQUEST_URL = "http://open.mapquestapi.com/nominatim/v1/reverse?format=json&";

	// [step1] 스텁을 사용하는 방식을 알려주기 위해 의존성 주입 기법 활용
//	private Http http;
	// [step4] 모키토 사용 http 필드를 찾아서 목 인스턴스로 주입해줌
	// 필드 수준 주입으로는 더 이상 클라이언트에 http 객체를 생성자로 넘길 필요 없음
	private Http http = new HttpImpl();

	// [step3] 기본 생성자
//	public AddressRetriever() {
//	}

	// [step1] 스텁을 사용하는 방식을 알려주기 위해 의존성 주입 기법 활용
	// [step4] 필드 수준 주입으로는 더 이상 클라이언트에 http 객체를 생성자로 넘길 필요 없음
//	public AddressRetriever(Http http) {
//		this.http = http;
//	}

	public Address retrieve(double latitude, double longitude) throws IOException, ParseException {
//		String params = String.format("lat=%.6flon=%.6f", latitude, longitude);
		String params = String.format("lat=%.6f&lon=%.6f", latitude, longitude);

		// HttpImpl는 apache의 HttpComponents 클라이언트와 상호 작용하여 REST 호출 실행
		// [step2] 스텁을 사용하는 방식을 알려주기 위해 수정
//		String response = new HttpImpl().get(REQUEST_URL + params);

		// [step2] 스텁을 사용하는 방식을 알려주기 위해 수정
		String response = http.get(REQUEST_URL + params);

		JSONObject obj = (JSONObject) new JSONParser().parse(response);

		JSONObject address = (JSONObject) obj.get("address");
		String country = (String) address.get("country_code");

		if (!country.equals("us")) {
			throw new UnsupportedOperationException("cannot support non-US addresses at this time");
		}

		String houseNumber = (String) address.get("house_number");
		String road = (String) address.get("road");
		String city = (String) address.get("city");
		String state = (String) address.get("state");
		String zip = (String) address.get("postcode");

		return new Address(houseNumber, road, city, state, zip);
	}
}
