import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {

	@Test
	public void cd_account_starts_with_specific_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		assertEquals(1000, cd.getBalance());
	}

	@Test
	public void cd_account_correct_apr_value() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		assertEquals(4.0, cd.getAPR());
	}

	@Test
	public void cd_deposit_increases_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		assertEquals(1500, cd.getBalance());
	}

	@Test
	public void cd_withdraw_decreases_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(200);
		assertEquals(1300, cd.getBalance());
	}

	@Test
	public void cd_withdraw_more_than_balance_goes_to_zero() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(1600);
		assertEquals(0, cd.getBalance());
	}

	@Test
	public void cd_depositing_twice_works() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.deposit(500);
		assertEquals(2000, cd.getBalance());
	}

	@Test
	public void cd_withdrawing_twice_works() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.deposit(500);
		cd.withdraw(200);
		cd.withdraw(200);
		assertEquals(1100, cd.getBalance());
	}
}