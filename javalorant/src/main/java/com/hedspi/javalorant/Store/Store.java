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
import com.hedspi.javalorant.order.CustomerInfor;
import com.hedspi.javalorant.order.Invoice;
import com.hedspi.javalorant.order.Order;
import com.hedspi.javalorant.order.OrderItem;
import com.hedspi.javalorant.order.PaymentMethod;
import com.hedspi.javalorant.user.Employee;
import com.hedspi.javalorant.user.User;
import com.hedspi.javalorant.user.UserRole;

//@Service
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






    /////////////// User Management //////////////
    public void addUser(User user) {
        userList.add(user);
    }

    public boolean removeUser(long userID) {
        return userList.removeIf(user -> user.getUserID() == userID);
    }

    public void updateUser(User updateUser) {
        for (User user : userList) {
            if (user.getUserID() == updateUser.getUserID()) {
                user.setUsername(updateUser.getUsername());
                user.setPassword(updateUser.getPassword());
                user.setFullName(updateUser.getFullName());
                user.setPhoneNumber(updateUser.getPhoneNumber());
                user.setRole(updateUser.getRole());
                break;
            }
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUserByUsername(String username) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }







    /////////////// Inventory Management //////////////
    public void addProduct(Product product) {
        inventory.addProduct(product);
    }

    public boolean removeProduct(long productID) {
        return inventory.removeProduct(productID);
    }

    public void updateProduct(Product product) {
        inventory.updateProduct(product);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Product getProductByName(String name) {
        return inventory.getProductByName(name);
    }







    //////////////// Order Management //////////////
    public void createOrder(Date orderDate) {
        Order newOrder = new Order(orderDate);
        orderList.add(newOrder);
    }

    public void createOrder(Date orderDate, CustomerInfor customerInfo) {
        Order newOrder = new Order(orderDate, customerInfo);
        orderList.add(newOrder);
    }

    public boolean removeOrder(long orderID) {
        return orderList.removeIf(order -> order.getOrderID() == orderID);
    }

    public void updateOrder(Order order) {
        for (Order existingOrder : orderList) {
            if (existingOrder.getOrderID() == order.getOrderID()) {
                existingOrder.setCustomerInfo(order.getCustomerInfo());
                existingOrder.setIsPaid(order.isPaid());
                break;
            }
        }
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Order getOrder(long orderID) {
        return orderList.stream()
                .filter(order -> order.getOrderID() == orderID)
                .findFirst()
                .orElse(null);
    }

    public void addItemToOrder(long orderID, long productID, int quantity) {
        Order order = getOrder(orderID);
        Product product = inventory.getProductByID(productID);
        if (order != null && product != null) {
            OrderItem item = new OrderItem(product, quantity);
            order.addItem(item);
        }
    }

    public Invoice generateInvoice(Date invoiceDate, long orderID, String paymentMethod, Employee employee) {
        Order order = getOrder(orderID);
        if (order != null) {
            Invoice invoice = new Invoice(invoiceDate, order, employee);
            if (paymentMethod != null) {
                invoice.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
            }
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

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    

    public void initializeData() {
        // Initialize Users
        addUser(new User("admin", "admin123", "Nguyễn Văn A", "0123456789", UserRole.Admin));
        addUser(new Employee("employee1", "e123", "Nguyễn Văn An", "0123456789", UserRole.Employee, 500000));
        addUser(new Employee("employee2", "e123", "Nguyễn Văn Anh", "0123456789", UserRole.Employee, 700000));

        // Initialize Books
        addProduct(new Book("The Art of Programming", 10, 35.0, 45.0, 
            "Tech Publications", "Donald Knuth", "978-0201038019"));
        addProduct(new Book("Clean Code", 15, 28.0, 39.99, 
            "Prentice Hall", "Robert Martin", "978-0132350884"));
        addProduct(new Book("Design Patterns", 8, 40.0, 54.99, 
            "Addison-Wesley", "Gang of Four", "978-0201633610"));

        // Initialize Stationary
        addProduct(new Stationary("Premium Notebook", 50, 3.0, 5.99, 
            "Moleskine", "Notebook"));
        addProduct(new Stationary("Gel Pen Set", 100, 1.0, 2.49, 
            "Pilot", "Pen"));
        addProduct(new Stationary("Color Pencils", 30, 4.0, 7.99, 
            "Faber-Castell", "Pencil"));

        // Initialize Toys
        addProduct(new Toy("LEGO Classic Set", 20, 15.0, 24.99, 
            "LEGO", 5));
        addProduct(new Toy("Rubik's Cube", 40, 5.0, 9.99, 
            "Rubik's", 8));
        addProduct(new Toy("Chess Set", 15, 12.0, 19.99, 
            "Classic Games", 7));

        // Create Customer Information
        CustomerInfor customer1 = new CustomerInfor("John Doe", "john@email.com");
        CustomerInfor customer2 = new CustomerInfor("Jane Smith", "jane@email.com");

        // Create Orders and Invoices
        Date currentDate = new Date();
        
        // Order 1
        createOrder(currentDate);
        Order order1 = orderList.get(orderList.size() - 1);
        order1.setCustomerInfo(customer1);
        addItemToOrder(order1.getOrderID(), 1, 2);
        addItemToOrder(order1.getOrderID(), 2, 3);
        order1.setIsPaid(true);

        Employee employee1 = (Employee) getUserByUsername("employee1");
        
        Invoice invoice1 = generateInvoice(currentDate, order1.getOrderID(), "CASH", employee1);
        invoice1.setPaymentMethod(PaymentMethod.CASH);

        // Order 2
        createOrder(currentDate);
        Order order2 = orderList.get(orderList.size() - 1);
        order2.setCustomerInfo(customer2);
        addItemToOrder(order2.getOrderID(), 3, 1);
        addItemToOrder(order2.getOrderID(), 4, 5);
        order2.setIsPaid(true);

        Employee employee2 = (Employee) getUserByUsername("employee2");
        
        Invoice invoice2 = generateInvoice(currentDate, order2.getOrderID(), "CREDIT_CARD", employee2);
        invoice2.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        // Add Expenses
        addExpense(new Expense("Rent", 1000.0, currentDate, "Monthly store rent"));
        addExpense(new Expense("Utilities", 200.0, currentDate, "Electricity and water"));
        addExpense(new Expense("Supplies", 150.0, currentDate, "Office supplies"));
        addExpense(new Expense("Marketing", 300.0, currentDate, "Online advertisements"));
    }
}
