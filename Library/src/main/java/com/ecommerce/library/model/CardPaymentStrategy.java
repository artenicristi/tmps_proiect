package com.ecommerce.library.model;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        SingletonLogger.getInstance().logWarning("Payment made with card");
    }
}