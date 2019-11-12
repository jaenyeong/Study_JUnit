package chapter_04;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ScoreCollectionTest {

	/**
	 * AAA
	 * 준비, 실행, 단언
	 *
	 * 준비(Arrange)
	 * 테스트 실행 전 시스템 상태 확인
	 * 객체 생성, API 호출 등
	 * 상황에 따라 생략 가능
	 *
	 * 실행(Act)
	 * 테스트 코드 실행
	 * 주로 단일 메소드 호출
	 *
	 * 단언(Assert)
	 * 실행 코드 동작 확인
	 * 반환값, 새로운 객체 상태 등 검사
	 * 또한 테스트 코드와 객체 간 의사소통 검사
	 *
	 * 사후(After)
	 * 테스트 실행시 할당된 자원이 잘 정리되었는지 확인
	 */

	@Test
	public void answersArithmeticMeanOfTwoNumbers() {
		// 준비
		ScoreCollection collection = new ScoreCollection();
		collection.add(() -> 5);
		collection.add(() -> 7);

		// 실행
		int actualResult = collection.arithmeticMean();

		// 단언
		assertThat(actualResult, equalTo(6));
	}
}
