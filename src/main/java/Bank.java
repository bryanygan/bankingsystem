import java.util.HashMap;
import java.util.Map;

public class Bank {
	private static Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public static Account getAccountByID(String accountID) {
		return accounts.get(accountID);
	}

	public static void addAccount(Account account) {
		accounts.put(account.getAccountID(), account);

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
		return accounts.size();
	}

	public void removeAccount(String id) {
		accounts.remove(id);
	}

	public void processPassTime(int months) {
		for (int i = 0; i < months; i++) {
			// close accounts with $0 balance
			accounts.values().removeIf(Account::isZeroBalance);

			// min balance fees + accrue APR for each account
			for (Account account : accounts.values()) {
				account.deductMinimumBalanceFee();
				account.accrueMonthlyApr();
			}
		}
	}

}
