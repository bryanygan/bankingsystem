import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		this.accounts = new HashMap<>();
	}

	public static Account getAccountByID(String accountID) {
		return accounts.get(accountID);
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
