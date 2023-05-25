package com.ecommerce.library.model;

public class ProductDecorator implements IProduct{

    private IProduct decoratedProduct;

    public ProductDecorator(IProduct decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public Long getId() {
        return this.decoratedProduct.getId();
    }

    @Override
    public String getName() {
        return this.decoratedProduct.getName();
    }

    @Override
    public void setName(String name) {
        this.decoratedProduct.setName(name);
    }

    @Override
    public String getDescription() {
        return this.decoratedProduct.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.decoratedProduct.setDescription(description);
    }

    @Override
    public int getCurrentQuantity() {
        return this.decoratedProduct.getCurrentQuantity();
    }

    @Override
    public void setCurrentQuantity(int currentQuantity) {
        this.decoratedProduct.setCurrentQuantity(currentQuantity);
    }

    @Override
    public double getCostPrice() {
        return this.decoratedProduct.getCostPrice();
    }

    @Override
    public void setCostPrice(double costPrice) {
        this.decoratedProduct.setCostPrice(costPrice);
    }

    @Override
    public double getSalePrice() {
        return this.decoratedProduct.getSalePrice();
    }

    @Override
    public void setSalePrice(double salePrice) {
        this.decoratedProduct.setSalePrice(salePrice);
    }

    @Override
    public String getImage() {
        return this.decoratedProduct.getImage();
    }

    @Override
    public void setImage(String image) {
        this.decoratedProduct.setImage(image);
    }
    @Override
    public Category getCategory() {
        return this.decoratedProduct.getCategory();
    }

    @Override
    public void setCategory(Category category) {
        this.decoratedProduct.setCategory(category);
    }

    @Override
    public boolean isActivated() {
        return this.decoratedProduct.isActivated();
    }

    @Override
    public void setActivated(boolean activated) {
        this.decoratedProduct.setActivated(activated);
    }

    @Override
    public boolean isDeleted() {
        return this.decoratedProduct.isDeleted();
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.decoratedProduct.setDeleted(deleted);
    }
}
