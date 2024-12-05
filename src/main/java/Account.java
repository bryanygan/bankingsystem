public class Account {
	protected String accountID;
	protected double balance;
	protected double APR;
	protected AccountType type;

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

	public double withdraw(double amount) {
		String account = getAccountID();
		if (account == null) {
			throw new IllegalArgumentException("No account with ID " + getAccountID());
		}

		double balance = getBalance();

		if (amount <= 0) {
			throw new IllegalArgumentException("Negative amount not allowed");
		}

		double withdrawn = Math.min(amount, balance);
		setBalance(balance - withdrawn);

		return withdrawn;
	}

	public AccountType getType() {
		return type;
	}

	public enum AccountType {
		CHECKING, SAVINGS, CD
	}

}
