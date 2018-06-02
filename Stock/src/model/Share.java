package model;

public class Share {

	String userId;
	String symbol;
	int quantity;
	int shid; // primary key in dbms
	
	public Share(String userId, String symbol, int quantity, int shid) {
		//super();
		this.userId = userId;
		this.symbol = symbol;
		this.quantity = quantity;
		this.shid = shid;
	}

	public String getUserId() {
		return userId;
	}

	//public void setUserId(String userId) {
		//this.userId = userId;
	//}

	public String getSymbol() {
		return symbol;
	}

	//public void setSymbol(String symbol) {
		//this.symbol = symbol;
	//}

	public int getQuantity() {
		return quantity;
	}

	//public void setQuantity(int quantity) {
		//this.quantity = quantity;
	//}
	
	public int getshid() {
		return this.shid;
	}
	
	public void setShid(int shid) {
		this.shid = shid;
	}
	
	public void increaseQuantity(int amount){
		quantity += amount;
	}
	
	public void decreaseQuantity(int amount){
		quantity -= amount;
	}

}
