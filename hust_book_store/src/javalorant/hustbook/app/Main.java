package javalorant.hustbook.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javalorant.hustbook.expense.Expense;
import javalorant.hustbook.invoice.Invoice;
import javalorant.hustbook.order.Order;
import javalorant.hustbook.order.OrderItem;
import javalorant.hustbook.product.Book;
import javalorant.hustbook.product.Stationary;
import javalorant.hustbook.product.Toy;
import javalorant.hustbook.store.Store;
import javalorant.hustbook.user.CustomerInfo;
import javalorant.hustbook.user.User;
import javalorant.hustbook.user.UserRole;

public class Main {
    private static Store store = new Store("HUSTBookStore");
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        seedData();
        performLogin();
        mainMenuLoop();
        System.out.println("Goodbye!");
    }


    private static void seedData() {
    	// Users
        store.addUser(new User("U001", "admin", "admin123", UserRole.ADMIN));
        store.addUser(new User("U002", "employee1", "emp123", UserRole.EMPLOYEE));
        store.addUser(new User("U003", "employee2", "emp456", UserRole.EMPLOYEE));

        // Books
        store.addProduct(new Book("B001", "The Art of Programming", 10, 35.0, 45.0,
            "Tech Publications", "Donald Knuth", "978-0201038019"));
        store.addProduct(new Book("B002", "Clean Code", 15, 28.0, 39.99,
            "Prentice Hall", "Robert Martin", "978-0132350884"));
        store.addProduct(new Book("B003", "Design Patterns", 8, 40.0, 54.99,
            "Addison-Wesley", "Gang of Four", "978-0201633610"));

        // Stationary
        store.addProduct(new Stationary("S001", "Premium Notebook", 50, 3.0, 5.99,
            "Moleskine", "Notebook"));
        store.addProduct(new Stationary("S002", "Gel Pen Set", 100, 1.0, 2.49,
            "Pilot", "Pen"));
        store.addProduct(new Stationary("S003", "Color Pencils", 30, 4.0, 7.99,
            "Faber-Castell", "Pencil"));

        // Toys
        store.addProduct(new Toy("T001", "LEGO Classic Set", 20, 15.0, 24.99,
            "LEGO", 5));
        store.addProduct(new Toy("T002", "Rubik's Cube", 40, 5.0, 9.99,
            "Rubik's", 8));
        store.addProduct(new Toy("T003", "Chess Set", 15, 12.0, 19.99,
            "Classic Games", 7));

        // Customers
        CustomerInfo customer1 = new CustomerInfo("C001", "John Doe", "john@email.com");
        CustomerInfo customer2 = new CustomerInfo("C002", "Jane Smith", "jane@email.com");

        // Current date
        LocalDate currentDate = LocalDate.now();

        // Order 1
        Order order1 = store.createOrder("O001", currentDate, customer1);
        store.addItemToOrder("O001", "B001", 2);
        store.addItemToOrder("O001", "S001", 3);
        order1.setPaid(true);
        Invoice invoice1 = store.generateInvoice("INV001", currentDate, "O001", "CASH");
        invoice1.setPaymentMethod("CASH");

        // Order 2
        Order order2 = store.createOrder("O002", currentDate, customer2);
        store.addItemToOrder("O002", "T001", 1);
        store.addItemToOrder("O002", "S002", 5);
        order2.setPaid(true);
        Invoice invoice2 = store.generateInvoice("INV002", currentDate, "O002", "CREDIT_CARD");
        invoice2.setPaymentMethod("CREDIT_CARD");

        // Expenses
        store.addExpense(new Expense("E001", "Rent", 1000.0, currentDate, "Monthly store rent"));
        store.addExpense(new Expense("E002", "Utilities", 200.0, currentDate, "Electricity and water"));
        store.addExpense(new Expense("E003", "Supplies", 150.0, currentDate, "Office supplies"));
        store.addExpense(new Expense("E004", "Marketing", 300.0, currentDate, "Online advertisements"));
    }


    private static void performLogin() {
        System.out.println("=== HUSTBookStore Login ===");
        while (true) {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();
            System.out.print("Password: ");
            String password = sc.nextLine().trim();
            User user = store.getUserByUsername(username);
            if (user != null && user.authenticate(password)) {
                currentUser = user;
                System.out.printf("Welcome, %s! Role: %s%n",user.getUsername(), user.getRole());
                break;
            }
            System.out.println("Invalid credentials. Try again.\n");
        }
    }

    private static void mainMenuLoop() {
        int choice;
        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. User Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Invoice Management");
            System.out.println("5. Expense Management");
            System.out.println("6. Statistics");
            System.out.println("0. Exit");
            System.out.print("Choose [0-6]: ");
            choice = parseIntInput();
            switch (choice) {
                case 1: userManagementMenu();      break;
                case 2: productManagementMenu();   break;
                case 3: orderManagementMenu();     break;
                case 4: invoiceManagementMenu();   break;
                case 5: expenseManagementMenu();   break;
                case 6: statisticsMenu();          break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // 1. User Management (Admin only)
    private static void userManagementMenu() {
        if (currentUser.getRole() != UserRole.ADMIN) {
            System.out.println("Access denied: only ADMIN can manage users.");
            return;
        }
        int choice;
        do {
            System.out.println("\n--- User Management ---");
            System.out.println("1. View Users");
            System.out.println("2. Add User");
            System.out.println("0. Back");
            System.out.print("Choose [0-2]: ");
            choice = parseIntInput();
            switch (choice) {
                case 1:
                    List<User> users = store.getUserList();
                    System.out.println("Users:");
                    for (User u : users) {
                        System.out.printf("- %s (%s) Role: %s%n",u.getUserID(), u.getUsername(), u.getRole());
                    }
                    break;
                case 2:
                    System.out.print("New UserID: ");
                    String uid = sc.nextLine().trim();
                    System.out.print("Username: ");
                    String uname = sc.nextLine().trim();
                    System.out.print("Password: ");
                    String pwd = sc.nextLine().trim();
                    System.out.print("Role [ADMIN/EMPLOYEE]: ");
                    UserRole role = UserRole.valueOf(sc.nextLine().trim().toUpperCase());
                    store.addUser(new User(uid, uname, pwd, role));
                    System.out.println("User added.");
                    break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // 2. Product Management
    private static void productManagementMenu() {
        int choice;
        do {
            System.out.println("\n--- Product Management ---");
            System.out.println("1. View All Products");
            System.out.println("2. Add Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Update Quantity");
            System.out.println("5. View Inventory Value");
            System.out.println("0. Back");
            System.out.print("Choose [0-5]: ");
            choice = parseIntInput();
            switch (choice) {
                case 1:
                    store.getInventory().getAllProducts().forEach(p ->
                        System.out.printf("- %s: %s, qty=%d, buy=%.2f, sell=%.2f, type=%s%n",
                            p.getProductID(), p.getName(), p.getQuantity(),
                            p.getPurchasePrice(), p.getSellingPrice(),
                            p.getProductType()));
                    break;
                case 2:
                    addProductInteractive();
                    break;
                case 3:
                    System.out.print("ProductID to remove: ");
                    if (store.removeProduct(sc.nextLine().trim())) {
                        System.out.println("Removed.");
                    } else {
                        System.out.println("Not found.");
                    }
                    break;
                case 4:
                    System.out.print("ProductID: ");
                    String pid = sc.nextLine().trim();
                    System.out.print("New quantity: ");
                    int q = parseIntInput();
                    if (store.getInventory().updateQuantity(pid, q)) {
                        System.out.println("Updated.");
                    } else {
                        System.out.println("Not found.");
                    }
                    break;
                case 5:
                    System.out.printf("Total Inventory Value: %.2f%n",
                        store.getInventory().getTotalInventoryValue());
                    break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addProductInteractive() {
        System.out.print("Type [BOOK/STATIONARY/TOY]: ");
        String type = sc.nextLine().trim().toUpperCase();
        System.out.print("ProductID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Quantity: ");
        int qty = parseIntInput();
        System.out.print("PurchasePrice: ");
        double pp = Double.parseDouble(sc.nextLine().trim());
        System.out.print("SellingPrice: ");
        double sp = Double.parseDouble(sc.nextLine().trim());
        switch (type) {
            case "BOOK":
                System.out.print("Publisher: ");
                String pub = sc.nextLine().trim();
                System.out.print("Author: ");
                String auth = sc.nextLine().trim();
                System.out.print("ISBN: ");
                String isbn = sc.nextLine().trim();
                store.addProduct(new Book(id, name, qty, pp, sp, pub, auth, isbn));
                break;
            case "STATIONARY":
                System.out.print("Brand: ");
                String brand = sc.nextLine().trim();
                System.out.print("Type: ");
                String stype = sc.nextLine().trim();
                store.addProduct(new Stationary(id, name, qty, pp, sp, brand, stype));
                break;
            case "TOY":
                System.out.print("Brand: ");
                String tbrand = sc.nextLine().trim();
                System.out.print("SuitableAge: ");
                int age = parseIntInput();
                store.addProduct(new Toy(id, name, qty, pp, sp, tbrand, age));
                break;
            default:
                System.out.println("Unknown type.");
        }
        System.out.println("Product added.");
    }

    // 3. Order Management 
    private static void orderManagementMenu() {
        int choice;
        do {
            System.out.println("\n--- Order Management ---");
            System.out.println("1. Create Order");
            System.out.println("2. View Orders");
            System.out.println("0. Back");
            System.out.print("Choose [0-2]: ");
            choice = parseIntInput();
            switch (choice) {
                case 1:
                    System.out.print("OrderID: ");
                    String oid = sc.nextLine().trim();
                    System.out.print("CustomerID: ");
                    String cid = sc.nextLine().trim();
                    System.out.print("Customer Name: ");
                    String cname = sc.nextLine().trim();
                    System.out.print("ContactInfo: ");
                    String cinfo = sc.nextLine().trim();

                    Order order = store.createOrder(oid, LocalDate.now(),
                        new CustomerInfo(cid, cname, cinfo));

                    while (true) {
                        System.out.print("Add item? [Y/N]: ");
                        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) break;
                        System.out.print("ProductID: ");
                        String pid2 = sc.nextLine().trim();
                        System.out.print("Quantity: ");
                        int qty2 = parseIntInput();
                        store.addItemToOrder(oid, pid2, qty2);
                    }

                    order.calculateTotal();
                    System.out.printf("Order total: %.2f%n", order.getTotalAmount());

                    System.out.print("Payment Method (CASH/CARD): ");
                    String payMethod = sc.nextLine().trim();
                    System.out.print("Mark this order as paid? [Y/N]: ");
                    boolean paid = sc.nextLine().trim().equalsIgnoreCase("Y");
                    order.setPaid(paid);

                    String invoiceID = "INV" + oid;
                    Invoice inv = store.generateInvoice(invoiceID, LocalDate.now(), oid, payMethod);
                    if (inv != null) {
                        System.out.println("\n=== Invoice Generated ===");
                        System.out.println(inv.generateInvoiceDetails());
                        System.out.println("Order paid status: " + (paid ? "PAID" : "UNPAID"));
                    } else {
                        System.out.println("Failed to generate invoice.");
                    }
                    break;
                case 2:
                    for (Order o : store.getOrderList()) {
                        System.out.printf("- %s on %s, total=%.2f, items=%d, paid=%s%n",
                            o.getOrderID(), o.getOrderDate(), o.getTotalAmount(),
                            o.getItems().size(), o.isPaid() ? "YES" : "NO");
                    }
                    break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // 4. Invoice Management
    private static void invoiceManagementMenu() {
        int choice;
        do {
            System.out.println("\n--- Invoice Management ---");
            System.out.println("1. View Invoices");
            System.out.println("0. Back");
            System.out.print("Choose [0-1]: ");
            choice = parseIntInput();
            if (choice == 1) {
                for (Invoice i : store.getInvoiceList()) {
                    System.out.printf("- %s on %s, total=%.2f, pay=%s%n",
                        i.getInvoiceID(), i.getInvoiceDate(),
                        i.getTotalAmount(), i.getPaymentMethod());
                }
            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // 5. Expense Management
    private static void expenseManagementMenu() {
        int choice;
        do {
            System.out.println("\n--- Expense Management ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("0. Back");
            System.out.print("Choose [0-2]: ");
            choice = parseIntInput();
            switch (choice) {
                case 1:
                    System.out.print("ExpenseID: ");
                    String eid = sc.nextLine().trim();
                    System.out.print("Type: "); String et = sc.nextLine().trim();
                    System.out.print("Amount: ");
                    double am = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Description: ");
                    String desc = sc.nextLine().trim();
                    store.addExpense(new Expense(eid, et, am, LocalDate.now(), desc));
                    System.out.println("Expense added.");
                    break;
                case 2:
                    for (Expense ex : store.getExpenseList()) {
                        System.out.printf("- %s: %s, amount=%.2f, on %s%n",
                            ex.getExpenseID(), ex.getExpenseType(),
                            ex.getAmount(), ex.getDate());
                    }
                    System.out.printf("Total Expenses: %.2f%n", store.getTotalExpenses());
                    break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // 6. Statistics
    private static void statisticsMenu() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate from = parseDateInput();
        System.out.print("Enter end date   (YYYY-MM-DD): ");
        LocalDate to   = parseDateInput();
        double rev  = store.getRevenueInPeriod(from, to);
        double exp  = store.getExpensesInPeriod(from, to);
        double cogs = calculateCOGS(from, to);
        double profit = rev - exp - cogs;
        System.out.printf("Revenue: %.2f%nExpenses: %.2f%nCOGS: %.2f%nProfit: %.2f%n",rev, exp, cogs, profit);
    }

    // Tính COGS
    private static double calculateCOGS(LocalDate from, LocalDate to) {
        double cogs = 0;
        List<Order> orders = store.getOrderList();
        for (Order o : orders) {
            if (!o.getOrderDate().isBefore(from) && !o.getOrderDate().isAfter(to)) {
                List<OrderItem> items = o.getItems();
                for (OrderItem oi : items) {
                    cogs += oi.getProduct().getPurchasePrice() * oi.getQuantity();
                }
            }
        }
        return cogs;
    }

    private static int parseIntInput() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    private static LocalDate parseDateInput() {
        try { return LocalDate.parse(sc.nextLine().trim()); }
        catch (Exception e) {
            System.out.println("Invalid date, using today.");
            return LocalDate.now();
        }
    }
}
