import java.util.ArrayList;
import java.util.List;

public class InvalidCommands {
	private List<String> invalidCommands;

	public InvalidCommands() {
		this.invalidCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}
}