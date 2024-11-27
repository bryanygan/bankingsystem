import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvalidCommandsTest {
	private InvalidCommands store;

	@BeforeEach
	public void setUp() {
		store = new InvalidCommands();
	}

	@Test
	public void test_add_invalid_command() {
		store.addInvalidCommand("invalid command 1");
		List<String> commands = store.getInvalidCommands();

		assertEquals(1, commands.size());
		assertEquals("invalid command 1", commands.get(0));
	}

	@Test
	public void test_get_invalid_commands() {
		store.addInvalidCommand("invalid command 1");
		store.addInvalidCommand("invalid command 2");

		List<String> commands = store.getInvalidCommands();

		assertEquals(2, commands.size());
		assertEquals("invalid command 1", commands.get(0));
		assertEquals("invalid command 2", commands.get(1));
	}

	@Test
	public void test_retrieve_empty_list_when_no_invalid_commands() {
		List<String> commands = store.getInvalidCommands();
		assertEquals(0, commands.size());
	}
}