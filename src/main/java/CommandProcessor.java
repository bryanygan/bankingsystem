public class CommandProcessor {
	private CreateCommand createCommand;
	private DepositCommand depositCommand;
	private InvalidCommands invalidCommands;

	public CommandProcessor(Bank bank) {
		this.createCommand = new CreateCommand(bank, invalidCommands);
		this.depositCommand = new DepositCommand(bank);
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		if (!isValidCommand(parts)) {
			throw new IllegalArgumentException("Invalid command: " + command);
		}
		switch (parts[0].toLowerCase()) {
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
}