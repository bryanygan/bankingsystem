import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		Checking account = (Checking) Bank.getAccountByID("12345678");

		assertEquals("12345678", account.getAccountID());
		assertEquals(1.0, account.getAPR(), 0.001);
	}

	@Test
	public void test_process_invalid_account_type() {
		Bank bank = new Bank();
		InvalidCommands invalidCommands = new InvalidCommands();
		CreateCommand createCommand = new CreateCommand(bank, invalidCommands);

		String[] command = { "create", "unknown", "12345678", "0.5" };

		createCommand.execute(command);

		assertTrue(invalidCommands.getInvalidCommands().contains("create unknown 12345678 0.5"));
	}

	@Test
	public void test_process_create_command_with_invalid_format() {
		Bank bank = new Bank();
		InvalidCommands invalidCommands = new InvalidCommands();
		CreateCommand createCommand = new CreateCommand(bank, invalidCommands);

		String[] invalidCommand = { "create", "invalid_format" };

		createCommand.execute(invalidCommand);

		assertTrue(invalidCommands.getInvalidCommands().contains("create invalid_format"));
	}

	@Test
	public void test_process_deposit_command() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		commandProcessor.processCommand("deposit 12345678 100");
		Account account = Bank.getAccountByID("12345678");

		assertEquals(100, account.getBalance());
	}
}