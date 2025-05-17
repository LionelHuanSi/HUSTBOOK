package com.hedspi.javalorant.dto;

public class ProductDTO {
    private String name;
    private int quantity;
    private double purchasePrice;
    private double sellingPrice;
    private String productType;
    private String publisher;
    private String author;
    private String ISBN;
    private String brand;
    private String stationaryType;
    private int suitableAge;    

    public ProductDTO() {
    }

    // Thêm constructor đầy đủ nếu cần
    public ProductDTO(String name, int quantity, double purchasePrice, double sellingPrice,
            String productType, String publisher, String author, String ISBN,
            String brand, String stationaryType, int suitableAge) {
        this.name = name;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.productType = productType;
        this.publisher = publisher;
        this.author = author;
        this.ISBN = ISBN;
        this.brand = brand;
        this.stationaryType = stationaryType;
        this.suitableAge = suitableAge;
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
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public int getSuitableAge() {
        return suitableAge;
    }

    public void setSuitableAge(int suitableAge) {
        this.suitableAge = suitableAge;
    }
}
