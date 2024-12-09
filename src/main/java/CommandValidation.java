
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
			System.out.println("Validating create command");
			System.out.println("Create Command Status: " + validateCreateCommand(command));
			return validateCreateCommand(command);
		case "deposit":
			System.out.println("Validating deposit command");
			System.out.println("Deposit Command Status: " + depositCommandValidation.validateDepositCommand(command));
			return depositCommandValidation.validateDepositCommand(command);
		case "withdraw":
			System.out.println("Validating withdraw command");
			System.out.println("Withdraw Command Status: " + withdrawCommandValidator.isValidCommand(command, bank));
			return withdrawCommandValidator.isValidCommand(command, bank);
		case "transfer":
			System.out.println("Validating transfer command");
			System.out.println("Transfer Command Status: " + validateTransferCommand(command));
			return validateTransferCommand(command);
		case "pass":
			System.out.println("Validating pass command");
			System.out.println("Pass Command Status: " + validatePassCommand(command));
			return validatePassCommand(command);
		default:
			return false;
		}
	}

	public boolean validateCreateCommand(String command) {
		String[] parts = CommandParsing.parseCommand(command);
		if (parts == null || parts.length < 3 || parts.length > 5 || !parts[0].equals("create")) {
			System.out.println("1st fail");
			return false;
		}

		String accountType = parts[1].toLowerCase();
		String accountNumber = parts[2];
		String additionalParam = parts.length > 3 ? parts[3] : null;

		if (Bank.getAccountByID(accountNumber) != null) {
			return false;
		}

		if (!AccountTypeValidation.isValidAccountType(accountType)) {
			System.out.println("2nd fail");
			return false;
		}

		if (accountNumberValidation.isValidAccountNumber(accountNumber)) {
			System.out.println("3rd fail 1");
			return false;
		}

		if (!accountType.equals("savings") && !accountType.equals("checking") && !accountType.equals("cd")) {
			System.out.println("4th fail");
			return false;
		}

		if (accountType.equals("savings") || accountType.equals("checking")) {
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !additionalParam.matches("\\d+(\\.\\d{1,2})?") || parts.length > 4) {
				System.out.println("5th fail");
				return false;
			}
		} else if (accountType.equals("cd")) {
			if (parts.length != 5) {
				System.out.println("6th fail");
				return false;
			}
			if (additionalParam == null || !isPositiveDecimal(additionalParam)
					|| !CdAccountValidation.validateApr(Double.parseDouble(additionalParam))) {
				System.out.println("7th fail");
				return false;
			}
			double cdBalance = Double.parseDouble(parts[4]);
			if (!CdAccountValidation.validateCdBalance(cdBalance)) {
				System.out.println("8th fail");
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
