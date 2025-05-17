package javalorant.hustbook.invoice;

import java.time.LocalDate;

import javalorant.hustbook.order.Order;

public class Invoice {
    private String invoiceID;
    private LocalDate invoiceDate;
    private Order order;
    private double totalAmount;
    private String paymentMethod;

    public Invoice(String invoiceID, LocalDate invoiceDate, Order order, String paymentMethod) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.totalAmount = order.getTotalAmount();
    }

    public String getInvoiceID() { return invoiceID; }
    public void setInvoiceID(String invoiceID) { this.invoiceID = invoiceID; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    /** Sinh chuỗi chi tiết hóa đơn **/
    public String generateInvoiceDetails() {
        return String.format(
            "Invoice ID: %s%nDate: %s%nTotal: %.2f%nPayment: %s",
            invoiceID, invoiceDate, totalAmount, paymentMethod
        );
    }
}
