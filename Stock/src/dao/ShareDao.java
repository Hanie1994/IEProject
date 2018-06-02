package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Share;
import daoInterface.ShareDaoInterface;

public class ShareDao implements ShareDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
	//ArrayList<Share> shares;
	private static ShareDao instance;
	
	public ShareDao() {
		//this.shares = new ArrayList<Share>();
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ShareDaoInterface getInstance(){
		if(instance == null)
			instance = new ShareDao();
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
	public ArrayList<Share> getAllShares() {
		
		String query = "select * from shares" ;
		ResultSet rs = queryExecutor(query);
		
		ArrayList<Share> allShares = convertShareResultSetToShareArrayList(rs);
		
		return allShares;
	}

	@Override
	public ArrayList<Share> getSymbolSharesByName(String symbol){
		
		//String query = "select * from shares where symbol='" + symbol + "'" ;
		String query = "select * from shares where symbol = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, symbol);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Share> symbolShares = convertShareResultSetToShareArrayList(rs);

			return symbolShares;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public ArrayList<Share> getCustomerSharesById(String id){
		
		//String query = "select * from shares where uid='" + id + "'" ;
		String query = "select * from shares where uid = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Share> customerShares = convertShareResultSetToShareArrayList(rs);
			
			return customerShares;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		

	}

	@Override
	public void updateShare(Share share) {
		System.out.println("update share called ....");
		
		//String query = "update shares set quantity = '" + share.getQuantity() + "' where shid='" + share.getshid() + "'" ;
		String query = "update shares set quantity = ? where shid = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, share.getQuantity());
			pst.setInt(2, share.getshid());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//updateExecutor(query);
	}

	@Override
	public void deleteShare(Share share) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addShare(Share share) {
		
		Share temp = getCustomerShareBySymbol(share.getUserId(), share.getSymbol());
		
		if(temp == null){
			System.out.println("inserting...");
			//String query = "INSERT INTO shares (shid, uid, symbol, quantity) VALUES (" + nextId() + ", '" + share.getUserId() + "', '" + share.getSymbol() + "', " + share.getQuantity() + " );";
			String query = "INSERT INTO shares (shid, uid, symbol, quantity) VALUES (?, ?, ?, ?);";
			
			try {
				
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setInt(1, nextId());
				pst.setString(2, share.getUserId());
				pst.setString(3, share.getSymbol());
				pst.setInt(4, share.getQuantity());
				
				pst.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//updateExecutor(query);
		}
		else{
			System.out.println("updating...");
			//String query = "update shares set quantity = '" + (temp.getQuantity() + share.getQuantity()) + "' where shid='" + temp.getshid() + "'" ;
			String query = "update shares set quantity = ? where shid = ? " ;
			
			try {
				
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setInt(1, temp.getQuantity() + share.getQuantity() );
				pst.setInt(2, temp.getshid());
				
				pst.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//updateExecutor(query);
		}
		
	}
	
	public void log(){
		
		ArrayList<Share> shares = getAllShares();
		
		for(int i=0 ; i<shares.size() ; i++){
			Share s = shares.get(i);
			System.out.println(i + "\t" + s.getUserId() + '\t' + s.getSymbol() + '\t' + s.getQuantity());
		}
	}

	@Override
	public Share getCustomerShareBySymbol(String id, String symbol) {
		
			//String query = "select * from shares where uid = '" + id + "' and symbol='" + symbol + "'" ;
			String query = "select * from shares where uid = ? and symbol = ? " ;
			
			try {
				
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setString(1, id);
				pst.setString(2, symbol);
				
				ResultSet rs = pst.executeQuery();
				
				int quantity = 0;
				int shid = -1;
				
				if (rs.next()) {
					
					quantity = rs.getInt("quantity");
					shid = rs.getInt("shid");
					
					Share loadedShare = new Share(id, symbol, quantity, shid);
					
					return loadedShare;
				}
				else {
					return null;
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
	
	public ArrayList<Share> convertShareResultSetToShareArrayList(ResultSet rs){
		
		ArrayList<Share> converted = new ArrayList<Share>();
		try {
			while (rs.next()) {
				converted.add(new Share(rs.getString("uid"), rs.getString("symbol"), rs.getInt("quantity"), rs.getInt("shid")));
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
			ResultSet rs = st.executeQuery("select max(shid) as max_shid from shares");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_shid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
}
