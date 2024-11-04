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

		return true;
	}

	private boolean isValidAccountType(String accountType) {
		if (accountType.equals("savings")) {
			return true;
		}

		if (accountType.equals("checking")) {
			return true;
		}

		if (accountType.equals("cd")) {
			return true;
		}

		return false;
	}

	// check if account number is 8 digits
	private boolean isValidAccountNumber(String accountNumber) {
		if (!accountNumber.matches("\\d{8}")) {
			return false;
		}

		return true;
	}

}