public class WithdrawCommandProcessor {
	public void processCommand(String command, Bank bank) {
		String[] parts = command.split("\\s+");
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);

		Account account = Bank.getAccountByID(accountId);
		double newBalance = Math.max(0, account.getBalance() - amount);
		account.setBalance(newBalance);
	}
}
