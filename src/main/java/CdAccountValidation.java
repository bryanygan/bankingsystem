
public class CdAccountValidation {

    public static boolean validateCdBalance(double balance) {
        return balance >= 1000 && balance <= 10000;
    }

    public static boolean validateApr(double apr) {
        return apr >= 0 && apr <= 10 && (Math.floor(apr * 100) == apr * 100);
    }
}
