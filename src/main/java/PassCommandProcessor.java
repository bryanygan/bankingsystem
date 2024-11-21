public class PassCommandProcessor {
	public int months;

	public void PassTimeCommand(int months) {
		if (months < 1 || months > 60) {
			throw new IllegalArgumentException("Months must be between 1 and 60.");
		}
		this.months = months;
	}

}
