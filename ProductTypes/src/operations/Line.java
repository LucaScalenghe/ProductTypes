package operations;

public class Line {

	ProductType productType;
	int qty;
	public Line(ProductType productType, int qty) {
		super();
		this.productType = productType;
		this.qty = qty;
	}
	public ProductType getproductType() {
		return productType;
	}
	public int getQty() {
		return qty;
	}

}
