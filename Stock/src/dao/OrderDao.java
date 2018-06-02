package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Order;
import daoInterface.OrderDaoInterface;
import exception.InvalidOrderIdException;

public class OrderDao implements OrderDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	final static int ACTIVE_STATUS = 0;
	
	//ArrayList<Order> orders;
	private static OrderDao instance;

	public OrderDao() {
		//this.orders = new ArrayList<Order>() ;
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static OrderDaoInterface getInstance(){
		if(instance == null)
			instance = new OrderDao();
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
	public ArrayList<Order> getAllOrders(int status) {
		
		//String query = "select * from orders where status=" + status ;
		String query = "select * from orders where status = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, status);
			//ResultSet rs = queryExecutor(query);
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Order> allOrders = convertOrderResultSetToOrderArrayList(rs);
			
			return allOrders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	

	}

	@Override
	public ArrayList<Order> getSymbolOrdersByName(String symbol, int status){
		
		//String query = "select * from orders where symbol='" + symbol + "' and status=" + status ;
		String query = "select * from orders where symbol = ? and status = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, symbol);
			pst.setInt(2, status);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Order> symbolOrders = convertOrderResultSetToOrderArrayList(rs);

			return symbolOrders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//ResultSet rs = queryExecutor(query);
		
		//ArrayList<Order> symbolOrders = convertOrderResultSetToOrderArrayList(rs);

		//return symbolOrders;

	}

	@Override
	public ArrayList<Order> getCustomerOrdersById(String id, int status){
		
		//String query = "select * from orders where uid='" + id + "' and status=" + status ;
		String query = "select * from orders where uid = ? and status = ? ";
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, id);
			pst.setInt(2, status);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Order> customerOrders = convertOrderResultSetToOrderArrayList(rs);
			
			return customerOrders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//ResultSet rs = queryExecutor(query);
		
		//ArrayList<Order> customerOrders = convertOrderResultSetToOrderArrayList(rs);
		
		//return customerOrders;

	}

	@Override
	public void updateOrder(Order order) {
		
		if(order.getRemainingQuantity()<=0 && order.getStatus()==ACTIVE_STATUS)
			order.approveOrder();
		
		System.out.println("update order called ....");
		
		//String query = "update orders set remainingQuantity = " + order.getRemainingQuantity() + ", status = " + order.getStatus() + " where oid=" + order.getOid() ;
		String query = "update orders set remainingQuantity = ? , status = ? where oid = ?" ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, order.getRemainingQuantity());
			pst.setInt(2, order.getStatus());
			pst.setInt(3, order.getOid());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//updateExecutor(query);
	}

	@Override
	public void deleteOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOrder(Order order) {
		
		System.out.println("inserting order...");
		//String query = "INSERT INTO orders (oid, uid, symbol, initQuantity, remainingQuantity, status, price, type, operation, submitDate) VALUES (" + nextId() + ", '" + order.getUserId() + "', '" + order.getSymbol() + "', " + order.getInitQuantity() + ", " + order.getRemainingQuantity()  + ", " + order.getStatus() + ", '" + order.getPrice() + "', '" + order.getType()  + "', '" + order.getOperation()  + "', TO_DATE('" +  order.getSubmitDate().getTime() + "') );";
		try{
			//Connection con = DriverManager.getConnection(CONN_STR);
			
			int generatedOid = nextId();
			
			PreparedStatement pst = con.prepareStatement("INSERT INTO orders (oid, uid, symbol, initQuantity, remainingQuantity, status, price, type, operation, submitDate) VALUES (?,?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, generatedOid);
			pst.setString(2, order.getUserId());
			pst.setString(3, order.getSymbol());
			pst.setInt(4, order.getInitQuantity());
			pst.setInt(5, order.getRemainingQuantity());
			pst.setInt(6, order.getStatus());
			pst.setString(7, order.getPrice());
			pst.setString(8, order.getType());
			pst.setString(9, order.getOperation());
			pst.setLong(10, order.getSubmitDate());
			
			System.out.println(pst.toString());
			pst.executeUpdate();
			
			order.setOid(generatedOid);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
				
	}

	@Override
	public ArrayList<Order> getSymbolOrdersByOperation(String symbol, String operation, Comparator<Order> comparator, int status) {
		
		//String query = "select * from orders where symbol='" + symbol + "' and operation='" + operation + "' and status=" + status ;
		String query = "select * from orders where symbol = ? and operation = ? and status = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, symbol);
			pst.setString(2, operation);
			pst.setInt(3, status);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Order> soOrders = convertOrderResultSetToOrderArrayList(rs);
			
			if(comparator != null){
				Collections.sort(soOrders, comparator);
			}

			return soOrders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//ResultSet rs = queryExecutor(query);
		
		//ArrayList<Order> soOrders = convertOrderResultSetToOrderArrayList(rs);
		
		//if(comparator != null){
		//	Collections.sort(soOrders, comparator);
		//}

		//return soOrders;
	}
	
	public void log(){
		
		ArrayList<Order> orders = getAllOrders(ACTIVE_STATUS);
		
		for(int i=0 ; i<orders.size() ; i++){
			Order o = orders.get(i);
			System.out.println(i + "\t" + o.getSymbol() + '\t' + o.getInitQuantity() + '\t' + o.getRemainingQuantity() + '\t' + o.getUserId() + '\t' + o.getType() + '\t' + o.getOperation() + '\t' + o.getPrice() + "\t" + o.getStatus() + "\t" + o.getSubmitDate());
			if(o.getStatus()==0 && !o.getType().equals("GTC"))
				System.err.println(i);
		}
	}

	@Override
	public void updateOrdersStatus() {
		/*
		for(Order x : orders){
			if(x.getRemainingQuantity()<=0 && x.getStatus()==ACTIVE_STATUS){
				//x.setStatus(1);
				x.approveOrder();
			}	
		}
		*/
	}
/*	
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
*/	
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
	
	public ArrayList<Order> convertOrderResultSetToOrderArrayList(ResultSet rs){
		
		ArrayList<Order> converted = new ArrayList<Order>();
		try {
			while (rs.next()) {
				Order newOrder = new Order(rs.getString("symbol"), rs.getString("price"), rs.getString("type"), rs.getString("uid"), rs.getInt("initQuantity"), rs.getString("operation"), rs.getInt("oid"), rs.getLong("submitDate"), rs.getInt("remainingQuantity"));
				newOrder.setStatus(rs.getInt("status"));
				converted.add(newOrder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return converted;
	}
	
	public int nextId() {
		try{
			//Connection con = DriverManager.getConnection(CONN_STR);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(oid) as max_oid from orders");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_oid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Order getOrderByOid(int oid) throws InvalidOrderIdException {
		
		//String query = "select * from orders where oid=" + oid  ;
		String query = "select * from orders where oid = ? " ;
		
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, oid);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				return new Order(rs.getString("symbol"), rs.getString("price"), rs.getString("type"), rs.getString("uid"), rs.getInt("initQuantity"), rs.getString("operation"), rs.getInt("oid"), rs.getLong("submitDate"), rs.getInt("remainingQuantity"));
			}
			else {
				throw new InvalidOrderIdException();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		
		//ResultSet rs = queryExecutor(query);
		
		/*
		try {
			if (rs.next()) {
				return new Order(rs.getString("symbol"), rs.getString("price"), rs.getString("type"), rs.getString("uid"), rs.getInt("initQuantity"), rs.getString("operation"), rs.getInt("oid"), rs.getLong("submitDate"), rs.getInt("remainingQuantity"));
			}
			else {
				throw new InvalidOrderIdException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		*/
	}

}
