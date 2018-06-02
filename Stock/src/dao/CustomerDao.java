package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Customer;

import daoInterface.CustomerDaoInterface;
import exception.RepeatedIdException;
import exception.RepeatedRoleException;
import exception.UnknownUserIdException;

public class CustomerDao implements CustomerDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
//	ArrayList<Customer> customers;
	private static CustomerDao instance;
	
	public CustomerDao() {
//		this.customers = new ArrayList<Customer>();
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static CustomerDaoInterface getInstance(){
		if(instance == null)
			instance = new CustomerDao();
		return instance;
	}

	static {
		try {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
			} catch (ClassNotFoundException ex) {
				System.err.println("Unable to load HSQLDB JDBC driver");
		}
	}
	
	@Override
	public ArrayList<Customer> getAllCustomers() {
		
		String query = "select * from customers" ;
		ResultSet rs = queryExecutor(query);
		
		ArrayList<Customer> allCustomers = convertCustomerResultSetToCustomerArrayList(rs);
		
		return allCustomers;
	
	}

	@Override
	public Customer getCustomerById(String id) throws UnknownUserIdException {
		

		//String query = "select * from customers where uid='" + id + "'" ;
		String query = "select * from customers where uid = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				return new Customer(rs.getString("uid"), rs.getString("pass"), rs.getString("name"), rs.getString("family"), rs.getString("email"), rs.getInt("credit") , rs.getInt("cid"));
			}
			else{
				throw new UnknownUserIdException();
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		System.out.println("update customer called .... credit:" + customer.getCredit());
		
		//String query = "update customers set credit = " + customer.getCredit() + " where uid='" + customer.getId() + "'";
		String query = "update customers set credit = ? where uid = ? ";
		
		try {
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, customer.getCredit());
			pst.setString(2, customer.getId());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//updateExecutor(query);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCustomer(Customer customer) throws RepeatedIdException {
		//try{
		
		try {
			
			getCustomerById(customer.getId());
			throw new RepeatedIdException();
			
		} catch (UnknownUserIdException e1) {
			
			System.out.println("inserting...");
			
			int generatedCid = nextId();
			//String query = "INSERT INTO customers (uid, pass, name, family, email, credit , cid) VALUES ('" + customer.getId() + "', '" + customer.getPass() + "', '"  + customer.getName() + "', '" + customer.getFamily() + "', '" + customer.getEmail() + "', " + customer.getCredit() + ", " + generatedCid + " );";
			String query = "INSERT INTO customers (uid, pass, name, family, email, credit , cid) VALUES (?, ?, ?, ?, ?, ?, ?);";

			try {
				
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setString(1, customer.getId());
				pst.setString(2, customer.getPass());
				pst.setString(3, customer.getName());
				pst.setString(4, customer.getFamily());
				pst.setString(5, customer.getEmail());
				pst.setInt(6, customer.getCredit());
				pst.setInt(7, generatedCid);
				
				pst.executeUpdate();
				customer.setCid(generatedCid);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		/*
		String q = "select * from customers where uid='" + customer.getId() + "'" ;
		ResultSet rs2 = queryExecutor(q);

		try{
			if(rs2.next()){
				throw new RepeatedIdException();
			}
			else{
				System.out.println("inserting...");
				
				int generatedCid = nextId();
				String query = "INSERT INTO customers (uid, pass, name, family, email, credit , cid) VALUES ('" + customer.getId() + "', '" + customer.getPass() + "', '"  + customer.getName() + "', '" + customer.getFamily() + "', '" + customer.getEmail() + "', " + customer.getCredit() + ", " + generatedCid + " );";
				updateExecutor(query);
				
				customer.setCid(generatedCid);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return;
		}
		*/
			//Customer temp = getCustomerById(customer.getId());
			
			//if(temp == null){
			//	System.out.println("inserting...");
			//	String query = "INSERT INTO shares (uid, name, family, credit , cid) VALUES ('" + customer.getId() + "', '" + customer.getName() + "', '" + customer.getFamily() + "', " + customer.getId() + ", " + customer.getCid() + " );";
			//	updateExecutor(query);
			//}
			//else{
			//	throw new RepeatedIdException();
			//}
		//}catch(Exception e){}
	}
	
	public void log(){
		
		ArrayList<Customer> customers = getAllCustomers();
		
		for(int i=0 ; i<customers.size() ; i++){
			Customer cs = customers.get(i);
			System.out.println(i + "\t" + cs.getId() + '\t' + cs.getCredit());
		}
		
	}

	public ResultSet queryExecutor(String query){
		System.out.println(query);
		//Connection con ;
		Statement st;
		ResultSet rs = null;
		try {
			//con = DriverManager.getConnection(CONN_STR);
			st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ArrayList<Customer> convertCustomerResultSetToCustomerArrayList(ResultSet rs){
		
		ArrayList<Customer> converted = new ArrayList<Customer>();
		try {
			while (rs.next()) {
				converted.add(new Customer(rs.getString("uid"), rs.getString("pass"), rs.getString("name"), rs.getString("family"), rs.getString("email"), rs.getInt("credit") , rs.getInt("cid")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return converted;
	}
	
	public Customer convertCustomerResultSetToCustomer(ResultSet rs){
		
		Customer customer = null;
		try {
			customer = new Customer(rs.getString("uid"), rs.getString("pass"), rs.getString("name"), rs.getString("family"), rs.getString("email"), rs.getInt("credit") , rs.getInt("cid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	
	
	public int updateExecutor(String query){
		System.out.println(query);
		//Connection con ;
		Statement st;
		int result  = -1;
		try {
			//con = DriverManager.getConnection(CONN_STR);
			st = con.createStatement();
			result = st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int nextId() {
		try{
			//Connection con = DriverManager.getConnection(CONN_STR);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(cid) as max_cid from customers");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_cid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void addRole(String uid, String role) throws RepeatedRoleException {
		
		String q = "select * from roles where uid='" + uid + "', and role='" + role + "'" ;
		ResultSet rs2 = queryExecutor(q);

		try{
			if(rs2.next()){
				throw new RepeatedRoleException();
			}
			else{
				String query = "INSERT INTO roles (uid, role) VALUES ('" + uid + "', '" + role + "')" ;
				updateExecutor(query);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return;
		}
		
	}

	@Override
	public ArrayList<String> getCustomerRoles(String uid) {
		
		String q = "select * from roles where uid='" + uid + "'" ;
		ResultSet rs = queryExecutor(q);
		
		ArrayList<String> roles = new ArrayList<String>();
		try {
			while (rs.next()) {
				roles.add(rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return roles;
	}
}