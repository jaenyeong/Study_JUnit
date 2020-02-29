package chapter_07.rectangle_test;

import org.junit.After;
import org.junit.Test;

import static chapter_07.rectangle_test.ConstrainsSidesTo.constrainsSidesTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RectangleTest {
	private Rectangle rectangle;

	@After
	public void ensureInvariant() {
		assertThat(rectangle, constrainsSidesTo(100));
	}

	@Test
	public void answersArea() {
		rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
		assertThat(rectangle.area(), equalTo(50));
	}

	@Test
	public void allowsDynamicallyChangingSize() {
		rectangle = new Rectangle(new Point(5, 5));
		rectangle.setOppositeCorner(new Point(130, 130)); // 불변식(ensureInvariant() 메서드의 값 조건 100을 넘김) 위반

		// 위 setOppositeCorner 값 삽입 조건 위반으로 테스트 실패
		assertThat(rectangle.area(), equalTo(15625));

		/**
		 * 여기서 불변식(invariant)은 프로그램이 실행되는 동안 또는 정해진 기간 동안 반드시 만족해야 하는 조건을 의미
		 * 변경을 허용하나 주어진 조건 내에서만 변경을 허용함
		 * 불변(immutable)는 어떠한 변경도 허용하지 않는 것 (가변 객체와 구분하는 용도로 사용됨)
		 * 예 : String 객체
		 */
	}
}
