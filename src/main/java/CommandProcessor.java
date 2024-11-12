public class CommandProcessor {
	private final CreateCommand createCommand;
	private final DepositCommand depositCommand;

	public CommandProcessor(Bank bank) {
		this.createCommand = new CreateCommand(bank);
		this.depositCommand = new DepositCommand(bank);
	}

	public void process(String command) {
		String[] parts = command.split(" ");
		String action = parts[0].toLowerCase();

		switch (action) {
		case "create":
			createCommand.execute(parts);
			break;
		case "deposit":
			depositCommand.execute(parts);
			break;
		default:
			throw new UnsupportedOperationException("Unsupported command: " + action);
		}
	}
}