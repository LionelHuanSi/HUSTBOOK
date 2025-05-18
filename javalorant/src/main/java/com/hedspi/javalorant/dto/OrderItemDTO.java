package com.hedspi.javalorant.dto;

import java.util.List;

import com.hedspi.javalorant.inventory.Product;

public class OrderItemDTO {
    private List<Product> product;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(List<Product> product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
