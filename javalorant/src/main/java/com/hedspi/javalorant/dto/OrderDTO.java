package com.hedspi.javalorant.dto;

import java.util.List;
import java.util.Map;

import com.hedspi.javalorant.order.CustomerInfor;
import com.hedspi.javalorant.order.PaymentMethod;


public class OrderDTO {
    private String orderDate;
    private List<Map<String, Object>> items;
    private double totalAmount;
    private boolean paid;
    private CustomerInfor customerInfo;
    private String fullName; // employee name
    private PaymentMethod paymentMethod;

    public OrderDTO() {
    }

    public OrderDTO(String orderDate, List<Map<String, Object>> items, double totalAmount, boolean paid,
            CustomerInfor customerInfo, String fullName, PaymentMethod paymentMethod) {
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paid = paid;
        this.customerInfo = customerInfo;
        this.fullName = fullName;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public CustomerInfor getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfor customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
       return "OrderDTO{" +
                "orderDate='" + orderDate + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", paid=" + paid +
                ", customerInfo=" + customerInfo +
                ", fullName='" + fullName + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
