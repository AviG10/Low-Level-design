package Ugly_Code.PaymentMethods;

import Ugly_Code.PaymentMethod;

public class Crypto implements PaymentMethod {
    public void processPayment() {
        System.out.println("Crypto Payment");
    }
}
