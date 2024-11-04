import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandValidationTest {

	private final CommandValidation validator = new CommandValidation();

	@Test
	public void test_valid_create_savings_account() {
		assertTrue(validator.validateCreateCommand("create savings 12345678 0.6"));
	}

	@Test
	public void test_valid_create_checking_account() {
		assertTrue(validator.validateCreateCommand("create checking 87654321 500.0"));
	}

	@Test
	public void test_valid_create_cd_account() {
		assertTrue(validator.validateCreateCommand("create cd 12345678 1.5"));
	}

}
