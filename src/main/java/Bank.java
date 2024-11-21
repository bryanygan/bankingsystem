import java.util.HashMap;
import java.util.Map;

public class Bank {
	private static Map<String, Account> accounts = new HashMap<>();

	public Bank() {
		accounts = new HashMap<>();
	}

	public static Account getAccountByID(String accountID) {
		return accounts.get(accountID);
	}

	public static void addAccount(Account account) {
		if (accounts.containsKey(account.getAccountID())) {
			throw new IllegalArgumentException(
					"banking.Account with ID " + account.getAccountID() + " already exists.");
		}
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
}
