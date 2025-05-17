package javalorant.hustbook.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javalorant.hustbook.product.Product;
import javalorant.hustbook.user.CustomerInfo;

public class Order {
    private String orderID;
    private LocalDate orderDate;
    private List<OrderItem> items = new ArrayList<>();
    private double totalAmount;
    private boolean isPaid;
    private CustomerInfo customerInfo;

    public Order(String orderID, LocalDate orderDate, CustomerInfo customerInfo) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerInfo = customerInfo;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
