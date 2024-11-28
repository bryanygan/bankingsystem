public class CertificateOfDeposit extends Account {
	public CertificateOfDeposit(String accountID, double APR, double initialBalance) {
		super(accountID, APR);
		this.balance = initialBalance;
		this.type = AccountType.CD;

	}

	@Override
	public boolean deposit(double amount) {
		return false;
	}
}