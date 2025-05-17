package javalorant.hustbook.product;

public abstract class Product {
    private String productID;
    private String name;
    private int quantity;
    private double purchasePrice;
    private double sellingPrice;

    public Product(String productID, String name, int quantity, double purchasePrice, double sellingPrice) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /** Lợi nhuận trên mỗi sản phẩm **/
    public double getUnitProfit() {
        return sellingPrice - purchasePrice;
    }


    public abstract String getProductType();
}