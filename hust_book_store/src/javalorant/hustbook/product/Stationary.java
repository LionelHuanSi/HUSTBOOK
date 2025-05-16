package product;

public class Stationary extends Product {
	private String brand;
	private String stationaryType;
	
	public Stationary(String productID, String name, int quantity, double purchasePrice, double sellingPrice, String brand, String stationaryType) {
		super(productID, name, quantity, purchasePrice, sellingPrice);
		this.brand = brand;
		this.stationaryType = stationaryType;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStationaryType() {
		return stationaryType;
	}

	public void setStationaryType(String stationaryType) {
		this.stationaryType = stationaryType;
	}
	
	public String getProductType() {
		return "Stationary";
	}
}
