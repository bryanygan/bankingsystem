import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	@Test
	public void checking_account_starts_at_zero() {
		Checking checking = new Checking("12345678", 3.5);
		assertEquals(0, checking.getBalance());
	}

	@Test
	public void checking_correct_apr_value() {
		Checking checking = new Checking("12345678", 3.5);
		assertEquals(3.5, checking.getAPR());
	}

	@Test
	public void checking_deposit_increases_balance() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(50.50);
		assertEquals(50.50, checking.getBalance());
	}

	@Test
	public void checking_withdraw_decreases_balance() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(20.50);
		assertEquals(479.50, checking.getBalance());
	}

	@Test
	public void checking_withdraw_more_than_balance_goes_to_zero() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(600);
		assertEquals(0, checking.getBalance());
	}

	@Test
	public void checking_depositing_twice_works() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.deposit(500);
		assertEquals(1000, checking.getBalance());
	}

	@Test
	public void checking_withdrawing_twice_works() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(200);
		checking.withdraw(200);
		assertEquals(100, checking.getBalance());
	}
}