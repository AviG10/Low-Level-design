package Ugly_Code;

import java.util.Scanner;

public class StrategyPatternUglyMain {
    public static void main(String[] args) {
        System.out.print("Enter Payment Method: ");
        Scanner input = new Scanner(System.in);
        String paymentMethod = input.next();

        PaymentProcessor paymentProcessor = new PaymentProcessor();
        paymentProcessor.processPayment(paymentMethod);
    }
}
