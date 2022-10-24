package operations;

public class ProductType {

	String name;
	int number = 0;
	int price;
	
	public ProductType(String name, int number, int price) {
		super();
		this.name = name;
		this.number = number;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public int getNumber() {
		return number;
	}
	public int getPrice() {
		return price;
	}
	public void decrementQuantity(int qty) {
		number = number - qty;
	}

}
