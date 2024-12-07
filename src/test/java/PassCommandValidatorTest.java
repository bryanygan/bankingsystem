import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandValidatorTest {

	private PassCommandValidator validator;

	@BeforeEach
	public void setUp() {
		validator = new PassCommandValidator();
	}

	@Test
	public void test_invalid_command_without_pass_word() {
		assertFalse(validator.validate("hello"));
		assertFalse(validator.validate(" "));
		assertFalse(validator.validate("passing"));
	}

	@Test
	public void test_valid_pass_command_within_range() {
		assertTrue(validator.validate("pass 12"));
		assertTrue(validator.validate("pass 1"));
		assertTrue(validator.validate("pass 60"));
	}

	@Test
	public void test_invalid_pass_command_with_invalid_months() {
		assertFalse(validator.validate("pass 0"));
		assertFalse(validator.validate("pass 61"));
	}

	@Test
	public void test_invalid_pass_command_with_non_numeric() {
		assertFalse(validator.validate("pass one"));
		assertFalse(validator.validate("pass 12.5"));
		assertFalse(validator.validate("pass 5 extra"));
	}

	@Test
	public void test_valid_pass_command_edge_cases() {
		assertTrue(validator.validate("pass 1"));
		assertTrue(validator.validate("pass 60"));
	}
}
