import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BankTest {

	@Test
	public void no_accounts_when_bank_is_created() {
		Bank bank = new Bank();
		assertNull(bank.getAccountByID("12345678"));
	}

	@Test
	public void bank_adds_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		assertEquals("12345678", bank.getAccountByID("12345678").getAccountID());
	}

	@Test
	public void bank_adds_two_accounts() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		Savings savings = new Savings("23456781", 1.5);
		bank.addAccount(savings);
		assertEquals(2, bank.getNumberOfAccounts());
	}

	@Test
	public void correct_account_retrieved() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Savings savings = new Savings("23456781", 1.5);
		bank.addAccount(checking);
		bank.addAccount(savings);
		Account retrievedAccount = bank.getAccountByID("12345678");
		assertEquals(checking, retrievedAccount);
		assertEquals("12345678", retrievedAccount.getAccountID());
	}

	@Test
	public void bank_deposits_to_correct_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		assertEquals(500, checking.getBalance());
	}

	@Test
	public void bank_withdraws_from_correct_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.withdrawByID("12345678", 200);
		assertEquals(300, checking.getBalance());
	}

	@Test
	public void two_deposits_through_bank() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.depositByID("12345678", 200);
		assertEquals(700, checking.getBalance());
	}

	@Test
	public void two_withdraws_through_bank() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.withdrawByID("12345678", 200);
		bank.withdrawByID("12345678", 200);
		assertEquals(100, checking.getBalance());
	}
}