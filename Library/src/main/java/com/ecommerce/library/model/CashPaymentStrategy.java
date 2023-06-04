package com.ecommerce.library.model;

public class CashPaymentStrategy implements PaymentStrategy{
    @Override
    public void processPayment(double amount) {
        SingletonLogger.getInstance().logWarning("Payment made with cash");
    }
}
