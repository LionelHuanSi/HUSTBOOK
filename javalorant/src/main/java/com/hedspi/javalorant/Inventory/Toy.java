package com.hedspi.javalorant.inventory;

public class Toy extends Product {
    private String brand;
    private int suitableAge;

    public Toy(String name, int quantity, double purchasePrice,
              double sellingPrice, String brand, int suitableAge) {
        super(name, quantity, purchasePrice, sellingPrice);
        this.brand = brand;
        this.suitableAge = suitableAge;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSuitableAge() {
        return suitableAge;
    }

    public void setSuitableAge(int suitableAge) {
        this.suitableAge = suitableAge;
    }

    @Override
    public String getProductType() {
        return "Toy";
    }
}
