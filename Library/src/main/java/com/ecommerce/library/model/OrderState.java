package com.ecommerce.library.model;

public interface OrderState {
    public void confirm(Order order);
    public void cancel(Order order);
    public void ship(Order order);
}
