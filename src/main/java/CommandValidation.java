import java.util.HashSet;
import java.util.Set;

public class CommandValidation {

	// set to keep track of existing account IDs for uniqueness checking
	private Set<String> existingAccountIds = new HashSet<>();

	public static boolean validateCdBalance(double balance) {
		return balance >= 1000 && balance <= 10000;
	}

	public static boolean validateApr(double apr) {
		return apr >= 0 && apr <= 10 && (Math.floor(apr * 100) == apr * 100);
	}

	public boolean validateCreateCommand(String command) {
		if (command == null || command.isEmpty()) {
			return false;
		}

		if (!command.equals(command.trim())) {
			return false;
		}

		String[] parts = command.trim().split("\\s+");

		if (parts.length < 3 || parts.length > 5) {
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

		if (parts.length > 4) {
			if (accountType.equals("savings") || accountType.equals("checking")) {
				return false;
			} else if (accountType.equals("cd")) {
				try {
					double cdBalance = Double.parseDouble(parts[4]);

					if (!validateCdBalance(cdBalance)) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
			}
		}

		// validate account type
		if (!isValidAccountType(accountType)) {
			return false;
		}

		// validate account number format and uniqueness
		if (!isValidAccountNumber(accountNumber) || !isUniqueAccountId(accountNumber)) {
			return false;
		}

		// savings and checking accounts need valid initial balance
		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)) {
				return false;
				// check decimal places
			} else {
				if (!additionalParam.matches("\\d+(\\.\\d{1,2})?")) {
					return false;
				}
			}
		}

		// cd needs valid interest rate
		else if (accountType.equals("cd")) {
			if (parts.length != 5) {
				return false;
			}
			if (additionalParam == null || !isPositiveDecimal(additionalParam)) {
				return false;
			}
			double apr = Double.parseDouble(additionalParam);
			if (!validateApr(apr)) {
				return false;
			}
		}

		// register account ID as in use
		existingAccountIds.add(accountNumber);

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

	// check if account ID is unique
	private boolean isUniqueAccountId(String accountId) {
		return !existingAccountIds.contains(accountId);
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
}