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
		assertEquals(expectedLog, logger.getTransactionLog(accountID).get(0));
	}

	@Test
	public void test_log_multiple_transactions() {
		TransactionLogger logger = new TransactionLogger();
		String accountID = "12345678";

		logger.logTransaction(accountID, "Deposit", 700);
		logger.logTransaction(accountID, "Withdraw", 300);

		List<String> logs = logger.getTransactionLog(accountID);
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

		List<String> formattedOutput = logger.generateOutput(accountState);

		List<String> expectedOutput = List.of("Savings 12345678 1000.50 0.60", "Deposit 12345678 700.00",
				"Withdraw 12345678 300.00");

		assertEquals(expectedOutput, formattedOutput);
	}

	@Test
	public void test_log_transactions_for_multiple_accounts() {
		TransactionLogger logger = new TransactionLogger();

		String accountState1 = "Savings 12345678 1000.50 0.60";
		logger.logTransaction("12345678", "Deposit", 700);
		logger.logTransaction("12345678", "Transfer", 300);

		String accountState2 = "Checking 98765432 300.00 0.01";
		logger.logTransaction("98765432", "Deposit", 300);

		List<String> formattedOutput1 = logger.generateOutput(accountState1);
		List<String> formattedOutput2 = logger.generateOutput(accountState2);

		List<String> expectedOutput1 = List.of("Savings 12345678 1000.50 0.60", "Deposit 12345678 700.00",
				"Transfer 12345678 300.00");

		List<String> expectedOutput2 = List.of("Checking 98765432 300.00 0.01", "Deposit 98765432 300.00");

		assertEquals(expectedOutput1, formattedOutput1);
		assertEquals(expectedOutput2, formattedOutput2);
	}

}