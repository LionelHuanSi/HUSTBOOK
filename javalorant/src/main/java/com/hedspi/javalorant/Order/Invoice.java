package com.hedspi.javalorant.order;

import java.util.Date;

import com.hedspi.javalorant.user.Employee;

public class Invoice {
    public static long countInvoice = 0;
    private long invoiceID;
    private Date invoiceDate;
    private Order order;
    private double totalAmount;
    private PaymentMethod paymentMethod;
    private Employee employee;

    public Invoice(Date invoiceDate, Order order, Employee employee) {
        Invoice.countInvoice++;
        this.invoiceID = Invoice.countInvoice;
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.totalAmount = order.getTotalAmount();
        this.employee = employee;
    }

    public long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(long invoiceID) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String generateInvoiceDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Invoice ID: ").append(invoiceID).append("\n");
        details.append("Date: ").append(invoiceDate).append("\n");
        details.append("Order ID: ").append(order.getOrderID()).append("\n");
        details.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        details.append("Payment Method: ").append(paymentMethod);
        details.append("\nEmployee: ").append(employee.getUsername()).append("\n");
        return details.toString();
    }
}
