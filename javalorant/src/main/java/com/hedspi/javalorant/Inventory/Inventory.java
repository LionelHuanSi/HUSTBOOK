package com.hedspi.javalorant.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Product> productList;

    public Inventory() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public boolean removeProduct(String productID) {
        return productList.removeIf(product -> product.getProductID().equals(productID));
    }

    public boolean updateQuantity(String productID, int newQuantity) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                product.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }

    public Product getProductByID(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }

    public double getTotalInventoryValue() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPurchasePrice() * product.getQuantity();
        }
        return total;
    }
}
