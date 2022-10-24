package operations;

import java.util.LinkedList;
import java.util.List;

public class Order {
	
	Customer customer;
	int discount;
	List<Line> lines = new LinkedList<>();
	
	public Order(Customer customer, int discount) {
		super();
		this.customer = customer;
		this.discount = discount;
	}
	public Customer getCustomer() {
		return customer;
	}
	public int getDiscount() {
		return discount;
	}
	
	public void addProductBought(ProductType productType, int qty) {
		Line l = new Line(productType, qty);
		lines.add(l);
	}
	public List<Line> getLines() {
		return lines;
	}
	
	

}
