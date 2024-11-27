import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CdAccountValidationTest {

	@Test
	void valid_cd_creation_should_return_true() {
		CertificateOfDeposit cd = new CertificateOfDeposit("12345678", 1.2, 2000);
		assertTrue(CdAccountValidation.validateCdBalance(cd.getBalance()));
		assertTrue(CdAccountValidation.validateApr(cd.getAPR()));
	}

	@Test
	void invalid_cd_creation_balance_below_minimum_should_return_false() {
		CertificateOfDeposit cd = new CertificateOfDeposit("12345678", 1.2, 500);
		assertFalse(CdAccountValidation.validateCdBalance(cd.getBalance()));
	}

	@Test
	void invalid_cd_creation_balance_above_maximum_should_return_false() {
		CertificateOfDeposit cd = new CertificateOfDeposit("12345678", 1.2, 15000);
		assertFalse(CdAccountValidation.validateCdBalance(cd.getBalance()));
	}

	@Test
	void invalid_cd_creation_apr_out_of_range_should_return_false() {
		CertificateOfDeposit cd = new CertificateOfDeposit("12345678", 15.0, 2000);
		assertFalse(CdAccountValidation.validateApr(cd.getAPR()));
	}
}