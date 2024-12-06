public class TransferCommand {
	private final String fromId;
	private final String toId;
	private final double amount;
	private final String rawCommand;

	public TransferCommand(String fromId, String toId, double amount, String rawCommand) {
		this.fromId = fromId;
		this.toId = toId;
		this.amount = amount;
		this.rawCommand = rawCommand;
	}

	public String getFromId() {
		return fromId;
	}

	public String getToId() {
		return toId;
	}

	public double getAmount() {
		return amount;
	}

	public String getRawCommand() {
		return rawCommand;
	}
}