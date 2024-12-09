import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {

	private Bank bank;
	private TransferCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new TransferCommandProcessor(new TransactionLogger());
	}

	@Test
	public void test_process_valid_transfer() {
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		processor.process(command, bank);

		assertEquals(200.0, Bank.getAccountByID("12345678").getBalance(), 0.01); // Remaining balance
		assertEquals(300.0, Bank.getAccountByID("87654321").getBalance(), 0.01); // New balance
	}

	@Test
	public void test_process_partial_transfer_due_to_insufficient_balance() {
		Checking checking = new Checking("12345678", 0.03);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		Bank.addAccount(savings);

		checking.deposit(200);

		TransferCommand command = new TransferCommand("12345678", "87654321", 300, "transfer 12345678 87654321 300");
		processor.process(command, bank);

		assertEquals(0.0, Bank.getAccountByID("12345678").getBalance(), 0.01); // All withdrawn
		assertEquals(200.0, Bank.getAccountByID("87654321").getBalance(), 0.01); // New balance
	}

	@Test
	public void test_invalid_transfer_to_nonexistent_account() {
		Checking fromAccount = new Checking("12345678", 0.03);
		fromAccount.deposit(500);
		Bank.addAccount(fromAccount);

		TransferCommand command = new TransferCommand("12345678", "99999999", 200, "transfer 12345678 99999999 200");

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}

	@Test
	public void test_invalid_transfer_from_nonexistent_account() {
		Checking toAccount = new Checking("87654321", 0.02);
		toAccount.deposit(300);
		Bank.addAccount(toAccount);

		TransferCommand command = new TransferCommand("99999999", "87654321", 200, "transfer 99999999 87654321 200");

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}

	@Test
	public void test_transfer_between_savings_accounts() {
		Savings fromAccount = new Savings("12345678", 0.05);
		fromAccount.deposit(1500);
		Bank.addAccount(fromAccount);

		Savings toAccount = new Savings("87654321", 0.05);
		toAccount.deposit(500);
		Bank.addAccount(toAccount);

		TransferCommand command = new TransferCommand("12345678", "87654321", 1000, "transfer 12345678 87654321 1000");
		processor.process(command, bank);

		assertEquals(500.0, fromAccount.getBalance(), 0.01);
		assertEquals(1500.0, toAccount.getBalance(), 0.01);
	}

	@Test
	public void test_valid_transfer_from_checking_to_savings() {
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		savings.deposit(300);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 200, "transfer 12345678 87654321 200");
		processor.process(command, bank);

		assertEquals(300.0, checking.getBalance(), 0.01); // Remaining balance
		assertEquals(500.0, savings.getBalance(), 0.01); // Updated balance
	}

	@Test
	public void test_valid_transfer_from_savings_to_checking() {
		Savings savings = new Savings("12345678", 0.05);
		savings.deposit(1000);
		Bank.addAccount(savings);

		Checking checking = new Checking("87654321", 0.03);
		checking.deposit(300);
		Bank.addAccount(checking);

		TransferCommand command = new TransferCommand("12345678", "87654321", 700, "transfer 12345678 87654321 700");
		processor.process(command, bank);

		assertEquals(300.0, savings.getBalance(), 0.01); // Remaining balance
		assertEquals(1000.0, checking.getBalance(), 0.01); // Updated balance
	}

	@Test
	public void test_partial_transfer_due_to_insufficient_funds_in_checking() {
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(300);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		savings.deposit(500);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 500, "transfer 12345678 87654321 500");
		processor.process(command, bank);

		assertEquals(0.0, checking.getBalance(), 0.01); // All funds withdrawn
		assertEquals(800.0, savings.getBalance(), 0.01); // Updated balance
	}

	@Test
	public void test_transfer_with_zero_amount() {
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		savings.deposit(300);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", 0, "transfer 12345678 87654321 0");

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}

	@Test
	public void test_transfer_with_negative_amount() {
		Checking checking = new Checking("12345678", 0.03);
		checking.deposit(500);
		Bank.addAccount(checking);

		Savings savings = new Savings("87654321", 0.05);
		savings.deposit(300);
		Bank.addAccount(savings);

		TransferCommand command = new TransferCommand("12345678", "87654321", -200, "transfer 12345678 87654321 -200");

		assertThrows(IllegalArgumentException.class, () -> processor.process(command, bank));
	}
}