public class Account {
	protected String accountID;
	protected double balance;
	protected double APR;
	protected AccountType type;

	protected Account(String accountID, double APR) {
		this.balance = 0;
		this.accountID = accountID;
		this.APR = APR;
		this.type = type;
	}

	public String getAccountID() {
		return accountID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double newBalance) {
		balance = newBalance;
		if (balance < 0) {
			balance = 0;
		}
	}

	public double getAPR() {
		return APR;
	}

	public boolean deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		}
		return false;
	}

	public boolean withdraw(double amount) {
		if (amount > 0) {
			if (amount >= balance) {
				balance = 0;
			} else {
				balance -= amount;
			}
		}
		return false;
	}

	public AccountType getType() {
		return type;
	}

	public enum AccountType {
		CHECKING, SAVINGS, CD
	}

}
