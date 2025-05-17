package com.hedspi.javalorant.inventory;

public abstract class Product {
    public static long countProduct = 0;
    private long productID;
    private String name;
    private int quantity;
    private double purchasePrice;
    private double sellingPrice;

    public Product(String name, int quantity, double purchasePrice, double sellingPrice) {
        Product.countProduct++;
        this.productID = Product.countProduct;
        this.name = name;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Selling price cannot be negative");
        }
        this.sellingPrice = sellingPrice;
    }

    public double getUnitProfit() {
        return sellingPrice - purchasePrice;
    }

    public abstract String getProductType();
}
