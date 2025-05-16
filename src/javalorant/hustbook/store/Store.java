package javalorant.hustbook.store;

import java.util.*;
import javalorant.hustbook.inventory.Inventory;
import javalorant.hustbook.order.Order;
import javalorant.hustbook.invoice.Invoice;
import javalorant.hustbook.invoice.PaymentMethod;
import javalorant.hustbook.expense.Expense;
import javalorant.hustbook.user.User;
import javalorant.hustbook.user.CustomerInfo;
import javalorant.hustbook.product.Product;

public class Store {
    private String storeName;
    private Inventory inventory;
    private List<Order> orderList;
    private List<Invoice> invoiceList;
    private List<Expense> expenseList;
    private List<User> userList;

    public Store(String name) {
        this.storeName = name;
        this.inventory = new Inventory();
        this.orderList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    // --- quan ly nguoi dung (user) ---
    public void addUser(User user) {
        userList.add(user);
    }

    public User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // --- quan ly san pham (product) ---
    public void addProduct(Product product) {
        inventory.addProduct(product);
    }

    public boolean removeProduct(String productID) {
        return inventory.removeProduct(productID);
    }

    // --- quan ly dat hang (order) ---
    public void createOrder(String orderID, Date orderDate) {
        Order order = new Order(orderID, orderDate);
        orderList.add(order);
    }

    public Order getOrderByID(String orderID) {
        for (Order order : orderList) {
            if (order.getOrderID().equals(orderID)) {
                return order;
            }
        }
        return null;
    }

    public void addItemToOrder(String orderID, String productID, int quantity) {
        Order order = getOrderByID(orderID);
        Product product = inventory.getProductByID(productID);
        if (order != null && product != null) {
            order.addItem(product, quantity);
        }
    }

    // --- quan ly hoa don (invoice) ---
    public Invoice generateInvoice(String invoiceID, Date invoiceDate, String orderID, String paymentMethod) {
        Order order = getOrderByID(orderID);
        if (order != null) {
            double totalAmount = order.getTotalAmount();
            CustomerInfo customerInfo = order.getCustomerInfo();
            PaymentMethod method = PaymentMethod.valueOf(paymentMethod.toUpperCase());
            Invoice invoice = new Invoice(invoiceID, invoiceDate, order, totalAmount, customerInfo, method);
            invoiceList.add(invoice);
            return invoice;
        }
        return null;
    }

    // --- quan ly chi phi (expense) ---
    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        return total;
    }

    // --- thong ke ---
    public double getRevenueInPeriod(Date start, Date end) {
        double revenue = 0;
        for (Invoice invoice : invoiceList) {
            Date date = invoice.getInvoiceDate();
            if (!date.before(start) && !date.after(end)) {
                revenue += invoice.getTotalAmount();
            }
        }
        return revenue;
    }

    public double getExpensesInPeriod(Date start, Date end) {
        double total = 0;
        for (Expense expense : expenseList) {
            Date date = expense.getDate();
            if (!date.before(start) && !date.after(end)) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public double getProfitInPeriod(Date start, Date end) {
        return getRevenueInPeriod(start, end) - getExpensesInPeriod(start, end);
    }

    // --- Getters ---
    public String getStoreName() {
        return storeName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public List<User> getUserList() {
        return userList;
    }
}
