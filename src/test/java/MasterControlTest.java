public class MasterControlTest {

	MasterControl masterControl;

	void setUp() {
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidaton(bank), new CommandProcessor(bank),
				new InvalidCommands());
	}
}
