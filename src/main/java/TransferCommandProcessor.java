public class TransferCommandProcessor {

	public void process(TransferCommand command, Bank bank) {
		Account fromAccount = Bank.getAccountByID(command.getFromId());
		Account toAccount = Bank.getAccountByID(command.getToId());

		if (fromAccount == null || toAccount == null) {
			throw new IllegalArgumentException();
		}

		double transferAmount = command.getAmount();

		double actualWithdrawn = fromAccount.withdraw(transferAmount);

		toAccount.deposit(actualWithdrawn);
	}
}