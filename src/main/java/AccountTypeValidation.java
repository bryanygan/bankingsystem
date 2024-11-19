
public class AccountTypeValidation {

    public static boolean isValidAccountType(String accountType) {
        return accountType.equals("savings") || accountType.equals("checking") || accountType.equals("cd");
    }
}
