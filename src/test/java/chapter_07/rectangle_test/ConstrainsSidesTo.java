package chapter_07.rectangle_test;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * 매처는 왼쪽에서 오른쪽으로 읽었을 때 잘 읽히는 단언을 표현
 * TypeSafeMatcher 클래스를 상속하여 사용자 정의 매처 구현
 * 관례상 문구와 클래스명을 동일하게 맞춤
 * 해당 사용자 정의 매처 클래스는 matchesSafely 메소드를 오버라이딩 해야함
 * 또한 정적 팩토리 메소드를 제공해야 함 (테스트에서 단언을 작성할 때 호출함)
 * constrainsSidesTo 팩토리 메소드는 제약 조건을 매처의 생성자로 넘겨주는데 matchesSafely() 메소드에서 사용됨
 */
public class ConstrainsSidesTo extends TypeSafeMatcher<Rectangle> {
	private int length;

	public ConstrainsSidesTo(int length) {
		this.length = length;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("both sides must be <= " + length);
	}

	@Override
	protected boolean matchesSafely(Rectangle rect) {
		return Math.abs(rect.origin().x - rect.opposite().x) <= length
				&& Math.abs(rect.origin().y - rect.opposite().y) <= length;
	}

	@Factory
	public static <T> Matcher<Rectangle> constrainsSidesTo(int length) {
		return new ConstrainsSidesTo(length);
	}
}
