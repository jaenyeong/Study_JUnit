package chapter_07.sparse_test;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SparseArrayTest {
	private SparseArray<Object> array;

	@Before
	public void create() {
		array = new SparseArray<>();
	}

	@Test
	public void handlesInsertionInDescendingOrder() {
		array.put(7, "seven");
		array.checkInvariants(); // 불변식 검사 추가

		array.put(6, "six");
		array.checkInvariants(); // 불변식 검사 추가

		assertThat(array.get(6), equalTo("six"));
		assertThat(array.get(7), equalTo("seven"));
	}
}
