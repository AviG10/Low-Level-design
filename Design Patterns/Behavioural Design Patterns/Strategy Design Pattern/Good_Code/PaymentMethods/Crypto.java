package Good_Code.PaymentMethods;

import Good_Code.PaymentMethod;

public class Crypto implements PaymentMethod {
    public void processPayment() {
        System.out.println("Crypto Payment");
    }
}
