public class PassCommandValidator {
	public boolean validate(String command) {
		if (!command.toLowerCase().startsWith("pass")) {
			return false;
		}
		String[] parts = command.split(" ");
		if (parts.length != 2) {
			return false;
		}
		try {
			int months = Integer.parseInt(parts[1]);
			return months >= 1 && months <= 60;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}