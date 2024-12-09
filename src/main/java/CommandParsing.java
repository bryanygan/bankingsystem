
public class CommandParsing {

	public static String[] parseCommand(String command) {
		if (command == null || command.isEmpty() || !command.equals(command.trim())) {
			return null;
		}

		String normalizedCommand = command.trim().toLowerCase();
		return normalizedCommand.split("\\s+");
	}
}
