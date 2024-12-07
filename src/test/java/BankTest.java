import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BankTest {

	@Test
	public void no_accounts_when_bank_is_created() {
		Bank bank = new Bank();
		assertNull(Bank.getAccountByID("12345678"));
	}

	@Test
	public void bank_adds_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		assertEquals("12345678", Bank.getAccountByID("12345678").getAccountID());
	}

	@Test
	public void bank_adds_two_accounts() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		Savings savings = new Savings("23456781", 1.5);
		Bank.addAccount(savings);
		assertEquals(2, bank.getNumberOfAccounts());
	}

	@Test
	public void correct_account_retrieved() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Savings savings = new Savings("23456781", 1.5);
		Bank.addAccount(checking);
		Bank.addAccount(savings);
		Account retrievedAccount = Bank.getAccountByID("12345678");
		assertEquals(checking, retrievedAccount);
		assertEquals("12345678", retrievedAccount.getAccountID());
	}

	@Test
	public void bank_deposits_to_correct_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		assertEquals(500, checking.getBalance());
	}

	@Test
	public void bank_withdraws_from_correct_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.withdrawByID("12345678", 200);
		assertEquals(300, checking.getBalance());
	}

	@Test
	public void two_deposits_through_bank() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.depositByID("12345678", 200);
		assertEquals(700, checking.getBalance());
	}

	@Test
	public void two_withdraws_through_bank() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		Bank.addAccount(checking);
		bank.depositByID("12345678", 500);
		bank.withdrawByID("12345678", 200);
		bank.withdrawByID("12345678", 200);
		assertEquals(100, checking.getBalance());
	}

	@Test
	public void test_remove_account_with_zero_balance() {
		Bank bank = new Bank();
		Account account = new Account("12345678", 1.0);
		Bank.addAccount(account);

		bank.processPassTime(1);

		assertNull(Bank.getAccountByID("12345678"));
	}

}