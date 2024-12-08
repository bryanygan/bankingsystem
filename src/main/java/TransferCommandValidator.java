public class TransferCommandValidator {

	public boolean validate(TransferCommand command, Bank bank) {
		if (command.getFromId().equals(command.getToId())) {
			return false;
		}

		Account fromAccount = Bank.getAccountByID(command.getFromId());
		Account toAccount = Bank.getAccountByID(command.getToId());

		if (fromAccount == null || toAccount == null) {
			return false;
		}

		if (fromAccount.getType() == Account.AccountType.Cd || toAccount.getType() == Account.AccountType.Cd) {
			return false;
		}

		double amount = command.getAmount();
		if (amount <= 0) {
			return false;
		}

		if (fromAccount.getType() == Account.AccountType.Savings && amount > 1000) {
			return false;
		}

		if (toAccount.getType() == Account.AccountType.Checking && amount > 400) {
			return false;
		}

		if (toAccount.getType() == Account.AccountType.Savings && amount > 2500) {
			return false;
		}

		// System.out.println("From Account Type: " + fromAccount.getType());
		// System.out.println("To Account Type: " + toAccount.getType());

		return true;
	}
}