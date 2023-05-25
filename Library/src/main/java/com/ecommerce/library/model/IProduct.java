package com.ecommerce.library.model;

public interface IProduct {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    int getCurrentQuantity();
    void setCurrentQuantity(int currentQuantity);

    double getCostPrice();
    void setCostPrice(double costPrice);

    double getSalePrice();
    void setSalePrice(double salePrice);

    String getImage();
    void setImage(String image);

    Category getCategory();
    void setCategory(Category category);

    boolean isActivated();
    void setActivated(boolean activated);

    boolean isDeleted();
    void setDeleted(boolean deleted);
}

