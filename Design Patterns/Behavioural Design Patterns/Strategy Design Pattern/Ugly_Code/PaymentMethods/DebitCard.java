package Ugly_Code.PaymentMethods;

import Ugly_Code.PaymentMethod;

public class DebitCard implements PaymentMethod {
    public void processPayment() {
        System.out.println("DebitCard Payment");
    }
}
