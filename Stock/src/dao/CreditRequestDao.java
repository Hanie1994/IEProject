package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.CreditRequest;
import daoInterface.CreditRequestDaoInterface;
import exception.RequestNotFoundException;

public class CreditRequestDao implements CreditRequestDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
	//ArrayList<Share> shares;
	private static CreditRequestDao instance;
	
	public CreditRequestDao(){
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static CreditRequestDaoInterface getInstance(){
		if(instance == null)
			instance = new CreditRequestDao();
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
	public ArrayList<CreditRequest> getAllCreditRequests(int status) {
		String query = "select * from requests" ;
		ResultSet rs = queryExecutor(query);
		
		ArrayList<CreditRequest> allRequests = convertCreditRequestResultSetToCreditRequestArrayList(rs);
		
		return allRequests;
	}

	@Override
	public ArrayList<CreditRequest> getCustomerCreditRequestsById(String id, int status) {
		
		//String query = "select * from requests where uid='" + id + "' and status=" + status ;
		String query = "select * from requests where uid = ? and status = ? ";
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, id);
			pst.setInt(2, status);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<CreditRequest> customerRequests = convertCreditRequestResultSetToCreditRequestArrayList(rs);
			
			return customerRequests;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}

	@Override
	public void updateCreditRequest(CreditRequest request) {
		
		//String query = "update requests set status = " + request.getStatus() + " where crid=" + request.getCrid() ;
		String query = "update requests set status = ? where crid = ? " ;
		
		try {
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, request.getStatus());
			pst.setInt(2, request.getCrid());
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//updateExecutor(query);
	}

	@Override
	public void deleteCreditRequest(CreditRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCreditRequest(CreditRequest request) {
		int genId = nextId();
		
		//String query = "INSERT INTO requests (crid, uid, status, amount, type) VALUES (" + genId + ", '" + request.getUserId() + "', " + request.getStatus() + ", " + request.getAmount() + ", " + request.getType() + " );";
		String query = "INSERT INTO requests (crid, uid, status, amount, type) VALUES (?, ?, ?, ?, ?);";
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, genId);
			pst.setString(2, request.getUserId());
			pst.setInt(3, request.getStatus());
			pst.setInt(4, request.getAmount());
			pst.setInt(5, request.getType());
			
			pst.executeUpdate();
			request.setCrid(genId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//request.setCrid(genId);
		//updateExecutor(query);
	}
	
	@Override
	public CreditRequest getCreditRequestByCrid(int crid) throws RequestNotFoundException {
		
		//String query = "select * from requests where crid=" + crid ;
		String query = "select * from requests where crid = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, crid);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return new CreditRequest(rs.getString("uid"), rs.getInt("status"), rs.getInt("amount"), rs.getInt("type"), rs.getInt("crid"));
			}
			else {
				throw new RequestNotFoundException();
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
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
	
	public ArrayList<CreditRequest> convertCreditRequestResultSetToCreditRequestArrayList(ResultSet rs){
		
		ArrayList<CreditRequest> converted = new ArrayList<CreditRequest>();
		try {
			while (rs.next()) {
				converted.add(new CreditRequest(rs.getString("uid"), rs.getInt("status"), rs.getInt("amount"), rs.getInt("type"), rs.getInt("crid")));
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
			ResultSet rs = st.executeQuery("select max(crid) as max_crid from requests");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_crid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

}
