public class TransferCommand {
	private String fromId;
	private String toId;
	private double amount;
	private String rawCommand;

	public TransferCommand(String rawCommand) {
		this.rawCommand = rawCommand;
	}

	public boolean validate(Bank bank) {
		String[] parts = rawCommand.split(" ");
		if (parts.length != 4) {
			return false;
		}

		fromId = parts[1];
		toId = parts[2];

		try {
			amount = Double.parseDouble(parts[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (amount <= 0 || fromId.equals(toId)) {
			return false;
		}

		Account fromAccount = Bank.getAccountByID(fromId);
		Account toAccount = Bank.getAccountByID(toId);

		if (fromAccount == null || toAccount == null) {
			return false;
		}

		if (fromAccount.getType().equals("CD") || toAccount.getType().equals("CD")) {
			return false;
		}

		return true;
	}

	/*
	 * public void execute(Bank bank) { Account fromAccount =
	 * Bank.getAccountByID(fromId); Account toAccount = Bank.getAccountByID(toId);
	 * 
	 * double withdrawalAmount = fromAccount.withdraw(amount);
	 * toAccount.deposit(withdrawalAmount);
	 * 
	 * }
	 */
}