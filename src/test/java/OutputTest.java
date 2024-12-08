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
	void test_create_savings_account() {
		List<String> input = List.of("create savings 12345678 0.6");
		masterControl.start(input);

		Map<String, Account> accountsMap = bank.getAccountsMap();
		assertEquals(1, accountsMap.size());
		assertEquals("Savings 12345678 0.00 0.60", accountsMap.get("12345678").toString());
	}

}
