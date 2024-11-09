
public class CommandValidation {

	private final AccountNumberValidation accountNumberValidation = new AccountNumberValidation();

	public boolean validateCreateCommand(String command) {
		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length < 3 || parts.length > 5 || !parts[0].equals("create")) {
			return false;
		}

		String accountType = parts[1];
		String accountNumber = parts[2];
		String additionalParam = parts.length > 3 ? parts[3] : null;

		if (!AccountTypeValidation.isValidAccountType(accountType)) {
			return false;
		}

		if (!accountNumberValidation.isValidAccountNumber(accountNumber)
				|| !accountNumberValidation.isUniqueAccountId(accountNumber)) {
			return false;
		}

		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !additionalParam.matches("\\d+(\\.\\d{1,2})?") || parts.length > 4) {
				return false;
			}
		} else if (accountType.equals("cd")) {
			if (parts.length != 5) {
				return false;
			}
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !CdAccountValidation.validateApr(Double.parseDouble(additionalParam))) {
				return false;
			}
			double cdBalance = Double.parseDouble(parts[4]);
			if (!CdAccountValidation.validateCdBalance(cdBalance)) {
				return false;
			}
		}

		accountNumberValidation.registerAccountId(accountNumber);
		return true;
	}

	private boolean isPositiveDecimal(String str) {
		try {
			double value = Double.parseDouble(str);
			return value > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
