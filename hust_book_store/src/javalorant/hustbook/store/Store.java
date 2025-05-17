package javalorant.hustbook.store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javalorant.hustbook.expense.Expense;
import javalorant.hustbook.inventory.Inventory;
import javalorant.hustbook.invoice.Invoice;
import javalorant.hustbook.order.Order;
import javalorant.hustbook.order.OrderItem;
import javalorant.hustbook.product.Book;
import javalorant.hustbook.product.Product;
import javalorant.hustbook.product.Stationary;
import javalorant.hustbook.product.Toy;
import javalorant.hustbook.user.CustomerInfo;
import javalorant.hustbook.user.User;
import javalorant.hustbook.user.UserRole;

public class Store {
    private String storeName;
    private Inventory inventory = new Inventory();
    private List<Order> orderList = new ArrayList<>();
    private List<CustomerInfo> customerList = new ArrayList<>();
    private List<Invoice> invoiceList = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    
    private int nextUserID = 1;
    private int nextBookID = 1;
    private int nextToyID = 1;
    private int nextStationaryID = 1;
    private int nextOrderID = 1;
    private int nextCustomerID = 1;
    
    public Store(String storeName) {
        this.storeName = storeName;
    }

    // Quản lý người dùng
    public void addUser(String username, String password, UserRole role) {
    	String userID = String.format("U%03d", nextUserID++);
    	User newUser = new User(userID, username, password, role);
    	userList.add(newUser);
    }
    
    public void addUser(User user) {
        userList.add(user);
        try {
        	int idNum = Integer.parseInt(user.getUserID().substring(1));
        	if (idNum >= nextUserID) {
        		nextUserID = idNum + 1;
        	}
        } catch (NumberFormatException ignored) {}
    }
    
    public boolean removeUser(String userID) {
    	boolean removed = userList.removeIf(u -> u.getUserID().equals(userID));
    	if (removed) {
    		try {
    			int removedIdNum = Integer.parseInt(userID.substring(1));
    			if (removedIdNum < nextUserID - 1) {
    				return true;
    			}
    			
    			int maxID = userList.stream().mapToInt(u -> {
    				try {
    					return Integer.parseInt(u.getUserID().substring(1));
    				} catch (NumberFormatException e) {
    					return 0;
    				}
    			}).max().orElse(0);
    			
    			nextUserID = maxID + 1;
    		} catch (NumberFormatException ignored) {}
    	}
    	return removed;
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
    	String id = product.getProductID().toUpperCase();
    	char typeChar = id.charAt(0);
    	int numID = Integer.parseInt(id.substring(1));
    	
    	switch(typeChar) {
    		case 'B':
    			if (numID >= nextBookID) {
    				nextBookID = numID + 1;
    			}
    			break;
    		case 'T':
    			if (numID >= nextToyID) {
    				nextToyID = numID + 1;
    			}
    			break;
    		case 'S':
    			if (numID >= nextStationaryID) {
    				nextStationaryID = numID + 1;
    			}
    			break;
    		default:
    			System.out.println("Invalid!");
    	}
        inventory.addProduct(product);
    }
    
    public void addProduct(String name, int quantity, double purchasePrice, double sellingPrice, String productType, Object...additionalParams) {
    	String productID;
    	Product product;
    	switch(productType.toLowerCase()) {
    		case "book":
    			productID = String.format("B%03d", nextBookID++);
    			product = new Book(productID, name, quantity, purchasePrice, sellingPrice, (String)additionalParams[0], (String)additionalParams[1], (String)additionalParams[2]);
    			break;
    		case "toy":
    			productID = String.format("T%03d", nextToyID++);
    			product = new Toy(productID, name, quantity, purchasePrice, sellingPrice, (String)additionalParams[0], (Integer)additionalParams[1]);
    			break;
    		case "stationary":
    			productID = String.format("S%03d", nextStationaryID++);
    			product = new Stationary(productID, name, quantity, purchasePrice, sellingPrice, (String)additionalParams[0], (String)additionalParams[1]);
    			break;
    		default:
    			throw new IllegalArgumentException("Invalid product type");
    	}
    	
    	inventory.addProduct(product);
    	
    }

    public boolean removeProduct(String productID) {
        return inventory.removeProduct(productID);
    }

    // Quản lý đơn hàng
    public Order createOrder(String orderID, LocalDate orderDate, CustomerInfo info) {
    	if (!customerList.contains(info)) {
    		customerList.add(info);
    		syncNextCustomerID();
    	}
    	int orderNumber = Integer.parseInt(orderID.substring(1));
    	if (orderNumber >= nextOrderID) {
    		nextOrderID = orderNumber + 1;
    	}
    	
    	Order order = new Order(orderID, orderDate, info);
        orderList.add(order);
        return order;
    }
    
    public Order createOrder(LocalDate orderDate, CustomerInfo info) {
    	String orderID = String.format("O%03d", nextOrderID++);
    	Order order = new Order(orderID, orderDate, info);
    	orderList.add(order);
    	return order;
    }
    
    private void syncNextCustomerID() {
        int maxID = customerList.stream()
            .map(c -> c.getCustomerID().substring(1)) // Bỏ "C"
            .mapToInt(Integer::parseInt)
            .max()
            .orElse(0); // Nếu không có khách hàng, bắt đầu từ 1
        nextCustomerID = maxID + 1;
    }
    
    public CustomerInfo findOrCreateCustomerInfo(String name, String contactInfo) {
    	syncNextCustomerID();
    	
    	for (CustomerInfo c : customerList) {
    		if (c.getName().equalsIgnoreCase(name) && c.getContactInfo().equalsIgnoreCase(contactInfo)) {
    			return c;
    		}
    	}
    	String customerID = String.format("C%03d", nextCustomerID++);
    	CustomerInfo newCustomer = new CustomerInfo(customerID, name, contactInfo);
    	customerList.add(newCustomer);
    	return newCustomer;
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
    
    public int getNextUserID() {
    	return nextUserID;
    }
    
    public int getNextBookID() {
    	return nextBookID;
    }
    
    public int getNextToyID() {
    	return nextToyID;
    }
    
    public int getNextStationaryID() {
    	return nextStationaryID;
    }
}
