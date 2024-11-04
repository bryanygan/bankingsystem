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

		// take each part of the command to validate
		String accountType = parts[1]; // second word should be the type "savings", "checking", or "cd"
		String accountNumber = parts[2]; // third part should be acc number
		String additionalParam = null; // initial balance, interest rate

		// if command has initial balance or interest, additionalParam is 4th
		if (parts.length > 3) {
			additionalParam = parts[3];
		}

		if (!isValidAccountType(accountType)) {
			return false;
		}

		if (!isValidAccountNumber(accountNumber)) {
			return false;
		}

		// savings and checking accounts need valid initial balance
		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)) {
				return false;
			}
		}

		// cd needs valid interest rate
		else if (accountType.equals("cd")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)) {
				return false;
			}
		}

		return true;
	}

	// same process for deposit commands
	public boolean validateDepositCommand(String command) {
		if (command == null) {
			return false;
		}

		if (command.isEmpty()) {
			return false;
		}

		String[] parts = command.split(" ");

		if (parts.length != 3) {
			return false;
		}

		if (!parts[0].equals("deposit")) {
			return false;
		}

		String accountNumber = parts[1];
		String amount = parts[2];

		if (!isValidAccountNumber(accountNumber)) {
			return false;
		}

		if (!isPositiveDecimal(amount)) {
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

	private boolean isPositiveDecimal(String str) {
		try {
			// try parsing as double
			double value = Double.parseDouble(str);

			if (value <= 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			// if it fails then not valid decimal
			return false;
		}

		return true;
	}

	private boolean isValidCDBalance(double balance) {
		return balance >= 1000 && balance <= 10000;
	}

	private boolean isValidAPR(double apr) {
		return apr > 0 && apr <= 10;
	}

}