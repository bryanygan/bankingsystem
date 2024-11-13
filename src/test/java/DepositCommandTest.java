import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandTest {
	private Bank bank;
	private DepositCommand depositCommand;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositCommand = new DepositCommand(bank);
		Checking account = new Checking("12345678", 1.0);
		bank.addAccount(account);
	}

	@Test
	public void test_deposit_to_new_account() {
		String[] command = { "deposit", "12345678", "100" };
		depositCommand.execute(command);
		Account account = bank.getAccountByID("12345678");

		assertEquals(100, account.getBalance());
	}

	@Test
	public void test_deposit_negative_amount() {
		String[] command = { "deposit", "12345678", "-50" };
		Account account = bank.getAccountByID("12345678");
		double initialBalance = account.getBalance();
		depositCommand.execute(command);

		assertEquals(account.getBalance(), initialBalance);
	}

	@Test
	public void test_deposit_to_non_existent_account() {
		String accountId = "99999999";
		String[] command = { "deposit", accountId, "50" };
		assertNull(bank.getAccountByID(accountId));
		depositCommand.execute(command);

		assertNull(bank.getAccountByID(accountId), "Account should still not exist after deposit attempt.");
	}

	@Test
	public void test_deposit_to_existing_account() {
		String[] command1 = { "deposit", "12345678", "50" };
		String[] command2 = { "deposit", "12345678", "100" };
		depositCommand.execute(command1);
		depositCommand.execute(command2);
		Account account = bank.getAccountByID("12345678");

		assertEquals(150, account.getBalance());
	}
}