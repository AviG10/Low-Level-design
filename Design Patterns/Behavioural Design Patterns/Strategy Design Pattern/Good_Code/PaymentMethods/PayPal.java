package Good_Code.PaymentMethods;

import Good_Code.PaymentMethod;

public class PayPal implements PaymentMethod {
    public void processPayment(){
        System.out.println("PayPal Payment");
    }
}
