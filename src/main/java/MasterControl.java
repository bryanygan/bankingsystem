import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private Bank bank;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;

	public MasterControl(Bank bank, CommandProcessor commandProcessor, InvalidCommands invalidCommands) {
		this.bank = bank;
		this.commandProcessor = commandProcessor;
		this.invalidCommands = invalidCommands;
	}

	public List<String> start(List<String> commands) {
		return new ArrayList<>();
	}
}