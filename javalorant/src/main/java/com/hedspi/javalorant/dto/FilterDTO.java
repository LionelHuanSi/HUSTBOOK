package com.hedspi.javalorant.dto;

public class FilterDTO {
    private String category;
    private String name;
    private double purchasePriceFrom;
    private double purchasePriceTo;
    private double sellingPriceFrom;
    private double sellingPriceTo;
    
    public FilterDTO() {
    }

    public FilterDTO(String category, String name, double purchasePriceFrom, double purchasePriceTo, double sellingPriceFrom, double sellingPriceTo) {
        this.category = category;
        this.name = name;
        this.purchasePriceFrom = purchasePriceFrom;
        this.purchasePriceTo = purchasePriceTo;
        this.sellingPriceFrom = sellingPriceFrom;
        this.sellingPriceTo = sellingPriceTo;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPurchasePriceFrom() {
        return purchasePriceFrom;
    }
    public void setPurchasePriceFrom(double purchasePriceFrom) {
        this.purchasePriceFrom = purchasePriceFrom;
    }
    public double getPurchasePriceTo() {
        return purchasePriceTo;
    }
    public void setPurchasePriceTo(double purchasePriceTo) {
        this.purchasePriceTo = purchasePriceTo;
    }
    public double getSellingPriceFrom() {
        return sellingPriceFrom;
    }
    public void setSellingPriceFrom(double sellingPriceFrom) {
        this.sellingPriceFrom = sellingPriceFrom;
    }
    public double getSellingPriceTo() {
        return sellingPriceTo;
    }
    public void setSellingPriceTo(double sellingPriceTo) {
        this.sellingPriceTo = sellingPriceTo;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", purchasePriceFrom=" + purchasePriceFrom +
                ", purchasePriceTo=" + purchasePriceTo +
                ", sellingPriceFrom=" + sellingPriceFrom +
                ", sellingPriceTo=" + sellingPriceTo +
                '}';
    }
}
