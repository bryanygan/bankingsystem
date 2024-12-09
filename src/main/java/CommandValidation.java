
public class CommandValidation {
	private final Bank bank;
	private final InvalidCommands invalidCommands;
	private final CreateCommand createCommand;
	private final DepositCommandValidation depositCommandValidation;
	private final TransferCommandValidator transferCommandValidator;
	private final WithdrawCommandValidator withdrawCommandValidator;
	private final AccountNumberValidation accountNumberValidation;

	public CommandValidation(Bank bank, InvalidCommands invalidCommands) {
		this.bank = bank;
		this.invalidCommands = invalidCommands;

		this.createCommand = new CreateCommand(bank, invalidCommands);
		this.depositCommandValidation = new DepositCommandValidation();
		this.transferCommandValidator = new TransferCommandValidator();
		this.withdrawCommandValidator = new WithdrawCommandValidator();
		this.accountNumberValidation = new AccountNumberValidation();
	}

	public boolean validateCommand(String command) {
		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length < 2) {
			return false;
		}

		String commandType = parts[0].toLowerCase();
		switch (commandType) {
		case "create":
			return validateCreateCommand(command);
		case "deposit":
			return depositCommandValidation.validateDepositCommand(command);
		case "withdraw":
			return withdrawCommandValidator.isValidCommand(command, bank);
		case "transfer":
			return validateTransferCommand(command);
		case "pass":
			return validatePassCommand(command);
		default:
			return false;
		}
	}

	public boolean validateCreateCommand(String command) {
		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length < 3 || parts.length > 5 || !parts[0].equals("create")) {
			return false;
		}

		String accountType = parts[1].toLowerCase();
		String accountNumber = parts[2];
		String additionalParam = parts.length > 3 ? parts[3] : null;

		if (Bank.getAccountByID(accountNumber) != null) {
			return false;
		}

		if (!AccountTypeValidation.isValidAccountType(accountType)) {
			return false;
		}

		if (accountNumberValidation.isValidAccountNumber(accountNumber)) {
			return false;
		}

		if (!accountType.equals("savings") && !accountType.equals("checking") && !accountType.equals("cd")) {
			return false;
		}

		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !additionalParam.matches("\\d+(\\.\\d{1,2})?") || parts.length > 4) {
				return false;
			}
		} else if (accountType.equals("cd")) {
			if (parts.length != 5) {
				return false;
			}
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !CdAccountValidation.validateApr(Double.parseDouble(additionalParam))) {
				return false;
			}
			double cdBalance = Double.parseDouble(parts[4]);
			if (!CdAccountValidation.validateCdBalance(cdBalance)) {
				return false;
			}
		}

		accountNumberValidation.registerAccountId(accountNumber);
		return true;
	}

	private boolean validateTransferCommand(String command) {
		try {
			String[] parts = command.trim().split("\\s+");
			if (parts.length != 4) {
				return false;
			}

			String fromId = parts[1];
			String toId = parts[2];
			double amount = Double.parseDouble(parts[3]);
			TransferCommand transferCommand = new TransferCommand(fromId, toId, amount, command);

			return transferCommandValidator.validate(transferCommand, bank);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean validatePassCommand(String command) {
		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length != 2 || !parts[0].equalsIgnoreCase("pass")) {
			return false;
		}

		try {
			int months = Integer.parseInt(parts[1]);
			return months >= 1 && months <= 60;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isPositiveDecimal(String str) {
		try {
			double value = Double.parseDouble(str);
			return value > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
