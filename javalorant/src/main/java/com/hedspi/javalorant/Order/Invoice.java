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

    public Invoice(Date invoiceDate, Order order, PaymentMethod paymentMethod, Employee employee) {
        Invoice.countInvoice++;
        this.invoiceID = Invoice.countInvoice;
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.totalAmount = order.getTotalAmount();
        this.paymentMethod = paymentMethod;
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
}
