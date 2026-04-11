package Good_Code.PaymentMethods;

import Good_Code.PaymentMethod;

public class CreditCard implements PaymentMethod {
    public void processPayment(){
        System.out.println("Credit Card Payment");
    }
}
