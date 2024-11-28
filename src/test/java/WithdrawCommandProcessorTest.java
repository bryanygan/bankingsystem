import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {

	@Test
	public void test_withdraw_from_checking() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(500);
		Bank.addAccount(checking);
		WithdrawCommandProcessor processor = new WithdrawCommandProcessor();

		String command = "withdraw 12345678 300";
		processor.processCommand(command, bank);

		assertEquals(200, Bank.getAccountByID("12345678").getBalance());
	}

	@Test
	public void test_withdraw_more_than_balance() {
		Bank bank = new Bank();
		Checking checking = new Checking("12345678", 3.5);
		checking.deposit(300);
		Bank.addAccount(checking);
		WithdrawCommandProcessor processor = new WithdrawCommandProcessor();

		String command = "withdraw 12345678 400";
		processor.processCommand(command, bank);

		assertEquals(0, Bank.getAccountByID("12345678").getBalance());
	}

}
