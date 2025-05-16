package com.hedspi.javalorant.order;

import java.util.Date;

public class Invoice {
    private String invoiceID;
    private Date invoiceDate;
    private Order order;
    private double totalAmount;
    private PaymentMethod paymentMethod;

    public Invoice(String invoiceID, Date invoiceDate, Order order) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.totalAmount = order.getTotalAmount();
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.totalAmount = order.getTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String generateInvoiceDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Invoice ID: ").append(invoiceID).append("\n");
        details.append("Date: ").append(invoiceDate).append("\n");
        details.append("Order ID: ").append(order.getOrderID()).append("\n");
        details.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        details.append("Payment Method: ").append(paymentMethod);
        return details.toString();
    }
}
