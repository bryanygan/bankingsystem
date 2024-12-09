import java.util.HashSet;
import java.util.Set;

public class AccountNumberValidation {

	private Set<String> existingAccountIds = new HashSet<>();

	public boolean isValidAccountNumber(String accountNumber) {
		return !accountNumber.matches("\\d{8}");
	}

	public boolean isUniqueAccountId(String accountId) {
		return !existingAccountIds.contains(accountId);
	}

	public void registerAccountId(String accountId) {
		existingAccountIds.add(accountId);
	}
}
