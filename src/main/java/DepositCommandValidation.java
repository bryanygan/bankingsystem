public class DepositCommandValidation {

	private final AccountNumberValidation accountNumberValidation = new AccountNumberValidation();

	public boolean validateDepositCommand(String command) {

		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length != 3 || !parts[0].equalsIgnoreCase("deposit")) {
			System.out.println("1");
			return false;
		}

		String accountId = parts[1];
		String amountStr = parts[2];

		Account account = Bank.getAccountByID(accountId);

		if (accountNumberValidation.isValidAccountNumber(accountId)) {
			System.out.println("2");
			return false;
		}

		double amount;
		try {
			amount = Double.parseDouble(amountStr);
		} catch (NumberFormatException e) {
			System.out.println("4");
			return false;
		}

		if (amount < 0) {
			System.out.println("5");
			return false;
		}

		if (amount > 2500 && account.getType() == Account.AccountType.Savings) {
			System.out.println("6");
			return false;
		}
		if (amount > 1000 && account.getType() == Account.AccountType.Checking) {
			System.out.println("7");
			return false;
		}

		if (account instanceof Savings) {
			System.out.println("8");
			return amount <= 2500;
		} else if (account instanceof Checking) {
			System.out.println("9");
			return amount <= 1000;
		} else if (account instanceof CertificateOfDeposit) {
			System.out.println("10");
			return false;
		}
		System.out.println("11");

		return false;
	}
}