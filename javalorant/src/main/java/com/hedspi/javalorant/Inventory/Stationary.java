package com.hedspi.javalorant.inventory;

public class Stationary extends Product {
    private String brand;
    private String stationaryType;

    public Stationary(String name, int quantity, double purchasePrice, 
                     double sellingPrice, String brand, String stationaryType) {
        super(name, quantity, purchasePrice, sellingPrice);
        this.brand = brand;
        this.stationaryType = stationaryType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStationaryType() {
        return stationaryType;
    }

    public void setStationaryType(String stationaryType) {
        this.stationaryType = stationaryType;
    }

    @Override
    public String getProductType() {
        return "Stationary";
    }
    @Override
    public String toString() {
        return "Stationary{" +
                "productID=" + getProductID() +
                ", name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", purchasePrice=" + getPurchasePrice() +
                ", sellingPrice=" + getSellingPrice() +
                ", brand='" + brand + '\'' +
                ", stationaryType='" + stationaryType + '\'' +
                '}';
    }
}
