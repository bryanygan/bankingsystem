import org.junit.jupiter.api.BeforeEach;

public class DepositCommandTest {

	private Bank bank;
	private DepositCommand depositCommand;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositCommand = new DepositCommand(bank);
		Checking checking = new Checking("12345678", 1.0);
		bank.addAccount(checking);
	}

}