public class PassCommandValidator {
    public int months;

    public PassCommandValidator(int months) {
        if (months < 1 || months > 60) {
            throw new IllegalArgumentException("Months must be between 1 and 60.");
        }
        this.months = months;
    }

    public void execute(Bank bank) {
    }
}
