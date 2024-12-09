import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
	private static Map<String, Account> accountsMap;

	private final TransactionLogger transactionLogger;

	public Bank() {
		accountsMap = new LinkedHashMap<>();
		transactionLogger = new TransactionLogger();
	}

	public static Account getAccountByID(String accountID) {
		return accountsMap.get(accountID);
	}

	public static void addAccount(Account account) {
		if (accountsMap.containsKey(account.getAccountID())) {
			throw new IllegalArgumentException("Account with ID " + account.getAccountID() + " already exists");
		}
		accountsMap.put(account.getAccountID(), account);

	}

	public static Map<String, Account> getAccountsMap() {
		return accountsMap;
	}

	public void depositByID(String accountID, double amount) {
		Account account = getAccountByID(accountID);
		if (account != null) {
			account.deposit(amount);
		}
	}

	public void withdrawByID(String accountID, double amount) {
		Account account = getAccountByID(accountID);
		if (account != null) {
			account.withdraw(amount);
		}
	}

	public int getNumberOfAccounts() {
		return accountsMap.size();
	}

	public void removeAccount(String accountID) {
		accountsMap.remove(accountID);
		transactionLogger.removeTransactionsForAccount(accountID);
	}

	public void processPassTime(int months) {
		for (int i = 0; i < months; i++) {
			// close accountsMap with $0 balance
			accountsMap.values().removeIf(Account::isZeroBalance);

			// min balance fees + accrue APR for each account
			for (Account account : accountsMap.values()) {
				account.deductMinimumBalanceFee();
				account.accrueMonthlyApr();
			}
		}
	}

}
