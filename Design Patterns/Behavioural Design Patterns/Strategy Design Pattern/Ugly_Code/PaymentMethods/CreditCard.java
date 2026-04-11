package Ugly_Code.PaymentMethods;

import Ugly_Code.PaymentMethod;

public class CreditCard implements PaymentMethod {
    public void processPayment(){
        System.out.println("Credit Card Payment");
    }
}
