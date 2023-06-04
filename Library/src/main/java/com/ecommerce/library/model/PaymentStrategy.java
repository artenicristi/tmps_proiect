package com.ecommerce.library.model;

public interface PaymentStrategy {
    void processPayment(double amount);
}
