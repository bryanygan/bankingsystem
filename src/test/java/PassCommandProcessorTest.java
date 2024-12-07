import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandProcessorTest {

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
}