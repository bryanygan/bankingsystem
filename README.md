# Banking System

This project is a simple banking system implemented in Java, created for my SE201 class in Drexel. It supports creating and managing different types of bank accounts, processing deposits, withdrawals, transfers, and simulating the passage of time with APR accrual and minimum balance fees. The system is designed for educational purposes and includes comprehensive unit tests.

Note: This program was never finshed completely, so some features may not work as intended. 

## Features

- **Account Types**: Checking, Savings, Certificate of Deposit (CD)
- **Account Operations**: Create, Deposit, Withdraw, Transfer, Pass (simulate months)
- **Validation**: Command and input validation for all operations
- **Transaction Logging**: Logs all transactions per account
- **Invalid Command Tracking**: Stores and outputs invalid commands
- **APR Accrual**: Monthly APR calculation for all accounts
- **Minimum Balance Fee**: Deducts fee for balances below threshold
- **Account Closure**: Automatically closes accounts with zero balance after time passes

## Project Structure

```
src/
  main/
    java/
      Account.java
      AccountNumberValidation.java
      AccountTypeValidation.java
      Bank.java
      CdAccountValidation.java
      CertificateOfDeposit.java
      Checking.java
      CommandParsing.java
      CommandProcessor.java
      CommandValidation.java
      CreateCommand.java
      DepositCommand.java
      DepositCommandValidation.java
      InvalidCommands.java
      MasterControl.java
      PassCommandProcessor.java
      PassCommandValidator.java
      Savings.java
      TransactionLogger.java
      TransferCommand.java
      TransferCommandProcessor.java
  test/
    java/
      (Unit tests for all major classes)
```

## How to Build

This project uses Gradle. To build:
```bash
./gradlew build
```

## How to Run

The main entry point is typically through the `MasterControl` class, which processes a list of commands. You can run the project using your IDE or by creating a main class that initializes `MasterControl` and feeds it commands.

## How to Test

Run all tests with:

```bash
./gradlew test
```

Unit tests are located in `test/java` and cover all major functionalities.

## Key Classes

- **Account**: Base class for all account types.
- **Bank**: Manages accounts and processes operations.
- **MasterControl**: Orchestrates command processing and output.
- **CommandProcessor**: Parses and executes commands.
- **TransactionLogger**: Logs transactions for accounts.
- **InvalidCommands**: Stores invalid commands for reporting.

## Example Commands

- ```create checking 12345678 1.0```
- ```deposit 12345678 500```
- ```withdraw 12345678 100```
- ```transfer 12345678 87654321 200```
- ```pass 1```

## License

This project is for educational purposes.




