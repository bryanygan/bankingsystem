public class CommandProcessor {
	private CreateCommand createCommand;
	private DepositCommand depositCommand;

	public CommandProcessor(Bank bank) {
		this.createCommand = new CreateCommand(bank);
		this.depositCommand = new DepositCommand(bank);
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		if (!isValidCommand(parts)) {
			throw new IllegalArgumentException("Invalid command: " + command);
		}
		switch (parts[0]) {
		case "create":
			createCommand.execute(parts);
			break;
		case "deposit":
			depositCommand.execute(parts);
			break;
		default:
			throw new UnsupportedOperationException("Command not supported");
		}
	}

	private boolean isValidCommand(String[] parts) {
		if (parts.length < 2) {
			return false;
		}
		String commandType = parts[0].toLowerCase();
		return commandType.equals("create") || commandType.equals("deposit") || commandType.equals("withdraw")
				|| commandType.equals("transfer") || commandType.equals("pass");
	}

	private void processCreateCommand(String[] parts) {
		if (parts.length < 4) {
			throw new IllegalArgumentException("Invalid create command: " + String.join(" ", parts));
		}
		String accountType = parts[1].toLowerCase();
		String id = parts[2];
		double apr = Double.parseDouble(parts[3]);

		if (accountType.equals("savings")) {
			Savings savings = new Savings(id, apr);
			Bank.addAccount(savings);
		} else {
			throw new IllegalArgumentException("Invalid account type: " + accountType);
		}
	}
}