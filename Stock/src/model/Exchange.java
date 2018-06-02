package model;

public class Exchange {
	
	String symbol;
	String sellPrice;
	String buyPrice;
	
	String type; //GTC, IOC, MPO, ...
	String sellerId;
	String buyerId;

	int quantity;
	int eid; // id in db
	
	int sellRef;
	int buyRef;
	long exchangeDate ;
	
	public Exchange(String symbol, String sellPrice, String buyPrice, String type, String sellerId, String buyerId, int quantity, int eid, int sellRef, int buyRef, long exchangeDate) {
		
		this.symbol = symbol;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.type = type;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.quantity = quantity;
		this.eid = eid;
		this.sellRef = sellRef;
		this.buyRef = buyRef;
		this.exchangeDate = exchangeDate;
	}
	
	public long getExchangeDate() {
		return exchangeDate;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	//public void setSellPrice(String sellPrice) {
	//	this.sellPrice = sellPrice;
	//}

	public String getBuyPrice() {
		return buyPrice;
	}

	//public void setBuyPrice(String buyPrice) {
	//	this.buyPrice = buyPrice;
	//}

	public String getSymbol() {
		return symbol;
	}

	//public void setSymbol(String symbol) {
	//	this.symbol = symbol;
	//}

	public String getType() {
		return type;
	}

	//public void setType(String type) {
	//	this.type = type;
	//}

	public String getSellerId() {
		return sellerId;
	}

	//public void setSellerId(String sellerId) {
	//	this.sellerId = sellerId;
	//}

	public String getBuyerId() {
		return buyerId;
	}

	//public void setBuyerId(String buyerId) {
	//	this.buyerId = buyerId;
	//}

	public int getQuantity() {
		return quantity;
	}

	//public void setQuantity(int quantity) {
	//	this.quantity = quantity;
	//}

	public int getEid() {
		return eid;
	}

	public int getSellRef() {
		return sellRef;
	}

	public int getBuyRef() {
		return buyRef;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	//public void setEid(int eid) {
	//	this.eid = eid;
	//}
	
	
}
