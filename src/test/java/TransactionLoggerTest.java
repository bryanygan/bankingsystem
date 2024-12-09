import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TransactionLoggerTest {

	@Test
	public void test_log_single_transaction() {
		TransactionLogger logger = new TransactionLogger();
		String accountID = "12345678";
		String transactionType = "Deposit";
		double amount = 700;

		logger.logTransaction(accountID, transactionType, amount);

		String expectedLog = "Deposit 12345678 700.00";
		assertEquals(expectedLog, logger.getTransactionLog().get(0));
	}

	@Test
	public void test_log_multiple_transactions() {
		TransactionLogger logger = new TransactionLogger();
		String accountID = "12345678";

		logger.logTransaction(accountID, "Deposit", 700);
		logger.logTransaction(accountID, "Withdraw", 300);

		List<String> logs = logger.getTransactionLog();
		assertEquals(2, logs.size());
		assertEquals("Deposit 12345678 700.00", logs.get(0));
		assertEquals("Withdraw 12345678 300.00", logs.get(1));
	}

	@Test
	public void test_output_formatting() {
		TransactionLogger logger = new TransactionLogger();
		String accountID = "12345678";
		String accountState = "Savings 12345678 1000.50 0.60";

		logger.logTransaction(accountID, "Deposit", 700);
		logger.logTransaction(accountID, "Withdraw", 300);

		String formattedOutput = logger.generateOutput(accountState);

		String expectedOutput = """
				Savings 12345678 1000.50 0.60
				Deposit 12345678 700.00
				Withdraw 12345678 300.00
				""";

		assertEquals(expectedOutput.trim(), formattedOutput.trim());
	}
}