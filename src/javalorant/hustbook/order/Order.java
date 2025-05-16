package javalorant.hustbook.order;

import java.time.LocalDate;
import java.util.List;

import javalorant.hustbook.user.CustomerInfo;

public class Order {
    private String orderID;
    private LocalDate orderDate;
    private double totalAmount;
    private boolean isPaid;
    private CustomerInfo customerinfo;
    private List<OrderItem> items;

    public Order(String orderID, LocalDate orderDate, CustomerInfo customerinfo){
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerinfo = customerinfo;
    }

    public void addItemm(Product product, int quantity){
        items.add(new OrderItem(product, quantity));
        calculateTotal();
    }

    public double calculateTotal(){
        for(Product product : items){
            totalAmount += product.getUnitProfit() * quantity;
        }
        return totalAmount;
    }
}
