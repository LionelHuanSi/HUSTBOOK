package com.hedspi.javalorant.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.hedspi.javalorant.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hedspi.javalorant.expense.Expense;
import com.hedspi.javalorant.inventory.Book;
import com.hedspi.javalorant.inventory.Product;
import com.hedspi.javalorant.inventory.Stationary;
import com.hedspi.javalorant.inventory.Toy;
import com.hedspi.javalorant.order.Invoice;
import com.hedspi.javalorant.order.Order;
import com.hedspi.javalorant.order.OrderItem;
import com.hedspi.javalorant.store.Store;
import com.hedspi.javalorant.user.Employee;
import com.hedspi.javalorant.user.User;

@RestController
@RequestMapping("/api")
public class JavalorantController {
    public final Store store = new Store("javalorantStore");

    
    public JavalorantController() {
        store.initializeData();
    }
















    // User Management APIs
    @GetMapping("/users") // Get all users
    public List<User> getAllUsers() {
        System.out.println("Store hashCode: " + store.hashCode());
        System.out.println("UserList hashCode: " + store.getUserList().hashCode());
        return store.getUserList();
    }

    @PostMapping("/users/filter") // Filter users
    public List<User> filterEmployees(@RequestBody FilterEmployeeDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO);
        return store.filterEmployees(filterDTO, store.getUserList());
    }

    @PostMapping("/users/sort") // Sort users
    public List<User> sortEmployees(@RequestBody SortEmployeeDTO sortDTO) {
        System.out.println("Received sortDTO: " + sortDTO);
        return store.sortEmployees(store.getUserList(), sortDTO);
    }

    @PostMapping("/users") // Add a new user
    public void addUser(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("Received employeeDTO: " + employeeDTO.toString());
        try {
            User user = new Employee(
                employeeDTO.getUsername(),
                employeeDTO.getPassword(),
                employeeDTO.getFullName(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getBasicSalary(),
                employeeDTO.getCoefficient()
            );
            store.addUser(user);
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    @PostMapping("/users/{id}") // Update an existing user
    public ResponseEntity<?> updateUser(
            @PathVariable long id,
            @RequestBody EmployeeDTO employeeDTO) {
        try {
            System.out.println("Received employeeDTO: " + employeeDTO.toString());
            User user = new Employee(
                employeeDTO.getUsername(),
                employeeDTO.getPassword(),
                employeeDTO.getFullName(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getBasicSalary(),
                employeeDTO.getCoefficient()
            );
            user.setUserID(id);
            store.updateUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Lỗi khi cập nhật người dùng: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}") // Remove a user
    public ResponseEntity<?> removeUser(@PathVariable long id) {
        try {
            System.out.println("Removing user with ID: " + id);
            boolean result = store.removeUser(id);
            if (result) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest()
                    .body("Không tìm thấy người dùng với ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Lỗi khi xóa người dùng: " + e.getMessage());
        }
    }







    // Inventory Management APIs
    @GetMapping("/products") // Get all products
    public List<Product> getAllProducts() {
        return store.getInventory().getAllProducts();
    }
    @PostMapping("/products/filter") // Filter products
    public List<Product> filterProducts(@RequestBody FilterDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        return store.filterProducts(filterDTO, store.getInventory().getAllProducts());
    }
    @PostMapping("/products/sort") // Sort products
    public List<Product> sortProducts(@RequestBody SortProductsDTO sortDTO) {
        System.out.println("Received filterDTO: " + sortDTO.toString());
        return store.sortProducts(store.getInventory().getAllProducts(), sortDTO);
    }
    @PostMapping("/products") // Add a new product
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            System.out.println("Received productDTO: " + productDTO.toString());
            Product product;
            // In ra để debug
            System.out.println("ProductType: " + productDTO.getProductType());
            System.out.println("Name: " + productDTO.getName());
            System.out.println("Selling Price: " + productDTO.getSellingPrice());
            switch (productDTO.getProductType().toLowerCase()) {
                case "book" -> product = new Book(
                    productDTO.getName(),
                    productDTO.getQuantity(),
                    productDTO.getPurchasePrice(),
                    productDTO.getSellingPrice(),
                    productDTO.getPublisher(),
                    productDTO.getAuthor(),
                    productDTO.getISBN()
                );
                case "stationary" -> product = new Stationary(
                    productDTO.getName(),
                    productDTO.getQuantity(),
                    productDTO.getPurchasePrice(),
                    productDTO.getSellingPrice(),
                    productDTO.getBrand(),
                    productDTO.getStationaryType()
                );
                case "toy" -> product = new Toy(
                    productDTO.getName(),
                    productDTO.getQuantity(),
                    productDTO.getPurchasePrice(),
                    productDTO.getSellingPrice(),
                    productDTO.getBrand(),
                    productDTO.getSuitableAge()
                );
                default -> {
                    return ResponseEntity.badRequest()
                        .body("Loại sản phẩm không hợp lệ: " + productDTO.getProductType());
                }
            }
            store.addProduct(product);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }
    @DeleteMapping("/products/{id}") // Remove a product
    public boolean removeProduct(@PathVariable long id) {
        return store.removeProduct(id);
    }
    @PostMapping("/products/{id}") // Update an existing product
    public ResponseEntity<?> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDTO productDTO) {
        try {
            System.out.println("Received productDTO: " + productDTO.toString());
            Product product;
            switch (productDTO.getProductType().toLowerCase()) {
                case "book" -> {
                    product = new Book(
                        productDTO.getName(),      
                        productDTO.getQuantity(),     
                        productDTO.getPurchasePrice(),
                        productDTO.getSellingPrice(), 
                        productDTO.getPublisher(),    
                        productDTO.getAuthor(),       
                        productDTO.getISBN()          
                    );
                    product.setProductID(id);
                }
                case "stationary" -> {
                    product = new Stationary(
                        productDTO.getName(),      
                        productDTO.getQuantity(),     
                        productDTO.getPurchasePrice(),
                        productDTO.getSellingPrice(), 
                        productDTO.getBrand(),        
                        productDTO.getStationaryType() 
                    );
                    product.setProductID(id);
                }
                case "toy" -> {
                    product = new Toy(
                        productDTO.getName(),      
                        productDTO.getQuantity(),     
                        productDTO.getPurchasePrice(),
                        productDTO.getSellingPrice(), 
                        productDTO.getBrand(),        
                        productDTO.getSuitableAge()       
                    );
                    product.setProductID(id);
                }
                default -> {
                    return ResponseEntity.badRequest()
                        .body("Loại sản phẩm không hợp lệ: " + productDTO.getProductType());
                }
            }
            System.out.println("Product: " + product.toString());
            store.updateProduct(product);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }
















    // Order Management APIs
    @GetMapping("/orders") // Get all orders
    public List<Order> getAllOrders() {
        System.out.println("OrderList: " + store.getOrderList().toString());
        return store.getOrderList();
    }
    @PostMapping("/orders/filter") // Filter orders
    public List<Order> filterOrders(@RequestBody FilterOrderDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        return store.filterOrders(filterDTO, store.getOrderList());
    }
    @PostMapping("/orders/sort") // Sort orders
    public List<Order> sortOrders(@RequestBody SortOrderDTO sortDTO) {
        System.out.println("Received sortDTO: " + sortDTO.toString());
        return store.sortOrders(store.getOrderList(), sortDTO);
    }
    @PostMapping("/orders") // Add a new order
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        try {
            System.out.println("Received orderDTO: " + orderDTO.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date orderDate = dateFormat.parse(orderDTO.getOrderDate());
            @SuppressWarnings("unchecked")
            List<OrderItem> orderItems = orderDTO.getItems().stream()
            .map(item -> {
                Map<String, Object> productMap = (Map<String, Object>) item.get("product");
                Product product = store.getInventory().getProductByID(((Integer) productMap.get("productID")).longValue());
                return new OrderItem(product, ((Integer) item.get("quantity")));
            })
            .collect(Collectors.toList());
            Order order = new Order(
                orderDate,
                orderItems,
                orderDTO.getTotalAmount(),
                orderDTO.getPaid(),
                orderDTO.getCustomerInfo()
            );
            store.addOrder(order, orderDTO.getPaymentMethod(), orderDTO.getFullName());
            return ResponseEntity.ok().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Lỗi khi phân tích ngày: " + e.getMessage());
        }
        
    }
    @PostMapping("/orders/{id}") // Update an existing order
    public ResponseEntity<?> updateOrder(
            @PathVariable long id,
            @RequestBody OrderDTO orderDTO) {
        try {
            System.out.println("Received orderDTO: " + orderDTO.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date orderDate = dateFormat.parse(orderDTO.getOrderDate());
            @SuppressWarnings("unchecked")
            List<OrderItem> orderItems = orderDTO.getItems().stream()
            .map(item -> {
                Map<String, Object> productMap = (Map<String, Object>) item.get("product");
                Product product = store.getInventory().getProductByID(((Integer) productMap.get("productID")).longValue());
                return new OrderItem(product, ((Integer) item.get("quantity")));
            })
            .collect(Collectors.toList());
            Order order = new Order(
                orderDate,
                orderItems,
                orderDTO.getTotalAmount(),
                orderDTO.getPaid(),
                orderDTO.getCustomerInfo()
            );
            store.updateOrder(id, order, orderDTO.getPaymentMethod(), orderDTO.getFullName());
            return ResponseEntity.ok().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Lỗi khi phân tích ngày: " + e.getMessage());
        }
    }
    @DeleteMapping("/orders/{id}") // Remove an order
    public boolean removeOrder(@PathVariable long id) {
        return store.removeOrder(id);
    }















    // Invoice Management APIs
    @GetMapping("/invoices") // Get all invoices
    public List<Invoice> getAllInvoices() {
        return store.getInvoiceList();
    }

    @GetMapping("/invoices/order/{id}") // Add a new invoice
    public ResponseEntity<?> getInvoiceByOrderID(@PathVariable long id) {
        try {
            for (Invoice invoice : store.getInvoiceList()) {
                if (invoice.getOrder().getOrderID() == id) {
                    return ResponseEntity.ok(invoice);
                }
            }
            return ResponseEntity.badRequest()
                .body("Không tìm thấy hóa đơn cho đơn hàng với ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Lỗi khi lấy hóa đơn: " + e.getMessage());
        }
    }

    @PostMapping("/invoices/sort") // Sort invoices
    public List<Invoice> sortInvoices(@RequestBody SortInvoiceDTO sortDTO) {
        System.out.println("Received sortDTO: " + sortDTO.toString());
        return store.sortInvoices(store.getInvoiceList(), sortDTO);
    }

    @PostMapping("/invoices/filter") // Filter invoices
    public List<Invoice> filterInvoices(@RequestBody FilterInvoiceDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        return store.filterInvoices(filterDTO, store.getInvoiceList());
    }











    // Expense Management APIs
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return store.getExpenseList();
    }

    @PostMapping("/expenses")
    public void addExpense(@RequestBody ExpenseDTO expenseDTO) throws ParseException {
        System.out.println("Received expenseDTO: " + expenseDTO.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(expenseDTO.getDate());
        Expense newExpense = new Expense(
                expenseDTO.getExpenseType(),
                expenseDTO.getAmount(),
                date,
                expenseDTO.getDescription()
        );
        store.addExpense(newExpense);
    }

    @PostMapping("/expenses/{id}")
    public void updateExpense(@PathVariable long id, @RequestBody ExpenseDTO expenseDTO) throws ParseException {
        System.out.println("Received expenseDTO: " + expenseDTO.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(expenseDTO.getDate());
        Expense updateExpense = new Expense(
                expenseDTO.getExpenseType(),
                expenseDTO.getAmount(),
                date,
                expenseDTO.getDescription()
        );
        store.updateExpense(id, updateExpense);
    }

    @DeleteMapping("/expenses/{id}")
    public void removeExpense(@PathVariable long id) {
        store.removeExpense(id);
    }



    // Financial Reports APIs
    @PostMapping("/finances/totalexpense")
    public double getTotalExpense(@RequestBody  FilterFinanceDTO filterDTO) throws ParseException {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        String startDateStr = filterDTO.getStartDate();
        String endDateStr = filterDTO.getEndDate();
        
        // Xử lý cả null và chuỗi rỗng
        boolean isStartDateEmpty = startDateStr == null || startDateStr.isEmpty();
        boolean isEndDateEmpty = endDateStr == null || endDateStr.isEmpty();
        
        if (isStartDateEmpty && isEndDateEmpty) {
            return store.getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(null, null);
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        if (isStartDateEmpty && !isEndDateEmpty) {
            Date endDate = dateFormat.parse(endDateStr);
            return store.getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(null, endDate);
        }
        
        if (!isStartDateEmpty && isEndDateEmpty) {
            Date startDate = dateFormat.parse(startDateStr);
            return store.getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(startDate, null);
        }
        
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);
        return store.getExpenseAndProductPurchasePriceAndEmployeeSalaryInPeriod(startDate, endDate);
    }

    @PostMapping("/finances/revenue")
    public double getRevenue(@RequestBody FilterFinanceDTO filterDTO) throws ParseException {
        String startDateStr = filterDTO.getStartDate();
        String endDateStr = filterDTO.getEndDate();
        
        // Xử lý cả null và chuỗi rỗng
        boolean isStartDateEmpty = startDateStr == null || startDateStr.isEmpty();
        boolean isEndDateEmpty = endDateStr == null || endDateStr.isEmpty();
        
        if (isStartDateEmpty && isEndDateEmpty) {
            return store.getRevenueInPeriod(null, null);
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        if (isStartDateEmpty && !isEndDateEmpty) {
            Date endDate = dateFormat.parse(endDateStr);
            return store.getRevenueInPeriod(null, endDate);
        }
        
        if (!isStartDateEmpty && isEndDateEmpty) {
            Date startDate = dateFormat.parse(startDateStr);
            return store.getRevenueInPeriod(startDate, null);
        }
        
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);
        return store.getRevenueInPeriod(startDate, endDate);
    }

    @PostMapping("/finances/profit")
    public double getProfit(@RequestBody FilterFinanceDTO filterDTO) throws ParseException {
        String startDateStr = filterDTO.getStartDate();
        String endDateStr = filterDTO.getEndDate();
        
        // Xử lý cả null và chuỗi rỗng
        boolean isStartDateEmpty = startDateStr == null || startDateStr.isEmpty();
        boolean isEndDateEmpty = endDateStr == null || endDateStr.isEmpty();
        
        if (isStartDateEmpty && isEndDateEmpty) {
            return store.getProfitInPeriod(null, null);
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        if (isStartDateEmpty && !isEndDateEmpty) {
            Date endDate = dateFormat.parse(endDateStr);
            return store.getProfitInPeriod(null, endDate);
        }
        
        if (!isStartDateEmpty && isEndDateEmpty) {
            Date startDate = dateFormat.parse(startDateStr);
            return store.getProfitInPeriod(startDate, null);
        }
        
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);
        return store.getProfitInPeriod(startDate, endDate);
    }

}
