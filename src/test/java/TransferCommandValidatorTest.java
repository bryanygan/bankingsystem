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
}
