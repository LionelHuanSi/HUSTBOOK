package javalorant.hustbook.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javalorant.hustbook.expense.Expense;
import javalorant.hustbook.inventory.Inventory;
import javalorant.hustbook.invoice.Invoice;
import javalorant.hustbook.order.Order;
import javalorant.hustbook.order.OrderItem;
import javalorant.hustbook.product.Product;
import javalorant.hustbook.user.CustomerInfo;
import javalorant.hustbook.user.User;

public class Store {
    private String storeName;
    private Inventory inventory = new Inventory();
    private List<Order> orderList = new ArrayList<>();
    private List<Invoice> invoiceList = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public Store(String storeName) {
        this.storeName = storeName;
    }

    // Quản lý người dùng
    public void addUser(User user) {
        userList.add(user);
    }

    // Tìm user theo vòng lặp for
    public User getUserByUsername(String username) {
        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    // Quản lý sản phẩm
    public void addProduct(Product product) {
        inventory.addProduct(product);
    }

    public boolean removeProduct(String productID) {
        return inventory.removeProduct(productID);
    }

    // Quản lý đơn hàng
    public Order createOrder(String orderID, LocalDate orderDate, CustomerInfo info) {
        Order order = new Order(orderID, orderDate, info);
        orderList.add(order);
        return order;
    }

    public Order getOrderByID(String orderID) {
        for (Order o : orderList) {
            if (o.getOrderID().equals(orderID)) {
                return o;
            }
        }
        return null;
    }

    public void addItemToOrder(String orderID, String productID, int qty) {
        Order order = getOrderByID(orderID);
        if (order != null) {
            Product p = inventory.getProductByID(productID);
            if (p != null) {
                order.addItem(p, qty);
            }
        }
    }

    // Quản lý hóa đơn
    public Invoice generateInvoice(String invoiceID, LocalDate invoiceDate, String orderID, String paymentMethod) {
        Order order = getOrderByID(orderID);
        if (order == null) return null;

        // Đánh dấu đơn hàng đã thanh toán
        order.setPaid(true);

        // Cập nhật tồn kho
        for (OrderItem item : order.getItems()) {
            Product prod = item.getProduct();
            int qtyOrdered = item.getQuantity();
            Product inStock = inventory.getProductByID(prod.getProductID());
            if (inStock != null) {
                int updatedQty = inStock.getQuantity() - qtyOrdered;
                inStock.setQuantity(Math.max(updatedQty, 0));
            }
        }

        Invoice inv = new Invoice(invoiceID, invoiceDate, order, paymentMethod);
        invoiceList.add(inv);
        return inv;
    }

    // Quản lý chi phí
    public void addExpense(Expense exp) {
        expenseList.add(exp);
    }

    // Tính tổng chi phí bằng vòng lặp for
    public double getTotalExpenses() {
        double sum = 0;
        for (Expense e : expenseList) {
            sum += e.getAmount();
        }
        return sum;
    }

    // Thống kê doanh thu trong khoảng thời gian
    public double getRevenueInPeriod(LocalDate from, LocalDate to) {
        double sum = 0;
        for (Invoice i : invoiceList) {
            if (!i.getInvoiceDate().isBefore(from) && !i.getInvoiceDate().isAfter(to)) {
                sum += i.getTotalAmount();
            }
        }
        return sum;
    }

    // Thống kê chi phí trong khoảng thời gian
    public double getExpensesInPeriod(LocalDate from, LocalDate to) {
        double sum = 0;
        for (Expense e : expenseList) {
            if (!e.getDate().isBefore(from) && !e.getDate().isAfter(to)) {
                sum += e.getAmount();
            }
        }
        return sum;
    }

    // Getters đơn giản
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
