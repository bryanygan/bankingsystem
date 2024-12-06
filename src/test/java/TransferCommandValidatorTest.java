import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {

	@Test
	public void test_valid_transfer_between_checking_accounts() {
		Bank bank = new Bank();

		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Checking checking2 = new Checking("87654321", 0.02);
		checking2.deposit(300);
		Bank.addAccount(checking2);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertTrue(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_to_same_account() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("12345678", "12345678", 300, "transfer 12345678 12345678 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_due_to_cd() {
		Bank bank = new Bank();
		CertificateOfDeposit cd = new CertificateOfDeposit("12345678", 1.2, 1000);
		Bank.addAccount(cd);

		Checking checking = new Checking("87654321", 0.03);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_from_cd() {
		Bank bank = new Bank();
		CertificateOfDeposit cd = new CertificateOfDeposit("11112222", 1.2, 2000);
		Bank.addAccount(cd);

		Checking checking = new Checking("33334444", 0.05);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("11112222", "33334444", 500, "transfer 11112222 33334444 500");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_to_cd() {
		Bank bank = new Bank();
		CertificateOfDeposit cd = new CertificateOfDeposit("55556666", 1.5, 3000);
		Bank.addAccount(cd);

		Checking checking = new Checking("77778888", 0.02);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("77778888", "55556666", 100, "transfer 77778888 55556666 100");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_due_to_withdrawal_limit() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Checking checking2 = new Checking("87654321", 0.02);
		checking2.deposit(300);
		Bank.addAccount(checking2);

		TransferCommand command = new TransferCommand("12345678", "87654321", 500, "transfer 12345678 87654321 500");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_due_to_deposit_limit() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 3000, "transfer 12345678 87654321 3000");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_valid_transfer_between_savings_accounts() {
		Bank bank = new Bank();

		Savings savings1 = new Savings("12345678", 0.03);
		savings1.deposit(1000);
		Bank.addAccount(savings1);

		Savings savings2 = new Savings("87654321", 0.05);
		savings2.deposit(500);
		Bank.addAccount(savings2);

		TransferCommand command = new TransferCommand("12345678", "87654321", 800, "transfer 12345678 87654321 800");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertTrue(validator.validate(command, bank));
	}

	@Test
	public void test_partial_transfer_due_to_insufficient_balance() {
		Bank bank = new Bank();

		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(200);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertTrue(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_to_nonexistent_account() {
		Bank bank = new Bank();

		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("12345678", "99999999", 300, "transfer 12345678 99999999 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_from_nonexistent_account() {
		Bank bank = new Bank();

		Savings savings = new Savings("87654321", 0.05);
		savings.deposit(500);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("99999999", "87654321", 300, "transfer 99999999 87654321 300");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_with_zero_amount() {
		Bank bank = new Bank();

		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Checking checking2 = new Checking("87654321", 0.02);
		checking2.deposit(300);
		Bank.addAccount(checking2);

		TransferCommand command = new TransferCommand("12345678", "87654321", 0, "transfer 12345678 87654321 0");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

	@Test
	public void test_invalid_transfer_with_negative_amount() {
		Bank bank = new Bank();

		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Checking checking2 = new Checking("87654321", 0.02);
		checking2.deposit(300);
		Bank.addAccount(checking2);

		TransferCommand command = new TransferCommand("12345678", "87654321", -100, "transfer 12345678 87654321 -100");
		TransferCommandValidator validator = new TransferCommandValidator();

		assertFalse(validator.validate(command, bank));
	}

}
