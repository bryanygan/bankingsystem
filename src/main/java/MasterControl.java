import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private CommandValidation commandValidation;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;
	private TransactionLogger transactionLogger;

	public MasterControl(CommandValidation commandValidation, CommandProcessor commandProcessor,
			InvalidCommands invalidCommands, TransactionLogger transactionLogger) {
		this.commandValidation = commandValidation;
		this.commandProcessor = commandProcessor;
		this.invalidCommands = invalidCommands;
		this.transactionLogger = new TransactionLogger();
	}

	public List<String> start(List<String> input) {
		List<String> output = new ArrayList<>();

		for (String command : input) {
			if (commandValidation.validateCommand(command)) {
				System.out.println("Processing valid command: " + command);
				commandProcessor.processCommand(command);
				String[] commandParts = CommandParsing.parseCommand(command);
				String accountID = commandParts[1];
				transactionLogger.logTransaction(accountID, commandParts[0], Double.parseDouble(commandParts[2]));
			} else {
				System.out.println("Invalid command: " + command);
				invalidCommands.addInvalidCommand(command);
			}
		}
		for (String accountID : Bank.getAccountsMap().keySet()) {
			Account account = Bank.getAccountByID(accountID);
			String accountState = account.toString();
			output.add(transactionLogger.generateOutput(accountState));
		}

		output.addAll(invalidCommands.getInvalidCommands());
		System.out.println(output);
		return output;
	}
}