package com.hedspi.javalorant.dto;

import java.util.List;

public class SortOrderDTO {
    private String field;
    private String type;
    private List<Long> orderIDList;

    public SortOrderDTO() {
    }

    public SortOrderDTO(String field, String type, List<Long> orderIDList) {
        this.field = field;
        this.type = type;
        this.orderIDList = orderIDList;
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

    public List<Long> getOrderIDList() {
        return orderIDList;
    }

    public void setOrderIDList(List<Long> orderIDList) {
        this.orderIDList = orderIDList;
    }
}
