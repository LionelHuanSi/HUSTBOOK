package com.hedspi.javalorant.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.hedspi.javalorant.dto.FilterDTO;
import com.hedspi.javalorant.dto.FilterEmployeeDTO;
import com.hedspi.javalorant.dto.FilterFinanceDTO;
import com.hedspi.javalorant.dto.FilterInvoiceDTO;
import com.hedspi.javalorant.dto.FilterOrderDTO;
import com.hedspi.javalorant.dto.SortEmployeeDTO;
import com.hedspi.javalorant.dto.SortInvoiceDTO;
import com.hedspi.javalorant.dto.SortOrderDTO;
import com.hedspi.javalorant.dto.SortProductsDTO;
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
    public String getStoreName() {
        return storeName;
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
                if (Objects.requireNonNull(user.getRole()) == UserRole.Employee) {
                    Employee employee = (Employee) user;
                    employee.setBasicSalary(((Employee) updateUser).getBasicSalary());
                    employee.setCoefficient(((Employee) updateUser).getCoefficient());
                }
                break;
            }
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUserByfullName(String fullName) {
        for (User user : userList) {
            if (user.getFullName().equals(fullName)) {
                return user;
            }
        }
        return null;
    }

    public List<User> filterEmployees(FilterEmployeeDTO filterDTO, List<User> userList) {
        List<User> filteredEmployees = userList.stream()
            .filter(user -> user.getRole() == UserRole.Employee)
            .collect(Collectors.toList());

        if (filterDTO.getName() != null && !filterDTO.getName().isEmpty()) {
            filteredEmployees = filteredEmployees.stream()
                .filter(emp -> emp.getFullName().toLowerCase()
                    .contains(filterDTO.getName().toLowerCase()))
                .collect(Collectors.toList());
        }

        if (filterDTO.getSalaryFrom() != null && filterDTO.getSalaryFrom() > 0) {
            filteredEmployees = filteredEmployees.stream()
                .filter(emp -> ((Employee)emp).tinhLuong() >= filterDTO.getSalaryFrom())
                .collect(Collectors.toList());
        }

        if (filterDTO.getSalaryTo() != null && filterDTO.getSalaryTo() > 0) {
            filteredEmployees = filteredEmployees.stream()
                .filter(emp -> ((Employee)emp).tinhLuong() <= filterDTO.getSalaryTo())
                .collect(Collectors.toList());
        }

        return filteredEmployees;
    }

    public List<User> sortEmployees(List<User> userList, SortEmployeeDTO sortDTO) {
        List<User> employees = userList.stream()
            .filter(user -> user.getRole() == UserRole.Employee)
            .collect(Collectors.toList());

        if (sortDTO.getUserIDList() != null && !sortDTO.getUserIDList().isEmpty()) {
            employees = employees.stream()
                .filter(emp -> sortDTO.getUserIDList().contains(emp.getUserID()))
                .collect(Collectors.toList());
        }

        if (sortDTO.getField() != null && !sortDTO.getField().isEmpty()) {
            switch (sortDTO.getField()) {
                case "userID" -> {
                    return employees.stream()
                        .sorted((e1, e2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Long.compare(e1.getUserID(), e2.getUserID());
                            } else {
                                return Long.compare(e2.getUserID(), e1.getUserID());
                            }
                        })
                        .collect(Collectors.toList());
                }
                case "salary" -> {
                    return employees.stream()
                        .sorted((e1, e2) -> {
                            double salary1 = ((Employee)e1).tinhLuong();
                            double salary2 = ((Employee)e2).tinhLuong();
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Double.compare(salary1, salary2);
                            } else {
                                return Double.compare(salary2, salary1);
                            }
                        })
                        .collect(Collectors.toList());
                }
                default -> {
                    return employees;
                }
            }
        }
        return employees;
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

    public List<Product> filterProducts(FilterDTO filterDTO, List<Product> productList) {
        if (filterDTO.getCategory() != null && !filterDTO.getCategory().isEmpty()) {
            productList = inventory.filterProductsByCategory(filterDTO.getCategory(), productList);
        }
        if (filterDTO.getName() != null && !filterDTO.getName().isEmpty()) {
            productList = inventory.filterProductsByName(filterDTO.getName(), productList);
        }
        if (filterDTO.getPurchasePriceFrom() > 0) {
            productList = inventory.filterProductsByPurchasePrice(filterDTO.getPurchasePriceFrom(), filterDTO.getPurchasePriceTo(), productList);
        }
        if (filterDTO.getSellingPriceFrom() > 0) {
            productList = inventory.filterProductsBySellingPrice(filterDTO.getSellingPriceFrom(), filterDTO.getSellingPriceTo(), productList);
        }
        return productList;
    }

    public List<Product> sortProducts(List<Product> productList, SortProductsDTO sortDTO) {
        if (sortDTO.getProductIDList() != null && !sortDTO.getProductIDList().isEmpty()) {
            productList = productList.stream()
                    .filter(product -> sortDTO.getProductIDList().contains(product.getProductID()))
                    .toList();
        }
        if (sortDTO.getField() != null && !sortDTO.getField().isEmpty()) {
            switch (sortDTO.getField()) {
                case "productID" -> productList = inventory.sortProductsByID(productList, sortDTO.getType());
                case "purchasePrice" -> productList =  inventory.sortProductsByPurchasePrice(productList, sortDTO.getType());
                case "sellingPrice" -> productList =  inventory.sortProductsBySellingPrice(productList, sortDTO.getType());
                case "quantity" -> productList = inventory.sortProductsByQuantity(productList, sortDTO.getType());
                default -> {
                    return productList;
                }
            }
        }
        return productList;
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

    public void generateInvoice(Date invoiceDate, Order order, PaymentMethod paymentMethod, String fullName) {
        if (order != null) {
            Invoice invoice = new Invoice(invoiceDate, order, paymentMethod, (Employee) getUserByfullName(fullName));
            invoiceList.add(invoice);
        }
    }

    public void addOrder(Order order, PaymentMethod paymentMethod, String fullName) {
        for (OrderItem orderItem : order.getItems()){
            Product product = orderItem.getProduct();
            if (product.getQuantity() >= orderItem.getQuantity()) {
                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            } else {
                product.setQuantity(0);
            }
        }
        orderList.add(order);
        if (order.isPaid()) {
            generateInvoice(order.getOrderDate(), order, paymentMethod, fullName);
        }
    }

    public boolean removeOrder(long orderID) {
        return orderList.removeIf(order -> order.getOrderID() == orderID);
    }

    public void updateOrder(long id, Order order, PaymentMethod paymentMethod, String fullName) {
        for (Order existingOrder : orderList) {
            if (existingOrder.getOrderID() == id) {
                existingOrder.setCustomerInfo(order.getCustomerInfo());
                existingOrder.setOrderDate(order.getOrderDate());
                existingOrder.setTotalAmount(order.getTotalAmount());
                existingOrder.setItems(order.getItems());
                existingOrder.setIsPaid(order.isPaid());
                if (order.isPaid()) {
                    generateInvoice(order.getOrderDate(), order, paymentMethod, fullName);
                }
                break;
            }
        }
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<Order> filterOrders(FilterOrderDTO filterDTO, List<Order> orderList) {
        List<Order> filteredOrders = new ArrayList<>(orderList);

        if (filterDTO.getCustomerName() != null && !filterDTO.getCustomerName().isEmpty()) {
            filteredOrders = filteredOrders.stream()
                .filter(order -> order.getCustomerInfo().getName()
                    .toLowerCase().contains(filterDTO.getCustomerName().toLowerCase()))
                .toList();
        }

        if (filterDTO.getStartDate() != null && !filterDTO.getStartDate().isEmpty()) {
            LocalDate startDate = LocalDate.parse(filterDTO.getStartDate());
            filteredOrders = filteredOrders.stream()
                .filter(order -> order.getOrderDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate().isAfter(startDate))
                .toList();
        }

        if (filterDTO.getEndDate() != null && !filterDTO.getEndDate().isEmpty()) {
            LocalDate endDate = LocalDate.parse(filterDTO.getEndDate());
            filteredOrders = filteredOrders.stream()
                .filter(order -> order.getOrderDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate().isBefore(endDate))
                .toList();
        }

        if (filterDTO.getIsPaid() != null) {
            filteredOrders = filteredOrders.stream()
                .filter(order -> order.isPaid() == filterDTO.getIsPaid())
                .toList();
        }

        if (filterDTO.getProductName() != null && !filterDTO.getProductName().isEmpty()) {
            filteredOrders = filteredOrders.stream()
                .filter(order -> order.getItems().stream()
                    .anyMatch(item -> item.getProduct().getName()
                        .toLowerCase().contains(filterDTO.getProductName().toLowerCase())))
                .toList();
        }

        return filteredOrders;
    }

    public List<Order> sortOrders(List<Order> orderList, SortOrderDTO sortDTO) {
        if (sortDTO.getOrderIDList() != null && !sortDTO.getOrderIDList().isEmpty()) {
            orderList = orderList.stream()
                .filter(order -> sortDTO.getOrderIDList().contains(order.getOrderID()))
                .toList();
        }

        if (sortDTO.getField() != null && !sortDTO.getField().isEmpty()) {
            switch (sortDTO.getField()) {
                case "orderID" -> {
                    return orderList.stream()
                        .sorted((o1, o2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Long.compare(o1.getOrderID(), o2.getOrderID());
                            } else {
                                return Long.compare(o2.getOrderID(), o1.getOrderID());
                            }
                        })
                        .toList();
                }
                case "orderDate" -> {
                    return orderList.stream()
                        .sorted((o1, o2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return o1.getOrderDate().compareTo(o2.getOrderDate());
                            } else {
                                return o2.getOrderDate().compareTo(o1.getOrderDate());
                            }
                        })
                        .toList();
                }
                case "totalAmount" -> {
                    return orderList.stream()
                        .sorted((o1, o2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Double.compare(o1.getTotalAmount(), o2.getTotalAmount());
                            } else {
                                return Double.compare(o2.getTotalAmount(), o1.getTotalAmount());
                            }
                        })
                        .toList();
                }
                default -> {
                    return orderList;
                }
            }
        }
        return orderList;
    }








    //////////////// Invoice Management //////////////
    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public List<Invoice> sortInvoices(List<Invoice> invoiceList, SortInvoiceDTO sortDTO) {
        if (sortDTO.getInvoiceIDList() != null && !sortDTO.getInvoiceIDList().isEmpty()) {
            invoiceList = invoiceList.stream()
                .filter(invoice -> sortDTO.getInvoiceIDList().contains(invoice.getInvoiceID()))
                .toList();
        }

        if (sortDTO.getField() != null && !sortDTO.getField().isEmpty()) {
            switch (sortDTO.getField()) {
                case "invoiceId" -> {
                    return invoiceList.stream()
                        .sorted((i1, i2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Long.compare(i1.getInvoiceID(), i2.getInvoiceID());
                            } else {
                                return Long.compare(i2.getInvoiceID(), i1.getInvoiceID());
                            }
                        })
                        .toList();
                }
                case "date" -> {
                    return invoiceList.stream()
                        .sorted((i1, i2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return i1.getInvoiceDate().compareTo(i2.getInvoiceDate());
                            } else {
                                return i2.getInvoiceDate().compareTo(i1.getInvoiceDate());
                            }
                        })
                        .toList();
                }
                case "total" -> {
                    return invoiceList.stream()
                        .sorted((i1, i2) -> {
                            if (sortDTO.getType().equalsIgnoreCase("up")) {
                                return Double.compare(i1.getTotalAmount(), i2.getTotalAmount());
                            } else {
                                return Double.compare(i2.getTotalAmount(), i1.getTotalAmount());
                            }
                        })
                        .toList();
                }
                default -> {
                    return invoiceList;
                }
            }
        }
        return invoiceList;
    }

    public List<Invoice> filterInvoices(FilterInvoiceDTO filterDTO, List<Invoice> invoiceList) {
        List<Invoice> filteredInvoices = new ArrayList<>(invoiceList);

        if (filterDTO.getCustomerName() != null && !filterDTO.getCustomerName().isEmpty()) {
            filteredInvoices = filteredInvoices.stream()
                .filter(invoice -> invoice.getOrder().getCustomerInfo().getName()
                    .toLowerCase().contains(filterDTO.getCustomerName().toLowerCase()))
                .toList();
        }

        if (filterDTO.getEmployeeName() != null && !filterDTO.getEmployeeName().isEmpty()) {
            filteredInvoices = filteredInvoices.stream()
                .filter(invoice -> invoice.getEmployee().getFullName()
                    .toLowerCase().contains(filterDTO.getEmployeeName().toLowerCase()))
                .toList();
        }

        if (filterDTO.getStartDate() != null && !filterDTO.getStartDate().isEmpty()) {
            LocalDate startDate = LocalDate.parse(filterDTO.getStartDate());
            filteredInvoices = filteredInvoices.stream()
                .filter(invoice -> invoice.getInvoiceDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    .isAfter(startDate))
                .toList();
        }

        if (filterDTO.getEndDate() != null && !filterDTO.getEndDate().isEmpty()) {
            LocalDate endDate = LocalDate.parse(filterDTO.getEndDate());
            filteredInvoices = filteredInvoices.stream()
                .filter(invoice -> invoice.getInvoiceDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    .isBefore(endDate))
                .toList();
        }

        if (filterDTO.getPaymentMethod() != null && !filterDTO.getPaymentMethod().isEmpty()) {
            filteredInvoices = filteredInvoices.stream()
                .filter(invoice -> invoice.getPaymentMethod().toString().equals(filterDTO.getPaymentMethod()))
                .toList();
        }

        return filteredInvoices;
    }










    ///////////////// Expense Management //////////////
    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public void removeExpense(long expenseID) {
        expenseList.removeIf(expense -> expense.getExpenseID() == expenseID);
    }

    public void updateExpense(long expenseID, Expense expense) {
        for (Expense existingExpense : expenseList) {
            if (existingExpense.getExpenseID() == expenseID) {
                existingExpense.setAmount(expense.getAmount());
                existingExpense.setDate(expense.getDate());
                existingExpense.setDescription(expense.getDescription());
                existingExpense.setExpenseType(expense.getExpenseType());
                break;
            }
        }
    }

    public List<Expense> filterExpense(FilterFinanceDTO filterDTO, List<Expense> expenseList) {
        List<Expense> filteredExpenses = new ArrayList<>(expenseList);
        if (filterDTO.getStartDate() != null && !filterDTO.getStartDate().isEmpty()) {
            LocalDate startDate = LocalDate.parse(filterDTO.getStartDate());
            filteredExpenses = filteredExpenses.stream()
                .filter(expense -> expense.getDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate().isAfter(startDate))
                    .collect(Collectors.toList());
        }

        if (filterDTO.getEndDate() != null && !filterDTO.getEndDate().isEmpty()) {
            LocalDate endDate = LocalDate.parse(filterDTO.getEndDate());
            filteredExpenses = filteredExpenses.stream()
                .filter(expense -> expense.getDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate().isBefore(endDate))
                    .collect(Collectors.toList());
        }
        return filteredExpenses;
    }

    public double getTotalExpensesInPeriod(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return expenseList.stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();
        }

        return expenseList.stream()
                .filter(expense -> {
                    if (startDate != null && endDate == null) {
                        // Filter expenses after startDate
                        return expense.getDate().compareTo(startDate) >= 0;
                    } else if (startDate == null && endDate != null) {
                        // Filter expenses before endDate
                        return expense.getDate().compareTo(endDate) <= 0;
                    } else {
                        // Filter expenses between startDate and endDate
                        return expense.getDate().compareTo(startDate) >= 0
                            && expense.getDate().compareTo(endDate) <= 0;
                    }
                })
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    /*public double getTotalProductPurchasePriceInPeriod(Date startDate, Date endDate) {
        // For product purchase price, we currently don't have date information in the inventory
        // So we'll just return the total regardless of date range
        return inventory.sumOfProductPurchasePrice();
    }*/

    public double getTotalEmployeeSalary(){
        double totalSalary = 0;
        for(User user : userList){
            if(user instanceof Employee){
                totalSalary += ((Employee) user).tinhLuong();
            }
        }
        System.out.println(totalSalary);
        return totalSalary;
    }

    public double getTotalEmployeeSalaryInPeriod(Date startDate, Date endDate) {
        // Default dates if null values are provided
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        
        if (startDate == null) {
            // Default start date: 1/1/2025
            calStart.set(2025, Calendar.JANUARY, 1);
        } else {
            calStart.setTime(startDate);
        }
        
        if (endDate == null) {
            // Default end date: current date
            // calEnd already has current date from Calendar.getInstance()
        } else {
            calEnd.setTime(endDate);
        }
        
        // Count how many 20th days fall between the start and end dates
        int count = 0;
        Calendar paymentDate = Calendar.getInstance();
        paymentDate.setTime(calStart.getTime());
        
        // Set to the 20th of the current month
        paymentDate.set(Calendar.DAY_OF_MONTH, 20);
        
        // If start date is after the 20th of its month, move to 20th of next month
        if (calStart.get(Calendar.DAY_OF_MONTH) > 20) {
            paymentDate.add(Calendar.MONTH, 1);
        }
        
        // Count all 20th days until end date
        while (paymentDate.getTime().compareTo(calEnd.getTime()) <= 0) {
            count++;
            paymentDate.add(Calendar.MONTH, 1);
        }
        
        // Multiply the base salary by the number of payment dates
        return getTotalEmployeeSalary() * count;
    }

    public double getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(Date startDate, Date endDate) { // tổng chi phí trong khoảng thời gian
        return getTotalExpensesInPeriod(startDate, endDate)
            /*+ getTotalProductPurchasePriceInPeriod(startDate, endDate)*/
            + getTotalEmployeeSalaryInPeriod(startDate, endDate);
    }

    public double getRevenueInPeriod(Date startDate, Date endDate) { // tổng lợi nhuận trong khoảng thời gian
        if (startDate == null && endDate == null) {
            return invoiceList.stream()
                    .mapToDouble(Invoice::getTotalAmount)
                    .sum();
        }

        return invoiceList.stream()
                .filter(invoice -> {
                    if (startDate != null && endDate == null) {
                        // Filter invoices after startDate
                        return invoice.getInvoiceDate().compareTo(startDate) >= 0;
                    } else if (startDate == null && endDate != null) {
                        // Filter invoices before endDate
                        return invoice.getInvoiceDate().compareTo(endDate) <= 0;
                    } else {
                        // Filter invoices between startDate and endDate
                        return invoice.getInvoiceDate().compareTo(startDate) >= 0
                            && invoice.getInvoiceDate().compareTo(endDate) <= 0;
                    }
                })
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }

    public double getProfitInPeriod(Date startDate, Date endDate) {
        return getRevenueInPeriod(startDate, endDate)
            - getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(startDate, endDate);
    }













    

    

    public void initializeData() {
        // Initialize Users
        addUser(new User("admin", "admin12345", "Nguyễn Văn A", "0123456789"));
        addUser(new Employee("employee1", "e123", "Nguyễn Văn An", "0123456789", 5000000, 1));
        addUser(new Employee("employee2", "e123", "Nguyễn Văn Anh", "0123456789", 5000000, 1.5));
        addUser(new Employee("employee3", "e123", "Trần Thị Bình", "0987654321", 5000000, 2));
        addUser(new Employee("employee4", "e123", "Lê Văn Cường", "0369852147", 5000000, 2.5));
        addUser(new Employee("employee5", "e123", "Phạm Thị Dung", "0741852963", 5000000, 3));

        // Initialize Books
        addProduct(new Book("The Art of Programming", 55, 700000.0, 950000.0, "Tech Publications", "Donald Knuth", "978-0201038019"));
        addProduct(new Book("Clean Code", 60, 600000.0, 850000.0, "Prentice Hall", "Robert Martin", "978-0132350884"));
        addProduct(new Book("Design Patterns", 50, 850000.0, 1200000.0, "Addison-Wesley", "Gang of Four", "978-0201633610"));
        addProduct(new Book("Java Programming", 65, 550000.0, 750000.0, "O'Reilly", "James Gosling", "978-0596009205"));
        addProduct(new Book("Python Basics", 80, 450000.0, 650000.0, "No Starch Press", "Al Sweigart", "978-1593279288"));
        addProduct(new Book("Data Structures", 52, 900000.0, 1350000.0, "Pearson", "Robert Sedgewick", "978-0321573513"));
        addProduct(new Book("Machine Learning", 50, 1500000.0, 2500000.0, "MIT Press", "Tom Mitchell", "978-0070428072"));
        addProduct(new Book("Web Development", 62, 750000.0, 1050000.0, "Wiley", "Jennifer Robbins", "978-1118907443"));
        
        // Initialize Stationary
        addProduct(new Stationary("Premium Notebook", 120, 150000.0, 220000.0, "Moleskine", "Notebook"));
        addProduct(new Stationary("Gel Pen Set", 200, 80000.0, 150000.0, "Pilot", "Pen"));
        addProduct(new Stationary("Color Pencils", 150, 130000.0, 220000.0, "Faber-Castell", "Pencil"));
        addProduct(new Stationary("Highlighter Pack", 180, 100000.0, 180000.0, "Stabilo", "Highlighter"));
        addProduct(new Stationary("Sticky Notes", 250, 60000.0, 120000.0, "Post-it", "Notes"));
        addProduct(new Stationary("Ruler Set", 160, 90000.0, 160000.0, "Westcott", "Ruler"));
        addProduct(new Stationary("Eraser Pack", 300, 50000.0, 100000.0, "Pentel", "Eraser"));
        addProduct(new Stationary("Scissors", 140, 120000.0, 200000.0, "Fiskars", "Scissors"));
        
        // Initialize Toys
        addProduct(new Toy("LEGO Classic Set", 60, 750000.0, 1200000.0, "LEGO", 5));
        addProduct(new Toy("Rubik's Cube", 100, 150000.0, 250000.0, "Rubik's", 8));
        addProduct(new Toy("Chess Set", 70, 350000.0, 550000.0, "Classic Games", 7));
        addProduct(new Toy("Remote Control Car", 55, 1200000.0, 1800000.0, "Hot Wheels", 6));
        addProduct(new Toy("Monopoly Board", 80, 350000.0, 600000.0, "Hasbro", 8));
        addProduct(new Toy("Barbie Doll", 90, 400000.0, 650000.0, "Mattel", 4));
        addProduct(new Toy("Building Blocks", 110, 300000.0, 480000.0, "Mega Bloks", 3));
        addProduct(new Toy("Science Kit", 50, 1000000.0, 1500000.0, "National Geographic", 10));

        // Add orders and invoices
        String[] customerNames = {
            "Nguyễn Văn Minh", "Trần Thị Hương", "Lê Thành Nam", "Phạm Hồng Hà", "Hoàng Văn Đức",
            "Vũ Thị Mai", "Đặng Quốc Tuấn", "Bùi Thị Lan", "Ngô Đình Phong", "Dương Thị Thảo",
            "Đỗ Văn Hoàng", "Hồ Thị Ngọc", "Phan Văn Tú", "Trương Minh Anh", "Võ Thị Kim",
            "Đinh Văn Bình", "Lý Thị Hà", "Nguyễn Minh Quân", "Trần Văn Tâm", "Lê Thị Thanh"
        };
        
        String[] contactInfo = {
            "0987654321", "0912345678", "0965432178", "0943215678", "0978563412",
            "0932145678", "0954321876", "0967891234", "0945678123", "0923456789",
            "0956781234", "0934567812", "0978123456", "0965432187", "0943215678",
            "0912876543", "0967812345", "0923451789", "0956789123", "0945671234"
        };
        
        // Mảng các phương thức thanh toán
        PaymentMethod[] paymentMethods = PaymentMethod.values();
        
        // Lấy danh sách sách (lọc từ danh sách sản phẩm)
        List<Product> booksList = inventory.getAllProducts().stream()
                .filter(product -> product instanceof Book)
                .toList();
        
        // Lấy danh sách nhân viên
        List<User> employeeList = userList.stream()
                .filter(user -> user.getRole() == UserRole.Employee)
                .toList();
        
        // Random để tạo dữ liệu ngẫu nhiên
        Random random = new Random();
        
        // Tạo hàm helper để lấy ngày ngẫu nhiên từ 1/1/2025 đến 22/5/2025
        Calendar startCal = Calendar.getInstance();
        startCal.set(2025, Calendar.JANUARY, 1);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2025, Calendar.MAY, 22);
        
        long startMillis = startCal.getTimeInMillis();
        long endMillis = endCal.getTimeInMillis();
        
        // Tạo 100 đơn hàng và hóa đơn
        for (int i = 0; i < 100; i++) {
            // Tạo ngày đặt hàng ngẫu nhiên
            long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
            Date orderDate = new Date(randomMillis);
            
            // Tạo thông tin khách hàng ngẫu nhiên
            int customerIndex = random.nextInt(customerNames.length);
            CustomerInfor customerInfo = new CustomerInfor(
                customerNames[customerIndex], 
                contactInfo[customerIndex]
            );
            
            // Tạo đơn hàng mới
            Order order = new Order(orderDate, customerInfo);
            
            // Số lượng sách trong đơn hàng (1-5)
            int numberOfBooks = random.nextInt(5) + 1;
            
            // Thêm sách vào đơn hàng
            for (int j = 0; j < numberOfBooks; j++) {
                // Chọn sách ngẫu nhiên
                Product book = booksList.get(random.nextInt(booksList.size()));
                
                // Số lượng của mỗi sách (1-5)
                int quantity = random.nextInt(5) + 1;
                
                // Thêm vào đơn hàng
                order.addItem(new OrderItem(book, quantity));
            }
            
            // Tính tổng tiền
            order.calculateTotal();
            
            // Đặt trạng thái đã thanh toán
            order.setIsPaid(true);
            
            // Thêm đơn hàng vào danh sách
            orderList.add(order);
            
            // Chọn phương thức thanh toán ngẫu nhiên
            PaymentMethod paymentMethod = paymentMethods[random.nextInt(paymentMethods.length)];
            
            // Chọn nhân viên ngẫu nhiên
            User employee = employeeList.get(random.nextInt(employeeList.size()));
            
            // Tạo hóa đơn
            Invoice invoice = new Invoice(orderDate, order, paymentMethod, (Employee) employee);
            
            // Thêm hóa đơn vào danh sách
            invoiceList.add(invoice);
        }
        
        // Add orders and invoices
        

        // Add Expenses
        // Tạo chi phí từ tháng 1/2025 đến tháng 5/2025
        Calendar cal = Calendar.getInstance();
        
        // Tháng 1/2025
        cal.set(2025, Calendar.JANUARY, 5);
        addExpense(new Expense("Electricity", 3200000.0, cal.getTime(), "Tiền điện tháng 1/2025"));
        addExpense(new Expense("Water", 1500000.0, cal.getTime(), "Tiền nước tháng 1/2025"));
        addExpense(new Expense("Facility Maintenance", 8500000.0, cal.getTime(), "Bảo trì cơ sở vật chất tháng 1/2025"));
        
        // Tháng 2/2025
        cal.set(2025, Calendar.FEBRUARY, 8);
        addExpense(new Expense("Electricity", 3350000.0, cal.getTime(), "Tiền điện tháng 2/2025"));
        addExpense(new Expense("Water", 1600000.0, cal.getTime(), "Tiền nước tháng 2/2025"));
        addExpense(new Expense("Facility Maintenance", 2500000.0, cal.getTime(), "Bảo trì cơ sở vật chất tháng 2/2025"));
        
        // Tháng 3/2025
        cal.set(2025, Calendar.MARCH, 10);
        addExpense(new Expense("Electricity", 3500000.0, cal.getTime(), "Tiền điện tháng 3/2025"));
        addExpense(new Expense("Water", 1750000.0, cal.getTime(), "Tiền nước tháng 3/2025"));
        addExpense(new Expense("Facility Maintenance", 5200000.0, cal.getTime(), "Bảo trì cơ sở vật chất tháng 3/2025"));
        
        // Tháng 4/2025
        cal.set(2025, Calendar.APRIL, 15);
        addExpense(new Expense("Electricity", 3650000.0, cal.getTime(), "Tiền điện tháng 4/2025"));
        addExpense(new Expense("Water", 1800000.0, cal.getTime(), "Tiền nước tháng 4/2025"));
        addExpense(new Expense("Facility Maintenance", 6800000.0, cal.getTime(), "Bảo trì cơ sở vật chất tháng 4/2025"));
        
        // Tháng 5/2025
        cal.set(2025, Calendar.MAY, 20);
        addExpense(new Expense("Electricity", 3800000.0, cal.getTime(), "Tiền điện tháng 5/2025"));
        addExpense(new Expense("Water", 1900000.0, cal.getTime(), "Tiền nước tháng 5/2025"));
        addExpense(new Expense("Facility Maintenance", 7500000.0, cal.getTime(), "Bảo trì cơ sở vật chất tháng 5/2025"));
        
        // Chi phí phát sinh
        cal.set(2025, Calendar.JANUARY, 15);
        addExpense(new Expense("Emergency Repair", 5600000.0, cal.getTime(), "Sửa chữa khẩn cấp hệ thống ống nước"));
        
        cal.set(2025, Calendar.FEBRUARY, 22);
        addExpense(new Expense("Security System", 18000000.0, cal.getTime(), "Lắp đặt hệ thống camera an ninh mới"));
        
        cal.set(2025, Calendar.MARCH, 28);
        addExpense(new Expense("Pest Control", 2800000.0, cal.getTime(), "Dịch vụ diệt côn trùng quý 1"));
        
        cal.set(2025, Calendar.APRIL, 5);
        addExpense(new Expense("Furniture", 25000000.0, cal.getTime(), "Mua sắm nội thất mới cho khu vực khách hàng"));
        
        cal.set(2025, Calendar.MAY, 12);
        addExpense(new Expense("Insurance Premium", 32000000.0, cal.getTime(), "Phí bảo hiểm hàng năm"));
        
        cal.set(2025, Calendar.JANUARY, 25);
        addExpense(new Expense("Advertising", 12000000.0, cal.getTime(), "Chi phí quảng cáo đầu năm"));
        
        cal.set(2025, Calendar.FEBRUARY, 15);
        addExpense(new Expense("Staff Training", 8500000.0, cal.getTime(), "Đào tạo nhân viên về dịch vụ khách hàng"));
        
        cal.set(2025, Calendar.MARCH, 20);
        addExpense(new Expense("IT Support", 6500000.0, cal.getTime(), "Nâng cấp và bảo trì hệ thống"));
        
        cal.set(2025, Calendar.APRIL, 25);
        addExpense(new Expense("Event", 15000000.0, cal.getTime(), "Tổ chức sự kiện giảm giá lớn"));
        
        cal.set(2025, Calendar.MAY, 5);
        addExpense(new Expense("Office Supplies", 4800000.0, cal.getTime(), "Mua sắm văn phòng phẩm"));
    }
}
