public class CreateCommand {
	private final Bank bank;
	private InvalidCommands invalidCommands = new InvalidCommands();
	private AccountNumberValidation accountNumberValidation = new AccountNumberValidation();

	public CreateCommand(Bank bank, InvalidCommands invalidCommands) {
		this.bank = bank;
		this.invalidCommands = invalidCommands != null ? invalidCommands : new InvalidCommands();
		this.accountNumberValidation = accountNumberValidation;

	}

	public void execute(String[] commandParts) {
		if (commandParts.length < 4) {
			invalidCommands.addInvalidCommand(String.join(" ", commandParts));
			return;
		}

		String accountType = commandParts[1].toLowerCase();
		String accountId = commandParts[2];
		double apr = Double.parseDouble(commandParts[3]);

		switch (accountType) {
		case "checking":
			Checking checkingAccount = new Checking(accountId, apr);
			Bank.addAccount(checkingAccount);
			accountNumberValidation.registerAccountId(accountId);

			break;
		case "savings":
			Savings savingsAccount = new Savings(accountId, apr);
			Bank.addAccount(savingsAccount);
			break;
		case "cd":
			if (commandParts.length != 5) {
				invalidCommands.addInvalidCommand(String.join(" ", commandParts));
				return;
			}
			double initialBalance = Double.parseDouble(commandParts[4]);
			CertificateOfDeposit cdAccount = new CertificateOfDeposit(accountId, apr, initialBalance);
			Bank.addAccount(cdAccount);
			break;
		default:
			invalidCommands.addInvalidCommand(String.join(" ", commandParts));
		}
	}
}