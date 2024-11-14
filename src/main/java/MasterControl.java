public class MasterControl {
	private CommandValidaton commandValidaton;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;

	public MasterControl(CommandValidaton commandValidaton, CommandProcessor commandProcessor,
			InvalidCommands invalidCommands) {
		this.commandValidaton = this.commandValidaton;
		this.commandProcessor = commandProcessor;
		this.invalidCommands = invalidCommands;
	}
}
