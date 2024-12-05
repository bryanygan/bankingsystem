public class TransferCommand {
	private String fromId;
	private String toId;
	private double amount;
	private String rawCommand;

	public TransferCommand(String rawCommand) {
		this.rawCommand = rawCommand;
	}

	public boolean validate(Bank bank) {
		return false;
	}

	public void execute(Bank bank) {
	}
}