package model;

import logic.Constants;

public class CreditRequest {
	/*
	final static int ACCEPTED_STATUS = 1;
	final static int PENDING_STATUS = 0;
	final static int REJECTED_STATUS = -1;
	
	static final int DEPOSIT_TYPE = 1;
	static final int WITHDRAW_TYPE = -1;
	*/
	String userId;
	int status;
	int amount;
	int type;
	int crid;
	
	public CreditRequest(String userId, int status, int amount, int type, int crid) {
		this.userId = userId;
		this.status = status;
		this.amount = amount;
		this.type = type;
		this.crid = crid;
	}

	public String getUserId() {
		return userId;
	}

	public int getStatus() {
		return status;
	}

	public int getAmount() {
		return amount;
	}

	public int getType() {
		return type;
	}

	public int getCrid() {
		return crid;
	}
	
	public void setCrid(int crid) {
		this.crid = crid;
	}

	public void accept(){
		this.status = Constants.CREDIT_ACCEPTED_STATUS;
	}
	
	public void reject(){
		this.status = Constants.CREDIT_REJECTED_STATUS;
	}
	
	public boolean isDeposit(){
		return (type == Constants.CREDIT_DEPOSIT_TYPE);
	}
	
	public boolean isPending(){
		return (status == Constants.CREDIT_PENDING_STATUS);
	}
}
