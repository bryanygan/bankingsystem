import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	private Bank bank;
	private CommandValidation commandValidation;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;
	private MasterControl masterControl;
	private TransactionLogger transactionLogger;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		invalidCommands = new InvalidCommands();
		commandValidation = new CommandValidation(bank, invalidCommands);
		transactionLogger = new TransactionLogger();
		masterControl = new MasterControl(commandValidation, commandProcessor, invalidCommands, transactionLogger);
	}

	@Test
	void typo_in_create_command_is_invalid() {
		List<String> input = new ArrayList<>();
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		List<String> input = new ArrayList<>();
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("depositt 12345678 100", actual.get(0));
	}

	@Test
	void two_typo_commands_both_invalid() {
		List<String> input = new ArrayList<>();
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

//	@Test
//	void invalid_to_create_accounts_with_same_ID() {
//		List<String> input = new ArrayList<>();
//		input.add("create checking 12345678 1.0");
//		input.add("create checking 12345678 1.0");
//
//		List<String> actual = masterControl.start(input);
//
//		assertEquals(1, actual.size());
//		assertEquals("create checking 12345678 1.0", actual.get(0));
//	}

	@Test
	public void test_empty_command_list() {
		List<String> commands = new ArrayList<>();
		List<String> result = masterControl.start(commands);

		assertEquals(0, result.size());
	}

	@Test
	public void test_process_valid_command() {
		List<String> input = new ArrayList<>();
		input.add("create checking 12345678 1.0");

		List<String> result = masterControl.start(input);

		assertEquals(0, invalidCommands.getInvalidCommands().size());
	}

	@Test
	public void test_process_invalid_command() {
		List<String> input = new ArrayList<>();
		input.add("create investment 87654321 2.0");

		List<String> result = masterControl.start(input);

		assertEquals(1, result.size());
		assertEquals("create investment 87654321 2.0", result.get(0));
	}

	@Test
	public void test_process_mixed_valid_and_invalid_commands() {
		List<String> input = new ArrayList<>();
		input.add("create checking 12345678 1.0"); // valid
		input.add("create investment 87654321 2.0"); // invalid
		input.add("deposit 12345678 100"); // valid
		input.add("deposit 99999999 50"); // invalid

		List<String> result = masterControl.start(input);

		assertEquals(2, invalidCommands.getInvalidCommands().size());
		assertEquals("create investment 87654321 2.0", invalidCommands.getInvalidCommands().get(0));
		assertEquals("deposit 99999999 50", invalidCommands.getInvalidCommands().get(1));

	}

	@Test
	public void test_all_invalid_commands() {
		List<String> input = new ArrayList<>();
		input.add("create investment 87654321 2.0"); // invalid
		input.add("withdraw 12345678 50"); // invalid

		List<String> result = masterControl.start(input);

		assertEquals(2, result.size());
		assertEquals("create investment 87654321 2.0", result.get(0));
		assertEquals("withdraw 12345678 50", result.get(1));
	}

	@Test
	public void test_all_valid_commands() {
		List<String> input = new ArrayList<>();
		input.add("create checking 12345678 1.0"); // valid
		input.add("deposit 12345678 100"); // valid

		List<String> result = masterControl.start(input);

		assertEquals(0, invalidCommands.getInvalidCommands().size());
	}
}