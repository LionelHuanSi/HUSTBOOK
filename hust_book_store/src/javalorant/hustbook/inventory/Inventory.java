package javalorant.hustbook.inventory;

import java.util.ArrayList;
import java.util.List;

import javalorant.hustbook.product.Product;

public class Inventory {
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public boolean removeProduct(String productID) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID().equals(productID)) {
                productList.remove(i);
                return true;
            }
        }
        return false;
    }


    public boolean updateQuantity(String productID, int newQuantity) {
        for (Product p : productList) {
            if (p.getProductID().equals(productID)) {
                p.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }
     public boolean updatePrice(String productID, double purchasePrice, double sellingPrice) {
    	 Product product = getProductByID(productID);
    	 if (product == null) {
    		 return false;
    	 }
    	 product.setPurchasePrice(purchasePrice);
    	 product.setSellingPrice(sellingPrice);
    	 return true;
     }

    public Product getProductByID(String productID) {
        for (Product p : productList) {
            if (p.getProductID().equals(productID)) {
                return p;
            }
        }
        return null;
    }

    // Trả về danh sách sản phẩm trực tiếp
    public List<Product> getAllProducts() {
        return productList;
    }

    // Tính tổng giá trị tồn kho
    public double getTotalInventoryValue() {
        double total = 0;
        for (Product product : productList) {
            total += product.getPurchasePrice() * product.getQuantity();
        }
        return total;
    }
}
