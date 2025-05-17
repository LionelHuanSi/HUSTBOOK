package com.hedspi.javalorant.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Product> productList;

    public Inventory() {
        this.productList = new ArrayList<>();
    }

    public Product getProductByID(long productID) {
        for (Product product : productList) {
            if (product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }

    public Product getProductByName(String name) {
        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public boolean removeProduct(long productID) {
        return productList.removeIf(product -> product.getProductID() == productID);
    }

    public boolean updateProduct(Product updatedProduct) {
        for (Product product : productList) {
            if (product.getProductID() == updatedProduct.getProductID()) {
                product.setName(updatedProduct.getName());
                product.setPurchasePrice(updatedProduct.getPurchasePrice());
                product.setSellingPrice(updatedProduct.getSellingPrice());
                product.setQuantity(updatedProduct.getQuantity());
                product.setUnitSold(updatedProduct.getUnitSold());
                switch (product) {
                    case Book book -> {
                        book.setPublisher(((Book) updatedProduct).getPublisher());
                        book.setAuthor(((Book) updatedProduct).getAuthor());
                        book.setISBN(((Book) updatedProduct).getISBN());
                    }
                    case Stationary stationary -> {
                        stationary.setBrand(((Stationary) updatedProduct).getBrand());
                        stationary.setStationaryType(((Stationary) updatedProduct).getStationaryType());
                    }
                    case Toy toy -> toy.setSuitableAge(((Toy) updatedProduct).getSuitableAge());
                    default -> {
                    }
                }
                return true;
            }
        }
        return false;
    }    

    public double getTotalInventoryValue() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPurchasePrice() * product.getQuantity();
        }
        return total;
    }
}
