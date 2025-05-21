package com.hedspi.javalorant.dto;

public class FilterFinanceDTO {
    private String startDate;
    private String endDate;

    public FilterFinanceDTO() {
    }

    public FilterFinanceDTO(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return "FilterFinanceDTO{" + "startDate=" + startDate + ", endDate=" + endDate + '}';
    }
}
