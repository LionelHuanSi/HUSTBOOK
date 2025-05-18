package com.hedspi.javalorant.dto;

import java.util.List;

import com.hedspi.javalorant.order.CustomerInfor;
import com.hedspi.javalorant.order.OrderItem;

public class OrderDTO {
    private String orderDate;
    private List<OrderItem> items;
    private double totalAmount;
    private boolean isPaid;
    private CustomerInfor customerInfo;

    public OrderDTO() {
    }

    public OrderDTO(String orderDate, List<OrderItem> items, double totalAmount, boolean isPaid,
            CustomerInfor customerInfo) {
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.customerInfo = customerInfo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public CustomerInfor getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfor customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderDate='" + orderDate + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                ", customerInfo=" + customerInfo +
                '}';
    }
}
