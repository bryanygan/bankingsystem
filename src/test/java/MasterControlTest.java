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
		commands.add("create checking 12345678 1.0"); // valid command

		List<String> result = masterControl.start(commands);

		assertEquals(0, result.size());
	}
}