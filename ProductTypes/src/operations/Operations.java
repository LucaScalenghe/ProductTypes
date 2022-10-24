package operations;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Operations {

//R1
	Map<String,ProductType> producttypes = new HashMap<>();
	Map<String,Customer> customers = new HashMap<>();
	List<Order> orders = new LinkedList<>();
	
	
	
	public int addProductType(String productType, int n, int price) throws OException {
		if(producttypes.containsKey(productType)) throw new OException("");
		ProductType p = new ProductType(productType, n, price);
		producttypes.put(productType, p);
		return n*price;
	}

	public int getNumberOfProducts(String productType) throws OException {
		if(!producttypes.containsKey(productType)) throw new OException("");
		
		return producttypes.get(productType).getNumber();
	}

	public SortedMap<Integer, List<String>> groupingProductTypesByPrices() {
		return producttypes.values().stream().sorted(Comparator.comparing(ProductType :: getName))
				.collect(Collectors.groupingBy(x-> x.getPrice(), () -> new TreeMap<>(), Collectors.mapping(x->x.getName(), Collectors.toList())));
	}

//R2
	public int addDiscount(String customer, int discount) {
		if(customers.containsKey(customer)) {
		customers.get(customer).addDiscount(discount);
		}else {
		Customer c = new Customer(customer);
		c.addDiscount(discount);
		customers.put(customer, c);
		}
		return customers.get(customer).getTotDiscount();
	}

	public int customerOrder(String customer, String ptpn, int discount) throws OException {
		/*1) If not all the number of products requested are available the result is 0; the customer doesn't buy anything.
		 * 
		 * 2) If the requested discount exceeds the available discount for the customer, an exception is thrown.
		*/
		
		//IN QUESTO METODO NON HO AGGIUNTO L'ORDER AL CUSTOMER ???????????????????????????????????????????
		
		String[] s = ptpn.split(" ");
		Map<String, Integer> prods = new HashMap<>();
		List<String> control = new LinkedList<>();
		
		if(customers.get(customer).getTotDiscount() < discount) throw new OException("");

		Order o = new Order(customers.get(customer), discount);
	
		for(String x : s ){
			
			String[] q = x.split(":");
			String name = q[0];
			int qty = Integer.parseInt(q[1]);
			
			if(!producttypes.containsKey(name) || producttypes.get(name).getNumber() < qty) return 0;
			//adding product and qty to order
			o.addProductBought(producttypes.get(name), qty);
			
			//decrementing from product types the quantity
			producttypes.get(name).decrementQuantity(qty);
			
			//adding the product type to customer 
			customers.get(customer).addProductType(producttypes.get(name), qty);
		}
		//decrement the discount
		customers.get(customer).decrementDiscount(discount);
		orders.add(o);
		
		
		int tot =0;
		for(Line l : o.getLines()) {
			tot = tot + l.getQty() * l.getproductType().getPrice();
		}
		
		//Adding the order to customer
		customers.get(customer).addOrder(o);
		
		return tot - discount;
	}

	public int getDiscountAvailable(String customer) {
		// The result is the difference between the total discount received by the
		// customer and the sum of the discounts already used.
		// The result is 0 if there is no discount that can be used.
		return  customers.get(customer).getTotDiscount();
	}

//R3
	public int evalByCustomer(String customer, String productType, int score) throws OException {
		// An exception is thrown if the customer:
		// has not purchased any product of the type indicated,
		// has already given a score to the product type indicated, or the score is out
		// of range.
		
		Customer cust = customers.get(customer);
		if(!cust.checkProductType(productType)) throw new OException("");
		if(score < 4 || score > 10) throw new OException("");
		
		if(cust.checkScore(productType)) throw new OException("");
		cust.addScore(productType, score);
		
		//HERE I DONT ADD THE SCORE TO THE PRODUCT ONLY TO THE CUSTOMER ????????????????
		
		
		return cust.getScoreCount();
	}

	public int getScoreFromProductType(String customer, String productType) throws OException {
		if(!customers.get(customer).checkIfScoreGiven(productType)) throw new OException("");
		return customers.get(customer).getScoreForProductType(productType);
	}

	public SortedMap<Integer, List<String>> groupingCustomersByScores(String productType) {
		return customers.values().stream().filter(x->x.checkIfScoreGiven(productType))
				.sorted(Comparator.comparing(Customer :: getName))
				.collect(Collectors.groupingBy(x -> x.getScoreForProductType(productType), () -> new TreeMap<>(), Collectors.mapping(x->x.getName(), Collectors.toList())));
	}

//R4
	public SortedMap<Integer, List<String>> groupingCustomersByNumberOfProductsPurchased() {
		//groups the customers by increasing number of products purchased. 
		//The customers are listed in alphabetical order.
		
		
		return customers.values().stream().filter(x->x.getNumberOfProductsPurchased() >0)
				.sorted(Comparator.comparing(Customer :: getName))
				.collect(Collectors.groupingBy(Customer :: getNumberOfProductsPurchased, () -> new TreeMap<>(),
						Collectors.mapping(Customer :: getName, Collectors.toList())
						));
	}

	public SortedMap<String, Integer> largestExpenseForCustomer() {
		//provides the largest expense for each customer (in increasing order)
		
		SortedMap<String, Integer> m = new TreeMap<>();
		for(Customer c : customers.values()) {
			if(c.getNumberOfOrders() > 0) {
			m.put(c.getName(), c.getMaxOrder());
			}
		}
		return m;
	}

}
