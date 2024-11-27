import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void test_process_create_checking_command() {
		String command = "create checking 12345678 1.0";
		commandProcessor.processCommand(command);
		Checking account = (Checking) bank.getAccountByID("12345678");

		assertEquals("12345678", account.getAccountID());
		assertEquals(1.0, account.getAPR(), 0.001);
	}

	@Test
	public void test_process_invalid_account_type() {
		String command = "create investment 87654321 2.0";
		assertThrows(UnsupportedOperationException.class, () -> {
			commandProcessor.processCommand(command);
		});
	}

	@Test
	public void test_process_create_command_with_invalid_format() {
		String command = "create checking";
		assertThrows(IllegalArgumentException.class, () -> {
			commandProcessor.processCommand(command);
		});
	}

	@Test
	public void test_process_deposit_command() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		commandProcessor.processCommand("deposit 12345678 100");
		Account account = bank.getAccountByID("12345678");

		assertEquals(100, account.getBalance());
	}
}