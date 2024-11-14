import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	private Bank bank;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;
	private MasterControl masterControl;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		invalidCommands = new InvalidCommands();
		masterControl = new MasterControl(bank, commandProcessor, invalidCommands);
	}

	@Test
	public void test_empty_command_list() {
		List<String> commands = new ArrayList<>();
		List<String> result = masterControl.start(commands);

		assertEquals(0, result.size());
	}

	@Test
	public void test_process_valid_command() {
		List<String> commands = new ArrayList<>();
		commands.add("create checking 12345678 1.0");

		List<String> result = masterControl.start(commands);

		assertEquals(0, result.size());
	}

	@Test
	public void test_process_invalid_command() {
		List<String> commands = new ArrayList<>();
		commands.add("create investment 87654321 2.0");

		List<String> result = masterControl.start(commands);

		assertEquals(1, result.size());
		assertEquals("create investment 87654321 2.0", result.get(0));
	}

	@Test
	public void test_process_mixed_valid_and_invalid_commands() {
		List<String> commands = new ArrayList<>();
		commands.add("create checking 12345678 1.0"); // valid
		commands.add("create investment 87654321 2.0"); // invalid
		commands.add("deposit 12345678 100"); // valid
		commands.add("deposit 99999999 50"); // invalid

		List<String> result = masterControl.start(commands);

		assertEquals(2, result.size());
		assertEquals("create investment 87654321 2.0", result.get(0));
		assertEquals("deposit 99999999 50", result.get(1));
	}
}