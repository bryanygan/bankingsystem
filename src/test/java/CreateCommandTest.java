import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandTest {
	private Bank bank;
	private CreateCommand createCommand;
	private InvalidCommands invalidCommands;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createCommand = new CreateCommand(bank, invalidCommands);
	}

	@Test
	public void create_checking_account() {
		String[] command = { "create", "checking", "12345678", "1.0" };
		createCommand.execute(command);
		Checking account = (Checking) Bank.getAccountByID("12345678");

		assertEquals("12345678", account.getAccountID());
		assertEquals(1.0, account.getAPR());
	}

	@Test
	public void create_savings_account() {
		String[] command = { "create", "savings", "87654321", "0.5" };
		createCommand.execute(command);
		Savings account = (Savings) Bank.getAccountByID("87654321");

		assertEquals("87654321", account.getAccountID());
		assertEquals(0.5, account.getAPR());
	}

	@Test
	public void create_cd_account() {
		String[] command = { "create", "cd", "11223344", "2.0", "1000.0" };
		createCommand.execute(command);
		CertificateOfDeposit account = (CertificateOfDeposit) Bank.getAccountByID("11223344");

		assertEquals("11223344", account.getAccountID());
		assertEquals(2.0, account.getAPR());
		assertEquals(1000.0, account.getBalance());
	}
}