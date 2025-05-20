package com.hedspi.javalorant.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hedspi.javalorant.dto.FilterDTO;
import com.hedspi.javalorant.dto.FilterEmployeeDTO;
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
                switch (user.getRole()) {
                    case Employee -> {
                        Employee employee = (Employee) user;
                        employee.setBasicSalary(((Employee) updateUser).getBasicSalary());
                        employee.setCoefficient(((Employee) updateUser).getCoefficient());
                    }
                    default -> {
                    }
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

    public Invoice generateInvoice(Date invoiceDate, Order order, PaymentMethod paymentMethod, String fullName) {
        if (order != null) {
            Invoice invoice = new Invoice(invoiceDate, order, paymentMethod, (Employee) getUserByfullName(fullName));
            invoiceList.add(invoice);
            return invoice;
        }
        return null;
    }

    public void addOrder(Order order, PaymentMethod paymentMethod, String fullName) {
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













    

    

    public void initializeData() {
        // Initialize Users
        addUser(new User("admin", "admin123", "Nguyễn Văn A", "0123456789"));
        addUser(new Employee("employee1", "e123", "Nguyễn Văn An", "0123456789", 5000000, 1));
        addUser(new Employee("employee2", "e123", "Nguyễn Văn Anh", "0123456789", 5000000, 1.5));
        addUser(new Employee("employee3", "e123", "Trần Thị Bình", "0987654321", 5000000, 2));
        addUser(new Employee("employee4", "e123", "Lê Văn Cường", "0369852147", 5000000, 2.5));
        addUser(new Employee("employee5", "e123", "Phạm Thị Dung", "0741852963", 5000000, 3));

        // Initialize Books
        addProduct(new Book("The Art of Programming", 10, 35.0, 45.0, "Tech Publications", "Donald Knuth", "978-0201038019"));
        addProduct(new Book("Clean Code", 15, 28.0, 39.99, "Prentice Hall", "Robert Martin", "978-0132350884"));
        addProduct(new Book("Design Patterns", 8, 40.0, 54.99, "Addison-Wesley", "Gang of Four", "978-0201633610"));
        addProduct(new Book("Java Programming", 20, 30.0, 42.99, "O'Reilly", "James Gosling", "978-0596009205"));
        addProduct(new Book("Python Basics", 25, 25.0, 34.99, "No Starch Press", "Al Sweigart", "978-1593279288"));
        addProduct(new Book("Data Structures", 12, 45.0, 59.99, "Pearson", "Robert Sedgewick", "978-0321573513"));
        addProduct(new Book("Machine Learning", 10, 50.0, 69.99, "MIT Press", "Tom Mitchell", "978-0070428072"));
        addProduct(new Book("Web Development", 18, 35.0, 49.99, "Wiley", "Jennifer Robbins", "978-1118907443"));

        // Initialize Stationary
        addProduct(new Stationary("Premium Notebook", 50, 3.0, 5.99, "Moleskine", "Notebook"));
        addProduct(new Stationary("Gel Pen Set", 100, 1.0, 2.49, "Pilot", "Pen"));
        addProduct(new Stationary("Color Pencils", 30, 4.0, 7.99, "Faber-Castell", "Pencil"));
        addProduct(new Stationary("Highlighter Pack", 80, 2.0, 4.99, "Stabilo", "Highlighter"));
        addProduct(new Stationary("Sticky Notes", 120, 1.5, 3.49, "Post-it", "Notes"));
        addProduct(new Stationary("Ruler Set", 40, 2.5, 5.49, "Westcott", "Ruler"));
        addProduct(new Stationary("Eraser Pack", 150, 0.5, 1.99, "Pentel", "Eraser"));
        addProduct(new Stationary("Scissors", 35, 3.5, 6.99, "Fiskars", "Scissors"));

        // Initialize Toys
        addProduct(new Toy("LEGO Classic Set", 20, 15.0, 24.99, "LEGO", 5));
        addProduct(new Toy("Rubik's Cube", 40, 5.0, 9.99, "Rubik's", 8));
        addProduct(new Toy("Chess Set", 15, 12.0, 19.99, "Classic Games", 7));
        addProduct(new Toy("Remote Control Car", 10, 25.0, 39.99, "Hot Wheels", 6));
        addProduct(new Toy("Monopoly Board", 25, 18.0, 29.99, "Hasbro", 8));
        addProduct(new Toy("Barbie Doll", 30, 15.0, 24.99, "Mattel", 4));
        addProduct(new Toy("Building Blocks", 35, 20.0, 34.99, "Mega Bloks", 3));
        addProduct(new Toy("Science Kit", 15, 30.0, 49.99, "National Geographic", 10));

        // Add Expenses
        Date currentDate = new Date();
        addExpense(new Expense("Rent", 1000.0, currentDate, "Monthly store rent"));
        addExpense(new Expense("Utilities", 200.0, currentDate, "Electricity and water"));
        addExpense(new Expense("Supplies", 150.0, currentDate, "Office supplies"));
        addExpense(new Expense("Marketing", 300.0, currentDate, "Online advertisements"));
        addExpense(new Expense("Insurance", 250.0, currentDate, "Store insurance"));
    }
}
