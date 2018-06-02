package model;

public class Customer {
	
	String id, pass, name, family, email ;
	int credit ;
	int cid ;
	//int cid; //used as database index

	public Customer(String id, String pass, String name, String family, String email, int credit, int cid) {
		//super();
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.family = family;
		this.email = email;
		this.credit = credit;
		this.cid = cid;
	}
	
	public String getPass() {
		return pass;
	}

	public String getEmail() {
		return email;
	}

	public void deposit(int amount){
		credit += amount;
	}
	
	public void withdraw(int amount) {
		credit -= amount;
	}

	public String getId() {
		return id;
	}

	//public void setId(String id) {
	//	this.id = id;
	//}

	public String getName() {
		return name;
	}

	//public void setName(String name) {
	//	this.name = name;
	//}

	public String getFamily() {
		return family;
	}

	//public void setFamily(String family) {
	//	this.family = family;
	//}

	public int getCredit() {
		return credit;
	}

	//public void setCredit(int credit) {
	//	this.credit = credit;
	//}
	
	//public boolean isAdmin(){
	//	return (id.equals("1")) ;
	//}

	//public void decreaseCredit(int amount) {
	//	// TODO Auto-generated method stub
	//	credit -= amount;
	//}
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

}
