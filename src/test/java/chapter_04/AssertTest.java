package chapter_04;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AssertTest {

	class InsufficientFundsException extends RuntimeException {
		public InsufficientFundsException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 1L;
	}

	class Account {
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
	@Ignore("don't forget me!")
	public void somethingWeCannotHandleRightNow() {
		// TODO
	}

}
