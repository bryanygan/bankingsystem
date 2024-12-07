public class PassCommandProcessor {
	private final Bank bank;

	public PassCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		if (!command.toLowerCase().startsWith("pass ")) {
			return;
		}
	}
}