public class CommandProcessor {
	private CreateCommand createCommand;
	private DepositCommand depositCommand;

	public CommandProcessor(Bank bank) {
		this.createCommand = new CreateCommand(bank);
		this.depositCommand = new DepositCommand(bank);
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
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
}