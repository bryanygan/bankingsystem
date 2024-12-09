import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private CommandValidation commandValidation;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;

	public MasterControl(CommandValidation commandValidation, CommandProcessor commandProcessor,
			InvalidCommands invalidCommands) {
		this.commandValidation = commandValidation;
		this.commandProcessor = commandProcessor;
		this.invalidCommands = invalidCommands;
	}

	public List<String> start(List<String> input) {
		List<String> output = new ArrayList<>();

		for (String command : input) {
			if (commandValidation.validateCommand(command)) {
				System.out.println("Processing valid command: " + command);
				commandProcessor.processCommand(command);
			} else {
				System.out.println("Invalid command: " + command);
				invalidCommands.addInvalidCommand(command);
			}
		}
		return invalidCommands.getInvalidCommands();
	}
}