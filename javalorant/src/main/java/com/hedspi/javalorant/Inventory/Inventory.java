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
                    case Toy toy -> {
                        toy.setBrand(((Toy) updatedProduct).getBrand());
                        toy.setSuitableAge(((Toy) updatedProduct).getSuitableAge());
                    }
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

    public List<Product> filterProductsByCategory(String category, List<Product> productList) {
        return productList.stream()
            .filter(product -> product.getProductType().equalsIgnoreCase(category))
            .toList();
    }

    public List<Product> filterProductsByName(String name, List<Product> productList) {
        return productList.stream()
            .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
            .toList();
    }

    public List<Product> filterProductsByPurchasePrice(double minPurchasePrice, double maxPurchasePrice, List<Product> productList) {
        return productList.stream()
            .filter(product -> product.getPurchasePrice() >= minPurchasePrice && product.getPurchasePrice() <= maxPurchasePrice)
            .toList();
    }

    public List<Product> filterProductsBySellingPrice(double minSellingPrice, double maxSellingPrice, List<Product> productList) {
        return productList.stream()
            .filter(product -> product.getSellingPrice() >= minSellingPrice && product.getSellingPrice() <= maxSellingPrice)
            .toList();
    }

    public List<Product> sortProductsByID(List<Product> productList, String type) {
        return productList.stream()
            .sorted((product1, product2) -> {
            if (type.equalsIgnoreCase("up")) {
                return Long.compare(product1.getProductID(), product2.getProductID());
            } else {
                return Long.compare(product2.getProductID(), product1.getProductID());
            }
            })
            .toList();
    }

    public List<Product> sortProductsByPurchasePrice(List<Product> productList, String type) {
        return productList.stream()
            .sorted((product1, product2) -> {
            if (type.equalsIgnoreCase("up")) {
                return Double.compare(product1.getPurchasePrice(), product2.getPurchasePrice());
            } else {
                return Double.compare(product2.getPurchasePrice(), product1.getPurchasePrice());
            }
            })
            .toList();
    }

    public List<Product> sortProductsBySellingPrice(List<Product> productList, String type) {
        return productList.stream()
            .sorted((product1, product2) -> {
            if (type.equalsIgnoreCase("up")) {
                return Double.compare(product1.getSellingPrice(), product2.getSellingPrice());
            } else {
                return Double.compare(product2.getSellingPrice(), product1.getSellingPrice());
            }
            })
            .toList();
    }

    public List<Product> sortProductsByQuantity(List<Product> productList, String type) {
        return productList.stream()
            .sorted((product1, product2) -> {
            if (type.equalsIgnoreCase("up")) {
                return Integer.compare(product1.getQuantity(), product2.getQuantity());
            } else {
                return Integer.compare(product2.getQuantity(), product1.getQuantity());
            }
            })
            .toList();
    }
}
