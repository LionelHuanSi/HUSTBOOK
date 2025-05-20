package com.hedspi.javalorant.dto;

import java.util.List;

public class SortEmployeeDTO {
    private String field;
    private String type;
    private List<Long> userIDList;

    public SortEmployeeDTO() {
    }

    public SortEmployeeDTO(String field, String type, List<Long> userIDList) {
        this.field = field;
        this.type = type;
        this.userIDList = userIDList;
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

    public List<Long> getUserIDList() {
        return userIDList;
    }

    public void setUserIDList(List<Long> userIDList) {
        this.userIDList = userIDList;
    }
}
