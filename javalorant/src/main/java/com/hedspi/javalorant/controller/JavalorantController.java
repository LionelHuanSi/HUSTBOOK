package com.hedspi.javalorant.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hedspi.javalorant.expense.Expense;
import com.hedspi.javalorant.inventory.Product;
import com.hedspi.javalorant.models.User;
import com.hedspi.javalorant.order.Invoice;
import com.hedspi.javalorant.order.Order;
import com.hedspi.javalorant.store.Store;

@RestController
@RequestMapping("/api/javalorant")
public class JavalorantController {
    public final Store store = new Store("javalorantStore");

    public JavalorantController() {
        store.initializeData();
    }

    // User Management APIs
    @GetMapping("/users")
    public List<User> getAllUsers() {
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

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) {
        return store.getInventory().getProductByID(id);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        store.addProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public boolean removeProduct(@PathVariable String id) {
        return store.removeProduct(id);
    }

    @PutMapping("/products/{id}/quantity")
    public boolean updateProductQuantity(@PathVariable String id, @RequestParam int quantity) {
        return store.getInventory().updateQuantity(id, quantity);
    }

    // Order Management APIs
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return store.getOrderList();
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable String id) {
        return store.getOrder(id);
    }

    @PostMapping("/orders")
    public void createOrder(@RequestParam String orderId, @RequestParam(required = false) Long dateMillis) {
        Date orderDate = dateMillis != null ? new Date(dateMillis) : new Date();
        store.createOrder(orderId, orderDate);
    }

    @PostMapping("/orders/{orderId}/items")
    public void addItemToOrder(
            @PathVariable String orderId,
            @RequestParam String productId,
            @RequestParam int quantity) {
        store.addItemToOrder(orderId, productId, quantity);
    }

    // Invoice Management APIs
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return store.getInvoiceList();
    }

    @PostMapping("/invoices")
    public Invoice generateInvoice(
            @RequestParam String invoiceId,
            @RequestParam String orderId,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) Long dateMillis) {
        Date invoiceDate = dateMillis != null ? new Date(dateMillis) : new Date();
        return store.generateInvoice(invoiceId, invoiceDate, orderId, paymentMethod);
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
