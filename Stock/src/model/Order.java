package model;

import logic.Constants;

public class Order {
	/*
	public final static int DECLINED_STATUS = -1;
	public final static int APPROVED_STATUS = 1 ;
	public final static int LIMITED_STATUS = -2 ;
	public final static int ACTIVE_STATUS = 0 ;
	*/
	public static int cnt = 0;
	
	String symbol;
	String price;
	String type; //GTC, IOC, MPO, ...
	String userId;

	int remainingQuantity; //quantity left
	int initQuantity; //quantity at first
	String operation; // buy or sell
	int oid; // id in db
	int status;
	
	long submitDate ;

	public Order(String symbol, String price, String type, String userId, int initQuantity, String operation, int oid, long submitDate, int remaininQuantity) {

		this.symbol = symbol;
		this.price = price;
		this.type = type;
		this.userId = userId;
		this.initQuantity = initQuantity;
		this.remainingQuantity = remaininQuantity;
		this.operation = operation;
		this.oid = oid;
		this.status = Constants.ORDER_ACTIVE_STATUS; //default status is active
		this.submitDate = submitDate;
		
		cnt++;
	}
	
	public long getSubmitDate() {
		return submitDate;
	}

	public String getSymbol() {
		return symbol;
	}
	
	//public void setSymbol(String symbol) {
	//	this.symbol = symbol;
	//}
	
	public String getPrice() {
		return price;
	}
	
	//public void setPrice(String price) {
	//	this.price = price;
	//}
	
	public String getType() {
		return type;
	}
	
	//public void setType(String type) {
	//	this.type = type;
	//}
	
	public String getUserId() {
		return userId;
	}
	
	//public void setUserId(String userId) {
	//	this.userId = userId;
	//}
	
	public int getRemainingQuantity() {
		return remainingQuantity;
	}
	
	//public void setRemainingQuantity(int remainingQuantity) {
	//	this.remainingQuantity = remainingQuantity;
	//}
	
	public int getInitQuantity() {
		return initQuantity;
	}
	
	//public void setInitQuantity(int initQuantity) {
	//	this.initQuantity = initQuantity;
	//}
	
	public String getOperation() {
		return operation;
	}
	
	//public void setOperation(String operation) {
	//	this.operation = operation;
	//}
	
	public int getOid() {
		return oid;
	}
	
	//public void setOid(int oid) {
	//	this.oid = oid;
	//}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void declineOrder() {
		status = Constants.ORDER_DECLINED_STATUS;
	}
	
	public void approveOrder() {
		status = Constants.ORDER_APPROVED_STATUS;
	}
	
	public void limitOrder(){
		status = Constants.ORDER_LIMITED_STATUS;
	}
	
	public void activateOrder(){
		status = Constants.ORDER_ACTIVE_STATUS;
	}
	
	public boolean isLimited(){
		return (status == Constants.ORDER_LIMITED_STATUS) ;
	}
	
	public void decreaseRemainingQuantity(int amount) {
		this.remainingQuantity -= amount;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}
	
	public boolean isSell(){
		return (operation.equals(Constants.ORDER_SELL)) ;
	}
	
	public boolean isBuy(){
		return (operation.equals(Constants.ORDER_BUY)) ;
	}
	
}
