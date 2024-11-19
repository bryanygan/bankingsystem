import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
	public void cd_withdraw_decreases_balance() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.withdraw(200);
		assertEquals(800, cd.getBalance());
	}

	@Test
	public void cd_withdraw_more_than_balance_goes_to_zero() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.withdraw(1600);
		assertEquals(0, cd.getBalance());
	}

	@Test
	public void cd_withdrawing_twice_works() {
		CertificateOfDeposit cd = new CertificateOfDeposit("54321876", 4.0, 1000);
		cd.withdraw(200);
		cd.withdraw(200);
		assertEquals(600, cd.getBalance());
	}

	@Test
	public void test_cd_account_deposit_not_allowed() {
		CertificateOfDeposit cd = new CertificateOfDeposit("24680", 0.03, 1000);
		assertFalse(cd.deposit(500));
	}
}