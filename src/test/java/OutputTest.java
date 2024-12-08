import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class OutputTest {
	private final Bank bank = new Bank();
	private final CommandProcessor commandProcessor = new CommandProcessor(bank);
	private final InvalidCommands invalidCommands = new InvalidCommands();
	private final MasterControl masterControl = new MasterControl(bank, commandProcessor, invalidCommands);

	@Test
	public void test_create_savings_account() {
		List<String> input = List.of("create savings 12345678 0.6");
		masterControl.start(input);

		Map<String, Account> accountsMap = bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

	@Test
	public void typo_in_create_command_is_invalid() {
		List<String> input = List.of("craete savings 12345678 0.6");
		List<String> output = masterControl.start(input);

		assertEquals(1, output.size());
		assertEquals("craete savings 12345678 0.6", output.get(0));
	}

	@Test
	public void test_mixed_valid_and_invalid_commands() {
		List<String> input = List.of("create savings 12345678 0.6", "depositt 12345678 500");
		List<String> output = masterControl.start(input);

		assertEquals(1, output.size());
		assertEquals("depositt 12345678 500", output.get(0));

		Map<String, Account> accountsMap = bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

	@Test
	public void test_case_insensitive_create_command() {
		List<String> input = List.of("CrEaTe savings 87654321 0.3");
		masterControl.start(input);

		Map<String, Account> accountsMap = bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 87654321 0.00 0.30", accountsMap.get("87654321").toString());
	}

	@Test
	void test_duplicate_account_id_is_invalid() {
		List<String> input = List.of("create savings 12345678 0.6", "create savings 12345678 0.7");
		List<String> output = masterControl.start(input);

		assertEquals(1, output.size());
		assertEquals("create savings 12345678 0.7", output.get(0));

		Map<String, Account> accountsMap = bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

}
