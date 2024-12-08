public class CreateCommand {

	public CreateCommand(Bank bank) {
	}

	public void execute(String[] commandParts) {
		if (commandParts.length < 4) {
			throw new IllegalArgumentException("Invalid create command format");
		}
		String accountType = commandParts[1];
		String accountId = commandParts[2];
		double apr = Double.parseDouble(commandParts[3]);

		switch (accountType) {
		case "checking":
			Checking checkingAccount = new Checking(accountId, apr);
			Bank.addAccount(checkingAccount);
			break;
		case "savings":
			Savings savingsAccount = new Savings(accountId, apr);
			Bank.addAccount(savingsAccount);
			break;
		case "cd":
			if (commandParts.length != 5) {
				// no initial balance error
				throw new IllegalArgumentException("Invalid CD command format");
			}
			double initialBalance = Double.parseDouble(commandParts[4]);
			CertificateOfDeposit cdAccount = new CertificateOfDeposit(accountId, apr, initialBalance);
			Bank.addAccount(cdAccount);
			break;
		default:
			throw new UnsupportedOperationException("Account type not supported");
		}
	}
}