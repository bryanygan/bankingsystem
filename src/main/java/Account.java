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

	public boolean isZeroBalance() {
		return getBalance() == 0;
	}

	public void deductMinimumBalanceFee() {
		if (getBalance() < 100) {
			setBalance(getBalance() - 25);
		}
	}

	public void accrueMonthlyApr() {
		if (getBalance() > 0) {
			double monthlyRate = (getAPR() / 100) / 12;
			double newBalance = getBalance() + (getBalance() * monthlyRate);
			setBalance((newBalance));
		}
	}

	public enum AccountType {
		Checking, Savings, Cd
	}

	@Override
	public String toString() {
		return String.format("%s %s %.2f %.2f", type, accountID, balance, APR);
	}

}
