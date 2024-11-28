import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
	@Test
	public void test_invalid_account_id() {
		WithdrawCommandValidator validator = new WithdrawCommandValidator();
		String command = "withdraw abcdefgh 100";
		Bank bank = new Bank();

		boolean isValid = validator.isValidCommand(command, bank);

		assertFalse(isValid);
	}

	@Test
	public void test_invalid_withdrawal_amount() {
		WithdrawCommandValidator validator = new WithdrawCommandValidator();
		String command = "withdraw 12345678 -100";
		Bank bank = new Bank();

		boolean isValid = validator.isValidCommand(command, bank);

		assertFalse(isValid);
	}
}
