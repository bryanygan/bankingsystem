import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class OutputTest {
	private final Bank bank = new Bank();
	private final CommandProcessor commandProcessor = new CommandProcessor(bank);
	private final InvalidCommands invalidCommands = new InvalidCommands();
	private final CommandValidation commandValidation = new CommandValidation(bank, invalidCommands);
	private final TransactionLogger transactionLogger = new TransactionLogger();
	private final MasterControl masterControl = new MasterControl(commandValidation, commandProcessor, invalidCommands,
			transactionLogger);
	List<String> input = new ArrayList<>();

	@Test
	public void test_create_savings_account() {
		List<String> input = List.of("create savings 12345678 0.6");
		masterControl.start(input);

		Map<String, Account> accountsMap = Bank.getAccountsMap();
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

		assertEquals(1, invalidCommands.getInvalidCommands().size());
		assertEquals("depositt 12345678 500", invalidCommands.getInvalidCommands().get(0));

		Map<String, Account> accountsMap = Bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

	@Test
	public void test_case_insensitive_create_command() {
		List<String> input = List.of("CrEaTe savings 87654321 0.3");
		masterControl.start(input);

		Map<String, Account> accountsMap = Bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 87654321 0.00 0.30", accountsMap.get("87654321").toString());
	}

	@Test
	void test_duplicate_account_id_is_invalid() {
		List<String> input = List.of("create savings 12345678 0.6", "create savings 12345678 0.7");
		List<String> output = masterControl.start(input);

		assertEquals(1, invalidCommands.getInvalidCommands().size());
		assertEquals("create savings 12345678 0.7", invalidCommands.getInvalidCommands().get(0));

		Map<String, Account> accountsMap = Bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

//	@Test
//	public void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
//		Bank bank = new Bank();
//
//		input.add("Create savings 12345678 0.6");
//		input.add("Deposit 12345678 700");
//		input.add("Deposit 12345678 5000");
//		input.add("creAte cHecKing 98765432 0.01");
//		input.add("Deposit 98765432 300");
//		input.add("Transfer 98765432 12345678 300");
//		input.add("Pass 1");
//		input.add("Create cd 23456789 1.2 2000");
//
//		List<String> actual = masterControl.start(input);
//
//		Map<String, Account> accounts = Bank.getAccountsMap();
//		System.out.println("Accounts Map: " + accounts);
//
//		assertEquals(5, actual.size());
//		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
//		assertEquals("Deposit 12345678 700", actual.get(1));
//		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
//		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
//		assertEquals("Deposit 12345678 5000", actual.get(4));
//	}

//	@Test
//	public void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
//		input.add("Create savings 12345678 0.6");
//		input.add("Deposit 12345678 700");
//		input.add("Deposit 12345678 5000");
//		input.add("creAte cHecKing 98765432 0.01");
//		input.add("Deposit 98765432 300");
//		input.add("Transfer 98765432 12345678 300");
//		input.add("Pass 1");
//		input.add("Create cd 23456789 1.2 2000");
//		List<String> actual = masterControl.start(input);
//		assertEquals(5, actual.size());
//		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
//		assertEquals("Deposit 12345678 700", actual.get(1));
//		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
//		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
//		assertEquals("Deposit 12345678 5000", actual.get(4));
//	}

}
