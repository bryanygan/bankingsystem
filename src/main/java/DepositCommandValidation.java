public class DepositCommandValidation {

	private final AccountNumberValidation accountNumberValidation = new AccountNumberValidation();

	public boolean validateDepositCommand(String command) {

		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length != 3 || !parts[0].equalsIgnoreCase("deposit")) {
			return false;
		}

		String accountId = parts[1];
		String amountStr = parts[2];

		Account account = Bank.getAccountByID(accountId);
		System.out.println("Debug: Account type: " + account.getType());

		if (accountNumberValidation.isValidAccountNumber(accountId)) {
			return false;
		}

		if (accountNumberValidation.isUniqueAccountId(accountId)) {
			return false;
		}

		double amount;
		try {
			amount = Double.parseDouble(amountStr);
		} catch (NumberFormatException e) {
			return false;
		}

		if (amount < 0) {
			return false;
		}

		if (amount > 2500 && account.getType() == Account.AccountType.Savings) {
			return false;
		}
		if (amount > 1000 && account.getType() == Account.AccountType.Checking) {
			return false;
		}

		if (account instanceof Savings) {
			return amount <= 2500;
		} else if (account instanceof Checking) {
			return amount <= 1000;
		} else if (account instanceof CertificateOfDeposit) {
			return false;
		}

		return false;
	}
}