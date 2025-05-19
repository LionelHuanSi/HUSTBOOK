package com.hedspi.javalorant.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hedspi.javalorant.dto.FilterDTO;
import com.hedspi.javalorant.dto.FilterOrderDTO;
import com.hedspi.javalorant.dto.OrderDTO;
import com.hedspi.javalorant.dto.ProductDTO;
import com.hedspi.javalorant.dto.SortOrderDTO;
import com.hedspi.javalorant.dto.SortProductsDTO;
import com.hedspi.javalorant.expense.Expense;
import com.hedspi.javalorant.inventory.Book;
import com.hedspi.javalorant.inventory.Product;
import com.hedspi.javalorant.inventory.Stationary;
import com.hedspi.javalorant.inventory.Toy;
import com.hedspi.javalorant.order.Invoice;
import com.hedspi.javalorant.order.Order;
import com.hedspi.javalorant.order.OrderItem;
import com.hedspi.javalorant.store.Store;
import com.hedspi.javalorant.user.User;

@RestController
@RequestMapping("/api")
public class JavalorantController {
    public final Store store = new Store("javalorantStore");

    
    public JavalorantController() {
        store.initializeData();
    }

    // User Management APIs
    @GetMapping("/users")
    public List<User> getAllUsers() {
        System.out.println("Store hashCode: " + store.hashCode());
        System.out.println("UserList hashCode: " + store.getUserList().hashCode());
        return store.getUserList();
    }

    @GetMapping("/users/{username}")
    public User getUser(@PathVariable String username) {
        return store.getUserByUsername(username);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        store.addUser(user);
    }







    // Inventory Management APIs
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return store.getInventory().getAllProducts();
    }

    @PostMapping("/products/filter")
    public List<Product> filterProducts(@RequestBody FilterDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        return store.filterProducts(filterDTO, store.getInventory().getAllProducts());
    }

    @PostMapping("/products/sort")
    public List<Product> sortProducts(@RequestBody SortProductsDTO sortDTO) {
        System.out.println("Received filterDTO: " + sortDTO.toString());
        return store.sortProducts(store.getInventory().getAllProducts(), sortDTO);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            System.out.println("Received productDTO: " + productDTO.toString());
            Product product;
            
            // In ra để debug
            System.out.println("ProductType: " + productDTO.getProductType());
            System.out.println("Name: " + productDTO.getName());
            System.out.println("Selling Price: " + productDTO.getSellingPrice());
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
                }
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

    @DeleteMapping("/products/{id}")
    public boolean removeProduct(@PathVariable long id) {
        return store.removeProduct(id);
    }

    @PostMapping("/products/{id}")
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
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        System.out.println("OrderList: " + store.getOrderList().toString());
        return store.getOrderList();
    }

    @PostMapping("/orders/filter")
    public List<Order> filterOrders(@RequestBody FilterOrderDTO filterDTO) {
        System.out.println("Received filterDTO: " + filterDTO.toString());
        return store.filterOrders(filterDTO, store.getOrderList());
    }

    @PostMapping("/orders/sort")
    public List<Order> sortOrders(@RequestBody SortOrderDTO sortDTO) {
        System.out.println("Received sortDTO: " + sortDTO.toString());
        return store.sortOrders(store.getOrderList(), sortDTO);
    }

    @PostMapping("/orders")
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
            store.addOrder(order);
            return ResponseEntity.ok().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Lỗi khi phân tích ngày: " + e.getMessage());
        }
        
    }


    @PostMapping("/orders/{id}")
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
            store.updateOrder(id, order);
            return ResponseEntity.ok().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Lỗi khi phân tích ngày: " + e.getMessage());
        }
    }

    

    @DeleteMapping("/orders/{id}")
    public boolean removeOrder(@PathVariable long id) {
        return store.removeOrder(id);
    }















    // Invoice Management APIs
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return store.getInvoiceList();
    }

    @PostMapping("/invoices")
    public Invoice generateInvoice(
            @RequestParam long orderId,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) Long dateMillis) {
        Date invoiceDate = dateMillis != null ? new Date(dateMillis) : new Date();
        return store.generateInvoice(invoiceDate, orderId, paymentMethod, null);
    }











    // Expense Management APIs
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return store.getExpenseList();
    }

    @PostMapping("/expenses")
    public void addExpense(@RequestBody Expense expense) {
        store.addExpense(expense);
    }

    // Financial Reports APIs
    @GetMapping("/finances/total-expenses")
    public double getTotalExpenses() {
        return store.getTotalExpenses();
    }

    @GetMapping("/finances/revenue")
    public double getRevenue(
            @RequestParam Long startDate,
            @RequestParam Long endDate) {
        return store.getRevenueInPeriod(new Date(startDate), new Date(endDate));
    }

    @GetMapping("/finances/expenses")
    public double getExpenses(
            @RequestParam Long startDate,
            @RequestParam Long endDate) {
        return store.getExpensesInPeriod(new Date(startDate), new Date(endDate));
    }

    @GetMapping("/finances/cogs")
    public double getCOGS(
            @RequestParam Long startDate,
            @RequestParam Long endDate) {
        return store.getCOGSInPeriod(new Date(startDate), new Date(endDate));
    }

    @GetMapping("/finances/profit")
    public double getProfit(
            @RequestParam Long startDate,
            @RequestParam Long endDate) {
        return store.getProfitInPeriod(new Date(startDate), new Date(endDate));
    }
}
