package com.hedspi.javalorant.dto;

import java.util.List;

public class SortProductsDTO {
    private String field;
    private String type;
    private List<Long> productIDList;

    public SortProductsDTO() {
    }

    public SortProductsDTO(String field, String type, List<Long> productIDList) {
        this.field = field;
        this.type = type;
        this.productIDList = productIDList;
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Long> getProductIDList() {
        return productIDList;
    }
    public void setProductIDList(List<Long> productIDList) {
        this.productIDList = productIDList;
    }

    @Override
    public String toString() {
        return "SortProductsDTO{" +
                "feild='" + field + '\'' +
                ", type='" + type + '\'' +
                ", productIDList=" + productIDList +
                '}';
    }
}
