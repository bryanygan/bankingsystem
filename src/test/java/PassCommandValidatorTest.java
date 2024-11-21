import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PassCommandValidatorTest {

	@Test
	void testInvalidMonths() {
		Bank bank = new Bank();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new PassCommandValidator(0).execute(bank);
		});
		assertEquals("Months must be between 1 and 60.", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new PassCommandValidator(61).execute(bank);
		});
		assertEquals("Months must be between 1 and 60.", exception.getMessage());
	}
}
