public class Savings extends Account {
	public Savings(String accountID, double APR) {
		super(accountID, APR);
		this.type = AccountType.Savings;
	}

	@Override
	public boolean deposit(double amount) {
		if (amount > 2500 || amount < 0) {
			return false;
		}
		super.deposit(amount);
		return true;
	}
}
