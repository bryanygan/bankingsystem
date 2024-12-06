import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {

	@Test
	public void test_process_valid_transfer() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		TransferCommandProcessor processor = new TransferCommandProcessor();
		processor.process(command, bank);

		assertEquals(200.0, Bank.getAccountByID("12345678").getBalance(), 0.01); // Remaining balance
		assertEquals(300.0, Bank.getAccountByID("87654321").getBalance(), 0.01); // New balance
	}

	@Test
	public void test_process_partial_transfer_due_to_insufficient_balance() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 0.03);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		checking.deposit(200);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		TransferCommandProcessor processor = new TransferCommandProcessor();
		processor.process(command, bank);

		assertEquals(0.0, Bank.getAccountByID("12345678").getBalance(), 0.01); // All withdrawn
		assertEquals(200.0, Bank.getAccountByID("87654321").getBalance(), 0.01); // New balance
	}

	@Test
	public void test_invalid_transfer_to_nonexistent_account() {
		Bank bank = new Bank();

		Checking fromAccount = new Checking("12345678", 0.03);
		fromAccount.deposit(500);
		Bank.addAccount(fromAccount);

		TransferCommand command = new TransferCommand("12345678", "99999999", 200, "transfer 12345678 99999999 200");
		TransferCommandProcessor processor = new TransferCommandProcessor();

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}

	@Test
	public void test_invalid_transfer_from_nonexistent_account() {
		Bank bank = new Bank();

		Checking toAccount = new Checking("87654321", 0.02);
		toAccount.deposit(300);
		Bank.addAccount(toAccount);

		TransferCommand command = new TransferCommand("99999999", "87654321", 200, "transfer 99999999 87654321 200");
		TransferCommandProcessor processor = new TransferCommandProcessor();

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}

}