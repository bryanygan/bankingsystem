//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//public class OutputTest {
//	private final Bank bank = new Bank();
//	private final CommandProcessor commandProcessor = new CommandProcessor(bank);
//	private final MasterControl masterControl = new MasterControl(bank, commandProcessor, invalidCommands);
//	private final InvalidCommands invalidCommands = new InvalidCommands();
//
//	@Test
//	void test_create_savings_account() {
//		List<String> input = List.of("create savings 12345678 0.6");
//		List<String> output = masterControl.start(input);
//
//		assertEquals(1, output.size());
//		assertEquals("Savings 12345678 0.00 0.60", output.get(0));
//	}
//
//}
