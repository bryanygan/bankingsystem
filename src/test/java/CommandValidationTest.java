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

	@Test
	public void test_apr_zero() {
		assertFalse(validator.validateCreateCommand("create savings 12345678 0"));
	}

	@Test
	public void test_apr_between_zero_and_ten() {
		assertTrue(validator.validateCreateCommand("create savings 12345678 5.5"));
	}

	@Test
	public void test_apr_ten() {
		assertTrue(validator.validateCreateCommand("create savings 12345678 10"));
	}

	@Test
	public void test_apr_negative() {
		assertFalse(validator.validateCreateCommand("create savings 12345678 -1.0"));
	}

	@Test
	void test_apr_more_than_ten() {
		boolean isValid = CommandValidation.validateApr(15.0); // Adjust with the actual method for APR validation
		assertFalse(isValid, "APR over 10% should not be valid");
	}

	@Test
	public void test_cd_balance_1000() {
		assertTrue(validator.validateCreateCommand("create cd 12345678 1000"));
	}

	@Test
	public void test_cd_balance_between_1000_and_10000() {
		assertTrue(validator.validateCreateCommand("create cd 12345678 5000"));
	}

	@Test
	public void test_cd_balance_10000() {
		assertTrue(validator.validateCreateCommand("create cd 12345678 10000"));
	}

	@Test
	public void test_cd_balance_less_than_1000() {
		assertFalse(CommandValidation.validateCdBalance(500));
	}

	@Test
	public void test_cd_balance_more_than_10000() {
		assertFalse(CommandValidation.validateCdBalance(15000));
	}

	@Test
	public void test_cd_balance_negative() {
		assertFalse(validator.validateCreateCommand("create cd 12345678 -1000"));
	}

	@Test
	public void test_cd_balance_not_specified() {
		assertFalse(validator.validateCreateCommand("create cd 12345678"));
	}

	@Test
	public void test_savings_checking_balance_specified() {
		assertTrue(validator.validateCreateCommand("create savings 12345678 500"));
	}

	@Test
	public void test_account_id_not_unique() {
		assertTrue(validator.validateCreateCommand("create savings 12345678 1.5"));
		assertFalse(validator.validateCreateCommand("create savings 12345678 1.5"));
	}

	@Test
	public void test_account_id_not_eight_digits() {
		assertFalse(validator.validateCreateCommand("create savings 1234567 1.5"));
	}

	@Test
	public void test_account_id_not_digits() {
		assertFalse(validator.validateCreateCommand("create savings 1234ABCD 1.5"));
	}

	@Test
	public void test_account_type_invalid() {
		assertFalse(validator.validateCreateCommand("create premium 12345678 1.5"));
	}

	@Test
	public void test_command_name_not_create() {
		assertFalse(validator.validateCreateCommand("delete savings 12345678 1.5"));
	}

	@Test
	public void test_command_missing_all_parameters() {
		assertFalse(validator.validateCreateCommand(""));
	}

	@Test
	public void test_command_missing_id() {
		assertFalse(validator.validateCreateCommand("create savings 1.5"));
	}

	@Test
	public void test_command_missing_apr() {
		assertFalse(validator.validateCreateCommand("create savings 12345678"));
	}

	@Test
	public void test_command_missing_account_type() {
		assertFalse(validator.validateCreateCommand("create 12345678 1.5"));
	}

	@Test
	public void test_command_missing_create_word() {
		assertFalse(validator.validateCreateCommand("savings 12345678 1.5"));
	}

}