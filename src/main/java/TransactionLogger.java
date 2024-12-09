import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {

	private final List<String> transactionLog;

	public TransactionLogger() {
		this.transactionLog = new ArrayList<>();
	}

	public void logTransaction(String accountID, String transactionType, double amount) {
		String logEntry = String.format("%s %s %.2f", transactionType, accountID, amount);
		transactionLog.add(logEntry);
	}

	public List<String> getTransactionLog() {
		return transactionLog;
	}

	public String generateOutput(String accountState) {
		StringBuilder output = new StringBuilder();
		output.append(accountState).append("\n");
		for (String log : transactionLog) {
			output.append(log).append("\n");
		}
		return output.toString().trim();
	}
}