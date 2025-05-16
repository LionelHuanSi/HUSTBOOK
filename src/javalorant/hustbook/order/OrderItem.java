package javalorant.hustbook.order;

public class OrderItem {
    private Product product;
    private int Quantity;

    public OrderItem(Product product, int Quantity){
        this.product = product;
        this.Quantity = Quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    
}
