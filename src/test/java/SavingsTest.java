import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	@Test
	public void savings_account_starts_at_zero() {
		Savings savings = new Savings("12345678", 1.5);
		assertEquals(0, savings.getBalance());
	}

	@Test
	public void savings_correct_apr_value() {
		Savings savings = new Savings("12345678", 1.5);
		assertEquals(1.5, savings.getAPR());
	}

	@Test
	public void savings_deposit_increases_balance() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		assertEquals(500, savings.getBalance());
	}

	@Test
	public void savings_withdraw_decreases_balance() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.withdraw(200);
		assertEquals(300, savings.getBalance());
	}

	@Test
	public void savings_withdraw_more_than_balance_goes_to_zero() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.withdraw(600);
		assertEquals(0, savings.getBalance());
	}

	@Test
	public void savings_depositing_twice_works() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.deposit(500);
		assertEquals(1000, savings.getBalance());
	}

	@Test
	public void savings_withdrawing_twice_works() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.withdraw(200);
		savings.withdraw(200);
		assertEquals(100, savings.getBalance());
	}

	@Test
	public void test_savings_account_deposit_limit() {
		Savings savings = new Savings("12345", 0.01);
		assertTrue(savings.deposit(2000));
		assertFalse(savings.deposit(2600));
	}

	@Test
	public void test_negative_and_zero_deposit() {
		Savings savings = new Savings("12345", 0.01);
		assertTrue(savings.deposit(0));
		assertFalse(savings.deposit(-100));
	}
}