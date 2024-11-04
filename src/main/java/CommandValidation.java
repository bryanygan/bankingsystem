public class CommandValidation {

	public boolean validateCreateCommand(String command) {
		if (command == null) {
			return false;
		}

		if (command.isEmpty()) {
			return false;
		}

		// split command string
		String[] parts = command.split(" ");

		// validate command has at least create, account type, and account number
		if (parts.length < 3) {
			return false;
		}

		// command must start with "create"
		if (!parts[0].equals("create")) {
			return false;
		}
		return false;
	}
}