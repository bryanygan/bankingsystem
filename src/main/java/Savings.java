public class Savings extends Account {
	public Savings(String accountID, double APR) {
		super(accountID, APR);
	}

	@Override
	public boolean deposit(double amount) {
		if (amount > 2500) {
			return false;
		}
		super.deposit(amount);
		return true;
	}
}
