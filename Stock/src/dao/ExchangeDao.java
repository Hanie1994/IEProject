package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Exchange;
import daoInterface.ExchangeDaoInterface;

public class ExchangeDao implements ExchangeDaoInterface{

	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
//	ArrayList<Exchange> exchanges;
	private static ExchangeDao instance;
	
	public ExchangeDao() {
//		super();
//		this.exchanges = new ArrayList<Exchange>() ;
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ExchangeDaoInterface getInstance(){
		if(instance == null)
			instance = new ExchangeDao();
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
	public ArrayList<Exchange> getAllExchanges() {
		
		
		String query = "select * from exchanges";
		ResultSet rs = queryExecutor(query);
		
		ArrayList<Exchange> exchanges = convertExchangeResultSetToExchangeArrayList(rs);
		
		return exchanges;
	
	}
	
	@Override
	public ArrayList<Exchange> filterBy(String lowerPrice, String upperPrice, String id, String name, String symbol, String fromDate, String toDate) {
		
		try{
			String query = "select * from  exchanges join customers as sellers on exchanges.sellerId=sellers.uid join customers as buyers on exchanges.buyerId=buyers.uid  where (buyPrice >= ? and buyPrice <= ?) and (sellerId like ? or buyerId like ?) and (buyers.name like ? or sellers.name like ?) and (symbol like ?) and (exchangeDate>? and exchangeDate<?) order by exchangeDate DESC";
			PreparedStatement pst = con.prepareStatement(query);
			
			if(lowerPrice.equals("%"))
				pst.setString(1, "0");
			else
				pst.setString(1, lowerPrice);

			if(upperPrice.equals("%"))
				pst.setString(2, "9999999999999999999");
			else
				pst.setString(2, upperPrice);

			pst.setString(3, id);
			pst.setString(4, id);
			pst.setString(5, name);
			pst.setString(6, name);
			pst.setString(7, symbol);
		
			//SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			if(fromDate.equals("%") || fromDate.equals("% %")){
				pst.setLong(8, (new Date(0)).getTime() );
			}
			else{
				System.out.println(fromDate);
				//Date from = format.parse(fromDate);
				//pst.setTimestamp(7, new Timestamp(from.getTime()));
				//pst.setString(7, "to_date('" + fromDate + "', 'yyyy-mm-dd hh24:mi:ss')");
				pst.setLong(8, Long.parseLong(fromDate) );
			}
			if(toDate.equals("%") || toDate.equals("% %")){
				pst.setLong(9, (new Date(Long.MAX_VALUE)).getTime() );
			}
			else{
				System.out.println(toDate);
				//Date to = format.parse(toDate);
				//pst.setTimestamp(8, new Timestamp(to.getTime()));
				//pst.setString(8, "to_date('" + toDate + "', 'yyyy-mm-dd hh24:mi:ss')");
				pst.setLong(9, Long.parseLong(toDate) );
			}
		
			ResultSet rs = pst.executeQuery();
			System.out.println(pst.toString());
		
			ArrayList<Exchange> filterExchanges = convertExchangeResultSetToExchangeArrayList(rs);
			
			try {
				while (rs.next()) {
					filterExchanges.add(new Exchange(rs.getString("symbol"), rs.getString("sellPrice"), rs.getString("buyPrice"), rs.getString("type"), rs.getString("sellerId"), rs.getString("buyerId"), rs.getInt("quantity"), rs.getInt("eid"), rs.getInt("sellRef"), rs.getInt("buyRef"), rs.getLong("exchangeDate")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return filterExchanges;
		}catch(SQLException | ClassCastException e ){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Exchange> getSymbolExchangesByName(String symbol) {
		
		//String query = "select * from exchanges where symbol='" + symbol;
		String query = "select * from exchanges where symbol = ? ";
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, symbol);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Exchange> symbolExchanges = convertExchangeResultSetToExchangeArrayList(rs);
			
			return symbolExchanges;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ArrayList<Exchange> getCustomerExchangesById(String id) {
		
		//String query = "select * from exchanges where uid='" + id;
		String query = "select * from exchanges where sellerId = ?  or buyerId = ? ";
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, id);
			pst.setString(2, id);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Exchange> customerExchanges = convertExchangeResultSetToExchangeArrayList(rs);
			return customerExchanges;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}

	@Override
	public void updateExchange(Exchange exchange) {
	/*	
		System.out.println("update exchange called ....");
		
		String query = "update exchanges set quantity = " + exchange.getQuantity() + " where oid=" + exchange.getEid() ;
		updateExecutor(query);
	*/
	}
	@Override
	public void deleteExchange(Exchange exchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExchange(Exchange exchange) {
		System.out.println("inserting exchange...");
		
		try{
			//Connection con = DriverManager.getConnection(CONN_STR);
			
			int generatedEid = nextId() ;
		
			PreparedStatement pst = con.prepareStatement("INSERT INTO exchanges (eid, symbol, sellPrice, buyPrice, sellerId, buyerId, quantity, type, sellRef, buyRef, exchangeDate) VALUES (?,?,?,?,?,?,?,?,?,?, ?)");
			pst.setInt(1, generatedEid);
			pst.setString(2, exchange.getSymbol());
			pst.setString(3, exchange.getSellPrice());
			pst.setString(4, exchange.getBuyPrice());
			pst.setString(5, exchange.getSellerId());
			pst.setString(6, exchange.getBuyerId());
			pst.setInt(7, exchange.getQuantity());
			pst.setString(8, exchange.getType());
			pst.setInt(9, exchange.getSellRef());
			pst.setInt(10, exchange.getBuyRef());
			//pst.setTimestamp(11, new Timestamp(exchange.getExchangeDate().getTime()));
			pst.setLong(11, exchange.getExchangeDate());
			
			System.out.println(pst.toString());
			pst.executeUpdate();
			
			exchange.setEid(generatedEid);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	
	public void log(){
		
		ArrayList<Exchange> exchanges = getAllExchanges();
		for(int i=0 ; i<exchanges.size() ; i++){
			Exchange ex = exchanges.get(i);
			System.out.println(i + "\t" + ex.getSymbol() + '\t' + ex.getQuantity() + '\t' + ex.getBuyerId() + '\t' + ex.getBuyPrice() + '\t' + ex.getSellerId() + '\t' + ex.getSellPrice());
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
	
	public ArrayList<Exchange> convertExchangeResultSetToExchangeArrayList(ResultSet rs){
		
		ArrayList<Exchange> converted = new ArrayList<Exchange>();
		try {
			while (rs.next()) {
				converted.add(new Exchange(rs.getString("symbol"), rs.getString("sellPrice"), rs.getString("buyPrice"), rs.getString("type"), rs.getString("sellerId"), rs.getString("buyerId"), rs.getInt("quantity"), rs.getInt("eid"), rs.getInt("sellRef"), rs.getInt("buyRef"), rs.getLong("exchangeDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return converted;
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
			ResultSet rs = st.executeQuery("select max(eid) as max_eid from exchanges");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_eid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
}