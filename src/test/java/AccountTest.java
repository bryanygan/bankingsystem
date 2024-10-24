import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AccountTest {

	@Test
	public void checking_account_balance_starts_at_zero() {
		Checking checkingAccount = new Checking("12345678", 3.5);
		assertEquals(0.0, checkingAccount.getBalance());
	}

	@Test
	public void saving_account_balance_starts_at_zero() {
		Savings savingsAccount = new Savings("87654321", 2.0);
		assertEquals(0.0, savingsAccount.getBalance());
	}
}
