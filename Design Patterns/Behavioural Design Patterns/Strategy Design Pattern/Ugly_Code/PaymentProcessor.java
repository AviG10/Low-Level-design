package Ugly_Code;

import Ugly_Code.PaymentMethods.CreditCard;
import Ugly_Code.PaymentMethods.Crypto;
import Ugly_Code.PaymentMethods.DebitCard;
import Ugly_Code.PaymentMethods.PayPal;

public class PaymentProcessor {
    public void processPayment(String paymentMethod){
        if(paymentMethod.equalsIgnoreCase("CreditCard")){
            CreditCard creditCard = new CreditCard();
            creditCard.processPayment();
        }
        else if(paymentMethod.equalsIgnoreCase("DebitCard")){
            DebitCard debitCard = new DebitCard();
            debitCard.processPayment();
        }
        else if(paymentMethod.equalsIgnoreCase("PayPal")){
            PayPal payPal = new PayPal();
            payPal.processPayment();
        }
        else if(paymentMethod.equalsIgnoreCase("Crypto")){
            Crypto crypto = new Crypto();
            crypto.processPayment();
        }
        else{
            throw new IllegalArgumentException("Invalid Payment Method");
        }
    }
}
