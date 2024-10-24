import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BankTest {
	@Test
	public void checking_account_starts_at_zero() {
		Checking checking = new Checking("12345678", 3.5);
		assertEquals(0, checking.getBalance());
	}

	@Test
	public void savings_account_starts_at_zero() {
		Savings savings = new Savings("12345678", 1.5);
		assertEquals(0, savings.getBalance());
	}

	@Test
	public void cd_account_starts_with_specific_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		assertEquals(1000, cd.getBalance());
	}

	@Test
	public void checking_correct_apr_value() {
		Checking checking = new Checking("12345678", 3.5);
		assertEquals(3.5, checking.getAPR());
	}

	@Test
	public void savings_correct_apr_value() {
		Savings savings = new Savings("12345678", 1.5);
		assertEquals(1.5, savings.getAPR());
	}

	@Test
	public void cd_account_correct_apr_value() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		assertEquals(4.0, cd.getAPR());
	}

	@Test
	public void checking_deposit_increases_balance() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(50.50);
		assertEquals(50.50, checking.getBalance());
	}

	@Test
	public void savings_deposit_increases_balance() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		assertEquals(500, savings.getBalance());
	}

	@Test
	public void cd_deposit_increases_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		assertEquals(1500, cd.getBalance());
	}

	@Test
	public void checking_withdraw_decreases_balance() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(20.50);
		assertEquals(479.50, checking.getBalance());
	}

	@Test
	public void savings_withdraw_decreases_balance() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.withdraw(200);
		assertEquals(300, savings.getBalance());
	}

	@Test
	public void cd_withdraw_decreases_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(200);
		assertEquals(1300, cd.getBalance());
	}

	@Test
	public void checking_withdraw_more_than_balance_goes_to_zero() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(600);
		assertEquals(0, checking.getBalance());
	}

	@Test
	public void savings_withdraw_more_than_balance_goes_to_zero() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.withdraw(600);
		assertEquals(0, savings.getBalance());
	}

	@Test
	public void cd_withdraw_more_than_balance_goes_to_zero() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(1600);
		assertEquals(0, cd.getBalance());
	}

	@Test
	public void checking_depositing_twice_works() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.deposit(500);
		assertEquals(1000, checking.getBalance());
	}

	@Test
	public void savings_depositing_twice_works() {
		Savings savings = new Savings("12345678", 1.5);
		savings.deposit(500);
		savings.deposit(500);
		assertEquals(1000, savings.getBalance());
	}

	@Test
	public void cd_depositing_twice_works() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.deposit(500);
		assertEquals(2000, cd.getBalance());
	}

	@Test
	public void checking_withdrawing_twice_works() {
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		checking.withdraw(200);
		checking.withdraw(200);
		assertEquals(100, checking.getBalance());
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
	public void cd_withdrawing_twice_works() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(200);
		cd.withdraw(200);
		assertEquals(1100, cd.getBalance());
	}

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
		assertEquals(checking, bank.getAccountByID("12345678"));
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
