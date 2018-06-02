package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import daoInterface.ConfigDaoInterface;

public class ConfigDao implements ConfigDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
	//ArrayList<Order> orders;
	private static ConfigDao instance;

	public ConfigDao() {
		//this.orders = new ArrayList<Order>() ;
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConfigDaoInterface getInstance(){
		if(instance == null)
			instance = new ConfigDao();
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
	public int getValueByName(String name) {
		
		//String query = "select * from configs where name='" + name + "'" ;
		String query = "select * from configs where name = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				return rs.getInt("value");
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
		return -1;
	}

	@Override
	public void updateByName(String name, int value) {
		
		//String query = "update configs set value = " + value + " where name='" + name + "'" ;
		String query = "update configs set value = ? where name = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, value);
			pst.setString(2, name);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	@Override
	public int getUpperBoundLimit() {
		
		int enable = getValueByName("limited");
		int bound = getValueByName("upperBound");
		
		if(enable > 0)
			return bound;
		else
			return Integer.MAX_VALUE;
	}

	@Override
	public void setUpperBoundLimit(int value, int enable) {
		updateByName("upperBound", value);
		updateByName("limited", enable);
	} 

}
