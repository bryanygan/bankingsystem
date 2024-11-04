import static org.junit.jupiter.api.Assertions.assertFalse;
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

	@Test
	public void test_null_command() {
		assertFalse(validator.validateCreateCommand(null));
	}

	@Test
	public void test_empty_command() {
		assertFalse(validator.validateCreateCommand(""));
	}

	@Test
	public void test_missing_parts_in_command() {
		assertFalse(validator.validateCreateCommand("create savings"));
	}

	@Test
	public void test_invalid_start_of_command() {
		assertFalse(validator.validateCreateCommand("open savings 12345678 0.6"));
	}

	@Test
	public void test_account_number_with_leading_zeros() {
		assertTrue(validator.validateCreateCommand("create cd 00000001 1.5"));
	}

}
