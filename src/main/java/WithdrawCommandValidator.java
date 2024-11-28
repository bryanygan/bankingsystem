public class WithdrawCommandValidator {
	public boolean isValidCommand(String command, Bank bank) {
		String[] parts = command.trim().split("\\s+");
		if (parts.length != 3 || !parts[0].equalsIgnoreCase("withdraw")) {
			return false;
		}

		String accountId = parts[1];
		if (!accountId.matches("\\d{8}")) {
			return false;
		}

		return true;
	}
}
