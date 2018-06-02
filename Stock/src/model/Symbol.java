package model;

import logic.Constants;

public class Symbol {
	/*
	static final int PENDING_STATUS = 0 ;
	static final int VERIFIED_STSTUS = 1;
	*/
	int sid;
	String name;
	String company;
	int total;
	int status ;
	
	public Symbol(int sid, String name, String company, int total, int status) {
		this.sid = sid;
		this.name = name;
		this.company = company;
		this.total = total;
		this.status = status;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public String getCompany() {
		return company;
	}

	public int getTotal() {
		return total;
	}

	public int getStatus() {
		return status;
	}
	
	public void verify(){
		status = Constants.SYMBOL_VERIFIED_STSTUS;
	}
	
	public void charge(int amount){
		total += amount;
	}
	
	public boolean isVerified(){
		return (status == Constants.SYMBOL_VERIFIED_STSTUS) ;
	}
	
}
