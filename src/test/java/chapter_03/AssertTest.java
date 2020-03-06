package chapter_03;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;
import static org.hamcrest.Matchers.startsWith; // startsWith 메소드 에러로 추가

public class AssertTest {

	class InsufficientFundsException extends RuntimeException {
		public InsufficientFundsException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 1L;
	}

	public class Account {
		int balance;
		String name;

		Account(String name) {
			this.name = name;
		}

		void deposit(int dollars) {
			balance += dollars;
		}

		void withdraw(int dollars) {
			if (balance < dollars) {
				throw new InsufficientFundsException("balance only " + balance);
			}
			balance -= dollars;
		}

		public String getName() {
			return name;
		}

		public int getBalance() {
			return balance;
		}

		public boolean hasPositiveBalance() {
			return balance > 0;
		}
	}

	private Account account;

	@Before
	public void createAccount() {
		account = new Account("an account name");
	}

	@Test
	public void hasPositiveBalance() {
		account.deposit(50);
		assertTrue(account.hasPositiveBalance());
	}

	@Test
	public void depositIncreaseBalance() {
		int initialBalance = account.getBalance();
		account.deposit(100);
		assertTrue(account.getBalance() > initialBalance);

		assertThat(account.getBalance(), equalTo(100));
	}

	@Test
	public void depositIncreasesBalance_hamcrestAssertTrue() {
		account.deposit(50);
		assertThat(account.getBalance() > 0, is(true));
	}

	@Test
	@Ignore
	@ExpectToFail
	public void matchesFailure() {
		assertThat(account.getName(), startsWith("xyz"));
	}

	@Test
	@Ignore
	@ExpectToFail
	public void comparesArraysFailing() {
		assertThat(new String[] {"a", "b", "c"}, equalTo(new String[] {"a", "b"}));
	}

	@Test
	@Ignore
	@ExpectToFail
	public void comparesCollectionsFailing() {
		assertThat(Arrays.asList(new String[] {"a"}), equalTo(Arrays.asList(new String[] {"a", "ab"})));
	}

	@Test
	public void comparesArraysPassing() {
		assertThat(new String[] {"a", "b"}, equalTo(new String[] {"a", "b"}));
	}

	@Test
	public void comparesCollectionsPassing() {
		assertThat(Arrays.asList(new String[] {"a"}), equalTo(Arrays.asList(new String[] {"a"})));
	}

	@Test
	public void variousMatcherTests() {
		Account account = new Account("my big fat acct");
		assertThat(account.getName(), is(equalTo("my big fat acct")));

		assertThat(account.getName(), not(equalTo("plunderings")));

		// null 검사를 자주 하는 것은 설계에 문제 또는 지나친 걱정
		assertThat(account.getName(), is(not(nullValue())));
		assertThat(account.getName(), is(notNullValue()));
	}

	@Test
	@Ignore
	@ExpectToFail
	public void floatingPointTest() {
		assertThat(2.32 * 3, equalTo(6.96));
	}

	@Test
	public void floatingPointAgainTest() {
		assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
	}

	@Test
	public void isCloseToTest() {
		assertThat(2.32 * 3, closeTo(6.96, 0.0005));
	}

	@Test
	public void testWithWorthlessAssertionComment() {
		account.deposit(50);
		assertThat("account balance is 100", account.getBalance(), equalTo(50));
	}

	@Test(expected = InsufficientFundsException.class)
	public void throwsWhenWithdrawingTooMuch() {
		account.withdraw(100);
	}

	@Test
	public void throwsWhenWithdrawingTooMuchTry() {
		try {
			account.withdraw(100);
			fail(); // 정상적일 경우(위에서 예외를 뱉는 경우) 실행되지 않음
		} catch (InsufficientFundsException expected) {
			assertThat(expected.getMessage(), equalTo("balance only 0"));
		}
	}

	// ExpectedException 규칙 사용시 public 으로 선언 후, Rule 애노테이션 태깅
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void exceptionRule() {
		thrown.expect(InsufficientFundsException.class);
		thrown.expectMessage("balance only 0");

		account.withdraw(100);
	}

	@Test
	public void readsFromTestFile() throws IOException {
		String fileName = "test.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write("test data");
		writer.close();
	}
}
