package com.ecommerce.library.model;

public class CanceledState implements OrderState {
    @Override
    public void confirm(Order order) {

    }

    @Override
    public void cancel(Order order) {

    }

    @Override
    public void ship(Order order) {

    }
}
