package com.hedspi.javalorant.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hedspi.javalorant.expense.Expense;
import com.hedspi.javalorant.inventory.Book;
import com.hedspi.javalorant.inventory.Inventory;
import com.hedspi.javalorant.inventory.Product;
import com.hedspi.javalorant.inventory.Stationary;
import com.hedspi.javalorant.inventory.Toy;
import com.hedspi.javalorant.models.User;
import com.hedspi.javalorant.models.UserRole;
import com.hedspi.javalorant.order.CustomerInfor;
import com.hedspi.javalorant.order.Invoice;
import com.hedspi.javalorant.order.Order;
import com.hedspi.javalorant.order.PaymentMethod;

public class Store {
    private final String storeName;
    private final Inventory inventory;
    private final List<Order> orderList;
    private final List<Invoice> invoiceList;
    private final List<Expense> expenseList;
    private final List<User> userList;

    public Store(String storeName) {
        this.storeName = storeName;
        this.inventory = new Inventory();
        this.orderList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        inventory.addProduct(product);
    }

    public boolean removeProduct(String productID) {
        return inventory.removeProduct(productID);
    }

    public void createOrder(String orderID, Date orderDate) {
        Order newOrder = new Order(orderID, orderDate);
        orderList.add(newOrder);
    }

    public Order getOrder(String orderID) {
        return orderList.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findFirst()
                .orElse(null);
    }

    public void addItemToOrder(String orderID, String productID, int quantity) {
        Order order = getOrder(orderID);
        Product product = inventory.getProductByID(productID);
        if (order != null && product != null) {
            order.addItem(product, quantity);
        }
    }

    public Invoice generateInvoice(String invoiceID, Date invoiceDate, String orderID, String paymentMethod) {
        Order order = getOrder(orderID);
        if (order != null) {
            Invoice invoice = new Invoice(invoiceID, invoiceDate, order);
            invoiceList.add(invoice);
            return invoice;
        }
        return null;
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public double getTotalExpenses() {
        return expenseList.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getRevenueInPeriod(Date startDate, Date endDate) {
        return invoiceList.stream()
                .filter(invoice -> invoice.getInvoiceDate().after(startDate) 
                        && invoice.getInvoiceDate().before(endDate))
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }

    public double getExpensesInPeriod(Date startDate, Date endDate) {
        return expenseList.stream()
                .filter(expense -> expense.getDate().after(startDate) 
                        && expense.getDate().before(endDate))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getCOGSInPeriod(Date startDate, Date endDate) {
        // Calculate Cost of Goods Sold for the period
        return orderList.stream()
                .filter(order -> order.getOrderDate().after(startDate) 
                        && order.getOrderDate().before(endDate))
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getProduct().getPurchasePrice() * item.getQuantity())
                .sum();
    }

    public double getProfitInPeriod(Date startDate, Date endDate) {
        double revenue = getRevenueInPeriod(startDate, endDate);
        double expenses = getExpensesInPeriod(startDate, endDate);
        double cogs = getCOGSInPeriod(startDate, endDate);
        return revenue - expenses - cogs;
    }

    public String getStoreName() {
        return storeName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Order> getOrderList() {
        return new ArrayList<>(orderList);
    }

    public List<Invoice> getInvoiceList() {
        return new ArrayList<>(invoiceList);
    }

    public List<Expense> getExpenseList() {
        return new ArrayList<>(expenseList);
    }

    public List<User> getUserList() {
        return new ArrayList<>(userList);
    }

    public void initializeData() {
        // Initialize Users
        addUser(new User("U001", "admin", "admin123", UserRole.Admin));
        addUser(new User("U002", "employee1", "emp123", UserRole.Employee));
        addUser(new User("U003", "employee2", "emp456", UserRole.Employee));

        // Initialize Books
        addProduct(new Book("B001", "The Art of Programming", 10, 35.0, 45.0, 
            "Tech Publications", "Donald Knuth", "978-0201038019"));
        addProduct(new Book("B002", "Clean Code", 15, 28.0, 39.99, 
            "Prentice Hall", "Robert Martin", "978-0132350884"));
        addProduct(new Book("B003", "Design Patterns", 8, 40.0, 54.99, 
            "Addison-Wesley", "Gang of Four", "978-0201633610"));

        // Initialize Stationary
        addProduct(new Stationary("S001", "Premium Notebook", 50, 3.0, 5.99, 
            "Moleskine", "Notebook"));
        addProduct(new Stationary("S002", "Gel Pen Set", 100, 1.0, 2.49, 
            "Pilot", "Pen"));
        addProduct(new Stationary("S003", "Color Pencils", 30, 4.0, 7.99, 
            "Faber-Castell", "Pencil"));

        // Initialize Toys
        addProduct(new Toy("T001", "LEGO Classic Set", 20, 15.0, 24.99, 
            "LEGO", 5));
        addProduct(new Toy("T002", "Rubik's Cube", 40, 5.0, 9.99, 
            "Rubik's", 8));
        addProduct(new Toy("T003", "Chess Set", 15, 12.0, 19.99, 
            "Classic Games", 7));

        // Create Customer Information
        CustomerInfor customer1 = new CustomerInfor("C001", "John Doe", "john@email.com");
        CustomerInfor customer2 = new CustomerInfor("C002", "Jane Smith", "jane@email.com");

        // Create Orders and Invoices
        Date currentDate = new Date();
        
        // Order 1
        createOrder("O001", currentDate);
        Order order1 = getOrder("O001");
        order1.setCustomerInfo(customer1);
        addItemToOrder("O001", "B001", 2);
        addItemToOrder("O001", "S001", 3);
        order1.setIsPaid(true);
        
        Invoice invoice1 = generateInvoice("INV001", currentDate, "O001", "CASH");
        invoice1.setPaymentMethod(PaymentMethod.CASH);

        // Order 2
        createOrder("O002", currentDate);
        Order order2 = getOrder("O002");
        order2.setCustomerInfo(customer2);
        addItemToOrder("O002", "T001", 1);
        addItemToOrder("O002", "S002", 5);
        order2.setIsPaid(true);
        
        Invoice invoice2 = generateInvoice("INV002", currentDate, "O002", "CREDIT_CARD");
        invoice2.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        // Add Expenses
        addExpense(new Expense("E001", "Rent", 1000.0, currentDate, "Monthly store rent"));
        addExpense(new Expense("E002", "Utilities", 200.0, currentDate, "Electricity and water"));
        addExpense(new Expense("E003", "Supplies", 150.0, currentDate, "Office supplies"));
        addExpense(new Expense("E004", "Marketing", 300.0, currentDate, "Online advertisements"));
    }
}
