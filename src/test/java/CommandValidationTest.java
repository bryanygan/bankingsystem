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
	public void test_invalid_account_type() {
		assertFalse(validator.validateCreateCommand("create investment 12345678 0.6"));
		assertFalse(validator.validateCreateCommand("create premium 87654321 1.0"));
	}

	@Test
	public void test_invalid_account_number_format() {
		assertFalse(validator.validateCreateCommand("create savings 1234 0.6"));
		assertFalse(validator.validateCreateCommand("create checking abcdefgh 500.0"));
	}

	@Test
	public void test_account_number_with_leading_zeros() {
		assertTrue(validator.validateCreateCommand("create cd 00000001 1.5"));
	}

	@Test
	public void test_negative_or_zero_initial_balance_for_savings_or_checking() {
		assertFalse(validator.validateCreateCommand("create savings 12345678 -100.0"));
		assertFalse(validator.validateCreateCommand("create checking 87654321 0"));
	}

	@Test
	public void test_invalid_format_for_balance_or_interest() {
		assertFalse(validator.validateCreateCommand("create savings 12345678 notanumber"));
		assertFalse(validator.validateCreateCommand("create cd 12345678 notanumber"));
	}

	@Test
	public void test_valid_cd_account_with_positive_interest() {
		assertTrue(validator.validateCreateCommand("create cd 12345678 1.2"));
	}

	@Test
	public void test_missing_initial_balance_for_savings_or_checking() {
		assertFalse(validator.validateCreateCommand("create savings 12345678"));
		assertFalse(validator.validateCreateCommand("create checking 87654321"));
	}

	@Test
	public void test_negative_or_zero_interest_rate_for_cd() {
		assertFalse(validator.validateCreateCommand("create cd 12345678 -0.5"));
		assertFalse(validator.validateCreateCommand("create cd 12345678 0"));
	}
}