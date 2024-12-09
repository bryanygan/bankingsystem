import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionLogger {

	private final Map<String, List<String>> transactionLogByAccount;

	public TransactionLogger() {
		this.transactionLogByAccount = new HashMap<>();
	}

	public void logTransaction(String accountID, String transactionType, double amount) {
		String logEntry = String.format("%s %s %.2f", transactionType, accountID, amount);
		transactionLogByAccount.computeIfAbsent(accountID, k -> new ArrayList<>()).add(logEntry);
	}

	public void logTransactionRaw(String accountID, String rawCommand) {
		if (rawCommand != null && !rawCommand.trim().isEmpty()) {
			String[] commandParts = CommandParsing.parseCommand(rawCommand);
			if (commandParts != null && commandParts.length > 0 && "transfer".equalsIgnoreCase(commandParts[0])) {
				if (commandParts.length >= 4) {
					String fromID = commandParts[1];
					String toID = commandParts[2];

					transactionLogByAccount.computeIfAbsent(fromID, k -> new ArrayList<>()).add(rawCommand);
					transactionLogByAccount.computeIfAbsent(toID, k -> new ArrayList<>()).add(rawCommand);
				} else {
					transactionLogByAccount.computeIfAbsent(accountID, k -> new ArrayList<>()).add(rawCommand);
				}
			} else {
				transactionLogByAccount.computeIfAbsent(accountID, k -> new ArrayList<>()).add(rawCommand);
			}
		}
	}

	public void removeTransactionsForAccount(String accountID) {
		transactionLogByAccount.remove(accountID);
	}

	public List<String> getTransactionLog(String accountID) {
		return transactionLogByAccount.getOrDefault(accountID, new ArrayList<>());
	}

	public List<String> generateOutput(String accountState) {
		String accountID = accountState.split(" ")[1];
		List<String> transactions = transactionLogByAccount.getOrDefault(accountID, new ArrayList<>());

		List<String> output = new ArrayList<>();
		output.add(accountState);
		output.addAll(transactions);
		return output;
	}
}