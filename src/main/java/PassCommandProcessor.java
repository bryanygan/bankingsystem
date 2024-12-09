import java.util.ArrayList;
import java.util.List;

public class PassCommandProcessor {
	private final Bank bank;
	private final TransactionLogger transactionLogger;

	public PassCommandProcessor(Bank bank, TransactionLogger transactionLogger) {
		this.bank = bank;
		this.transactionLogger = transactionLogger;
	}

	public void process(String command) {
		if (!command.toLowerCase().startsWith("pass ")) {
			return;
		}

		String[] parts = command.split(" ");
		if (parts.length != 2) {
			return;
		}

		int months;
		try {
			months = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			return;
		}

		if (months < 1 || months > 60) {
			return;
		}

		for (int i = 0; i < months; i++) {
			List<Account> accounts = new ArrayList<>(Bank.getAccountsMap().values());

			accounts.stream().filter(Account::isZeroBalance)
					.forEach(account -> bank.removeAccount(account.getAccountID()));

			for (Account account : accounts) {
				if (!account.isZeroBalance()) {
					account.deductMinimumBalanceFee();
					account.accrueMonthlyApr();
				}
			}
		}
	}
}