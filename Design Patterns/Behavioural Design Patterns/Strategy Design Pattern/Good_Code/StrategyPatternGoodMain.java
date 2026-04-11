package Good_Code;

import Good_Code.PaymentMethods.CreditCard;
import Good_Code.PaymentMethods.Crypto;
import Good_Code.PaymentMethods.DebitCard;
import Good_Code.PaymentMethods.PayPal;

public class StrategyPatternGoodMain {
    public static void main(String[] args) {
        PaymentMethod creditCard = new CreditCard();
        PaymentMethod debitCard = new DebitCard();
        PaymentMethod crypto = new Crypto();
        PaymentMethod payPal = new PayPal();

        PaymentProcessor paymentProcessor = new PaymentProcessor(creditCard);
        paymentProcessor.processPayment();

        //Dynamically changing payment method to debit card
        paymentProcessor.setPaymentMethod(debitCard);
        paymentProcessor.processPayment();

        paymentProcessor.setPaymentMethod(payPal);
        paymentProcessor.processPayment();

        paymentProcessor.setPaymentMethod(crypto);
        paymentProcessor.processPayment();
    }
}
