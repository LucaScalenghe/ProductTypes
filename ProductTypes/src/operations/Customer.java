package operations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Customer {

	String name;
	List<Integer> discounts = new LinkedList<>();
	int totDiscount =0;
	
	//adding Line bought
	List<Line> lines = new LinkedList<>();
	
	List<Order> orders = new LinkedList<>();
	
	//adding the scores given
	Map<String, Integer> scores = new HashMap<>();
	int totScore =0;

	public Customer(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addDiscount(int discount) {
		discounts.add(discount);
		totDiscount +=discount;
	}

	public List<Integer> getDiscountsList() {
		return discounts;
	}

	public int getTotDiscount() {
		return totDiscount;
	}

	public void decrementDiscount(int d) {

		totDiscount = totDiscount - d;
	}

	public void addProductType(ProductType productType, int qty) {
		Line l = new Line(productType, qty);
		lines.add(l);
	}

	public void addOrder(Order o) {
		orders.add(o);
	}

	public boolean checkProductType(String productType) {

		//se ce torna true
		if(lines.stream().anyMatch(x->x.getproductType().getName() == productType)) return true;
		return false;
	}

	public boolean checkScore(String productType) {
		if(scores.containsKey(productType)) return true;
		return false;
	}

	public void addScore(String productType, int score) {
		if(!scores.containsKey(productType)) {
			scores.put(productType, score);
			totScore += score;
		}
		
	}

	public List<Integer> getDiscounts() {
		return discounts;
	}

	public List<Line> getLines() {
		return lines;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Map<String, Integer> getScores() {
		return scores;
	}

	public int getTotScore() {
		return totScore;
	}
	
	public int getScoreCount() {
		return scores.size();
	}

	public boolean checkIfScoreGiven(String productType) {
		//se lha dato torna true
		if(scores.containsKey(productType)) return true;
		return false;
	}

	public int getScoreForProductType(String productType) {
		return scores.get(productType);
	}
	

	public int getNumberOfProductsPurchased() {
////		int tot =0;
////		
////		for(Line l : lines) {
////			tot += l.get
////		}
//		// return tot;
//		return lines.size();
		int tot =0;
		
		for(Line l : lines) {
		tot += l.getQty();
		}
		return tot;
	}
	
	public int getMaxOrder() {
		List<Integer> m = new LinkedList<>();
		for(Order o : orders) {
			int totOrder =0;
			for(Line l : o.getLines()) {
				totOrder += l.getproductType().getPrice() * l.getQty();
			}
			totOrder = totOrder - o.getDiscount();
			m.add(totOrder);
		}
		int max =0;
		for(int q : m) {
			if(q>=max) 
			{
				max = q;
			}
			
		}
		return max;
	}

	public int getNumberOfOrders() {
		return orders.size();
	}
	
}
