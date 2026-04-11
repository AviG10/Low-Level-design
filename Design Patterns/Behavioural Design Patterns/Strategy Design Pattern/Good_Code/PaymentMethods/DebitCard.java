package Good_Code.PaymentMethods;

import Good_Code.PaymentMethod;

public class DebitCard implements PaymentMethod {
    public void processPayment() {
        System.out.println("DebitCard Payment");
    }
}
