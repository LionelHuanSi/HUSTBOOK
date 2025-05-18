package com.hedspi.javalorant.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    public static long countOrder = 0;
    private long orderID;
    private Date orderDate;
    private List<OrderItem> items;
    private double totalAmount;
    private boolean isPaid;
    private CustomerInfor customerInfo;

    public Order(Date orderDate) {
        Order.countOrder++;
        this.orderID = Order.countOrder;
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.isPaid = false;
    }

    public Order(Date orderDate, CustomerInfor customerInfo) {
        this(orderDate);
        this.customerInfo = customerInfo;
    }

    public Order(Date orderDate, List<OrderItem> items, double totalAmount, boolean isPaid,
            CustomerInfor customerInfo) {
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.customerInfo = customerInfo;
    }

    public void calculateTotal() {
        totalAmount = 0.0;
        for (OrderItem item : items) {
            totalAmount += item.getProduct().getSellingPrice() * item.getQuantity();
        }
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
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

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        calculateTotal();
    }

    public void updateItem(OrderItem updateItem){
        for (OrderItem item : items) {
            if (item.getProduct().getProductID() == updateItem.getProduct().getProductID()) {
                item.setQuantity(updateItem.getQuantity());
                item.setProduct(updateItem.getProduct());
                break;
            }
        }
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
