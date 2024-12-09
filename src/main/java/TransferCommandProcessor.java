public class TransferCommandProcessor {

	private final TransactionLogger transactionLogger;

	public TransferCommandProcessor(TransactionLogger transactionLogger) {
		this.transactionLogger = transactionLogger;
	}

	public void process(TransferCommand command, Bank bank) {
		Account fromAccount = Bank.getAccountByID(command.getFromId());
		Account toAccount = Bank.getAccountByID(command.getToId());

		if (fromAccount == null || toAccount == null) {
			throw new IllegalArgumentException();
		}

		double transferAmount = command.getAmount();

		double actualWithdrawn = fromAccount.withdraw(transferAmount);

		toAccount.deposit(actualWithdrawn);

		System.out.println("Raw Transfer Command: " + command.getRawCommand());

		String rawCommand = command.getRawCommand();

		transactionLogger.logTransactionRaw(command.getFromId(), rawCommand);
		transactionLogger.logTransactionRaw(command.getToId(), rawCommand);
	}
}