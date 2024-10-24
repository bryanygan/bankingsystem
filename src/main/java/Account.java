public abstract class Account {
	protected String accountID;
	protected double balance;
	protected double APR;

	public Account(String accountID, double APR) {
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

	public void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		}
	}

	public void withdraw(double amount) {
		if (amount > 0) {
			if (amount >= balance) {
				balance = 0;
			} else {
				balance -= amount;
			}
		}
	}
}
