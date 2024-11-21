import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AccountTypeValidationTest {

	@Test
	void valid_account_type_checking_should_return_true() {
		assertTrue(AccountTypeValidation.isValidAccountType("checking"));
	}

	@Test
	void valid_account_type_savings_should_return_true() {
		assertTrue(AccountTypeValidation.isValidAccountType("savings"));
	}

	@Test
	void valid_account_type_cd_should_return_true() {
		assertTrue(AccountTypeValidation.isValidAccountType("cd"));
	}

	@Test
	void invalid_account_type_should_return_false() {
		assertFalse(AccountTypeValidation.isValidAccountType("Investment"));
	}
}