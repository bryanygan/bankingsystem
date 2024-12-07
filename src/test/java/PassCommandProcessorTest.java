import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandProcessorTest {
	private PassCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		processor = new PassCommandProcessor(bank);
	}

	@Test
	public void test_process_closes_zero_balance_accounts() {
		Account account = new Account("12345678", 1.0);
		Bank.addAccount(account);

		processor.process("pass 1");

		assertNull(Bank.getAccountByID("12345678"));
	}

	@Test
	public void test_process_deducts_minimum_balance_fee() {
		Account account = new Account("12345678", 1.0);
		account.setBalance(50);
		Bank.addAccount(account);

		processor.process("pass 1");

		assertEquals(25.02, Bank.getAccountByID("12345678").getBalance(), 0.01);
	}

	@Test
	public void test_process_accrues_monthly_apr() {
		Account account = new Account("12345678", 1.2);
		account.setBalance(1000);
		Bank.addAccount(account);

		processor.process("pass 1");

		assertEquals(1001.00, Bank.getAccountByID("12345678").getBalance(), 0.01);
	}

	@Test
	public void test_process_multiple_months() {
		Account account = new Account("12345678", 1.2);
		account.setBalance(1000);
		Bank.addAccount(account);

		processor.process("pass 2");

		assertEquals(1002.00, Bank.getAccountByID("12345678").getBalance(), 0.01);
	}

	@Test
	public void test_process_invalid_commands_format() {
		Account account = new Account("12345678", 1.0);
		account.setBalance(500);
		Bank.addAccount(account);

		processor.process("pas 1");
		processor.process("pass -1");
		processor.process("pass 0");
		processor.process("pass 61");
		processor.process("pass");

		assertEquals(500.0, Bank.getAccountByID("12345678").getBalance(), 0.01);
	}
}