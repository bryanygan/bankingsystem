public class DepositCommand {
	private final Bank bank;

	public DepositCommand(Bank bank) {
		this.bank = bank;
	}

	public void execute(String[] parts) {
		if (parts.length < 3) {
			throw new IllegalArgumentException("Invalid deposit command format.");
		}

		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);

		bank.depositByID(accountId, amount);
	}
}