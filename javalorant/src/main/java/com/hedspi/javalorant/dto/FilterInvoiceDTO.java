package com.hedspi.javalorant.dto;

public class FilterInvoiceDTO {
    private String customerName;
    private String employeeName;
    private String startDate;
    private String endDate;
    private String paymentMethod;

    public FilterInvoiceDTO() {
    }

    public FilterInvoiceDTO(String customerName, String employeeName, String startDate, String endDate,
            String paymentMethod) {
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "FilterInvoiceDTO{" +
                "customerName='" + customerName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
