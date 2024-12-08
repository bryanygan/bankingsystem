import java.util.ArrayList;
import java.util.List;

public class InvalidCommands {
	private final List<String> invalidCommands = new ArrayList<>();

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}
}