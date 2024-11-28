public class Checking extends Account {
	public Checking(String accountID, double APR) {
		super(accountID, APR);
		this.type = AccountType.CHECKING;
	}

	@Override
	public boolean deposit(double amount) {
		if (amount > 1000) {
			return false;
		}
		super.deposit(amount);
		return true;
	}
}
