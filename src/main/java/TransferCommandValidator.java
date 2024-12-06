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

		if (fromAccount.getType() == Account.AccountType.CD || toAccount.getType() == Account.AccountType.CD) {
			return false;
		}

		double amount = command.getAmount();
		if (amount <= 0) {
			return false;
		}

		if (fromAccount.getType() == Account.AccountType.SAVINGS && amount > 1000) {
			return false;
		}

		if (toAccount.getType() == Account.AccountType.CHECKING && amount > 1000) {
			return false;
		}

		if (toAccount.getType() == Account.AccountType.SAVINGS && amount > 2500) {
			return false;
		}

		// System.out.println("From Account Type: " + fromAccount.getType());
		// System.out.println("To Account Type: " + toAccount.getType());

		return true;
	}
}