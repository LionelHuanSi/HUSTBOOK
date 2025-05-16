package com.hedspi.javalorant.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hedspi.javalorant.inventory.Product;

public class Order {
    private String orderID;
    private Date orderDate;
    private List<OrderItem> items;
    private double totalAmount;
    private boolean isPaid;
    private CustomerInfor customerInfo;

    public Order(String orderID, Date orderDate) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.isPaid = false;
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
        calculateTotal();
    }

    public void calculateTotal() {
        totalAmount = 0.0;
        for (OrderItem item : items) {
            totalAmount += item.getProduct().getSellingPrice() * item.getQuantity();
        }
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotal();
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

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public CustomerInfor getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfor customerInfo) {
        this.customerInfo = customerInfo;
    }
}
