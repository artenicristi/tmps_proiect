package com.ecommerce.library.model;

public class PendingState implements OrderState {
    @Override
    public void confirm(Order order) {
        //logic
        order.setOrderState(new ConfirmedState());
    }

    @Override
    public void cancel(Order order) {
        // logic
        order.setOrderState(new CanceledState());
    }

    @Override
    public void ship(Order order) {
        // logic
        order.setOrderState(new ShippedState());
    }
}
