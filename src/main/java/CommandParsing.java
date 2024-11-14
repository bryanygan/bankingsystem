
public class CommandParsing {

    public static String[] parseCommand(String command) {
        if (command == null || command.isEmpty() || !command.equals(command.trim())) {
            return null;
        }
        return command.trim().split("\\s+");
    }
}
