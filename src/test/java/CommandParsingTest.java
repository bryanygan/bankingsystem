import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CommandParsingTest {

	@Test
	public void test_valid_command_without_extra_spaces() {
		String command = "create checking 123 500.00";
		String[] expected = { "create", "checking", "123", "500.00" };
		assertArrayEquals(expected, CommandParsing.parseCommand(command));
	}

	@Test
	public void test_valid_command_with_extra_spaces_between_words() {
		String command = "create    cd   789   1500.75";
		String[] expected = { "create", "cd", "789", "1500.75" };
		assertArrayEquals(expected, CommandParsing.parseCommand(command));
	}

	@Test
	public void test_null_command() {
		String command = null;
		assertNull(CommandParsing.parseCommand(command));
	}

	@Test
	public void test_empty_command() {
		String command = "";
		assertNull(CommandParsing.parseCommand(command));
	}

	@Test
	public void test_command_with_only_spaces() {
		String command = "     ";
		assertNull(CommandParsing.parseCommand(command));
	}

	@Test
	public void test_single_word_command() {
		String command = "exit";
		String[] expected = { "exit" };
		assertArrayEquals(expected, CommandParsing.parseCommand(command));
	}

}