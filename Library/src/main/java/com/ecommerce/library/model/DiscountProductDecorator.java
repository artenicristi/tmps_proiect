package com.ecommerce.library.model;

import java.util.ArrayList;
import java.util.List;

public class DiscountProductDecorator extends ProductDecorator{
    private final double discount;

    public DiscountProductDecorator(IProduct decoratedProduct, double discount) {
        super(decoratedProduct);
        this.discount = discount;
    }

    public double getDiscountedPrice() {
        return super.getSalePrice() * discount;
    }
}
