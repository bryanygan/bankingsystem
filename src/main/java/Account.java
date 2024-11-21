public class Account {
	protected String accountID;
	protected double balance;
	protected double APR;

	protected Account(String accountID, double APR) {
		this.balance = 0;
		this.accountID = accountID;
		this.APR = APR;
	}

	public String getAccountID() {
		return accountID;
	}

	public double getBalance() {
		return balance;
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
}
