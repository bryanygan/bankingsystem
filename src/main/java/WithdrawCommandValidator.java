public class WithdrawCommandValidator {
	public boolean isValidCommand(String command, Bank bank) {
		String[] parts = command.trim().split("\\s+");
		if (parts.length != 3 || !parts[0].equalsIgnoreCase("withdraw")) {
			return false;
		}

		String accountId = parts[1];
		String amountStr = parts[2];

		if (!accountId.matches("\\d{8}")) {
			return false;
		}

		try {
			double amount = Double.parseDouble(amountStr);
			if (amount < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
