public class CommandProcessor {
	private Bank bank;
	private CreateCommand createCommand;
	private DepositCommand depositCommand;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;
	private InvalidCommands invalidCommands;
	private TransactionLogger transactionLogger;

	public CommandProcessor(Bank bank) {
		this.invalidCommands = new InvalidCommands();
		this.transactionLogger = new TransactionLogger();
		this.createCommand = new CreateCommand(bank, invalidCommands);
		this.depositCommand = new DepositCommand(bank);
		this.transferCommandProcessor = new TransferCommandProcessor(transactionLogger);
		this.passCommandProcessor = new PassCommandProcessor(bank, transactionLogger);

	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		if (!isValidCommand(parts)) {
			invalidCommands.addInvalidCommand(command.toString());
			return;
		}
		switch (parts[0].toLowerCase()) {
		case "create":
			createCommand.execute(parts);
			break;
		case "deposit":
			depositCommand.execute(parts);
			break;
		case "transfer":
			TransferCommand transferCommand = parseTransferCommand(parts);
			if (transferCommand != null) {
				transferCommandProcessor.process(transferCommand, bank);
			} else {
				invalidCommands.addInvalidCommand(command);
			}
			break;
		case "pass":
			passCommandProcessor.process(command);
			break;

		default:
			invalidCommands.addInvalidCommand(command.toString());
		}
	}

	private TransferCommand parseTransferCommand(String[] parts) {
		if (parts.length != 4) {
			return null;
		}
		try {
			String fromId = parts[1];
			String toId = parts[2];
			double amount = Double.parseDouble(parts[3]);
			return new TransferCommand(fromId, toId, amount, String.join(" ", parts));
		} catch (NumberFormatException e) {
			return null;
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