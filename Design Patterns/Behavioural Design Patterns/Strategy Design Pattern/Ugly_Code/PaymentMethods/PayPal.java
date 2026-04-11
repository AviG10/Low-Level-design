package Ugly_Code.PaymentMethods;

import Ugly_Code.PaymentMethod;

public class PayPal implements PaymentMethod {
    public void processPayment(){
        System.out.println("PayPal Payment");
    }
}
