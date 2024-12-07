public class PassCommandValidator {
	public boolean validate(String command) {
		if (!command.toLowerCase().startsWith("pass")) {
			return false;
		}
		return false;
	}
}
