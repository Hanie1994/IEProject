package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Symbol;

import daoInterface.SymbolDaoInterface;
import exception.InvalidSymbolIdException;
import exception.RepeatedSymbolException;

public class SymbolDao implements SymbolDaoInterface{
	
	public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";
	private Connection con = null;
	
	//static final int PENDING_STATUS = 0 ;
	//static final int ACCEPTED_STSTUS = 1; 
	
	ArrayList<String> symbols;
	private static SymbolDao instance;
	
	public SymbolDao() {
		this.symbols = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(CONN_STR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static SymbolDaoInterface getInstance(){
		if(instance == null)
			instance = new SymbolDao();
		return instance;
	}

	@Override
	public ArrayList<Symbol> getAllSymbols(int status) {
		
		String query = "select * from symbols where status = " + status ;
		
		ResultSet rs = queryExecutor(query);
		
		ArrayList<Symbol> allSymbols = convertSymbolResultSetToSymbolArrayList(rs);
		
		return allSymbols;
		
		//return symbols;
	}

	@Override
	public void deleteSymbol(Symbol Symbol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSymbol(Symbol symbol) throws RepeatedSymbolException {
		
		try {
			
			getSymbolByName(symbol.getName());
			throw new RepeatedSymbolException();
			
		} catch (InvalidSymbolIdException e) {
			
			// the symbol is not in system and admin wants to add
			int generatedId = nextId();
			//String query = "INSERT INTO symbols (sid, name, company, status, total) VALUES (" + generatedId + ", '" + symbol.getName()  + "', '" + symbol.getCompany() + "', " + symbol.getStatus() + ", " + symbol.getTotal() + ")";
			String query = "INSERT INTO symbols (sid, name, company, status, total) VALUES (?, ?, ?, ?, ?)";
			
			try {
				
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setInt(1, generatedId);
				pst.setString(2, symbol.getName());
				pst.setString(3, symbol.getCompany());
				pst.setInt(4, symbol.getStatus());
				pst.setInt(5, symbol.getTotal());
				
				pst.executeUpdate();
				
				symbol.setSid(generatedId);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//symbol.setSid(generatedId);
			
			//updateExecutor(query);
		}
		
	}
	
	@Override
	public void updateSymbol(Symbol symbol) {
		
		System.out.println("update symbol called ....");
		
		//String query = "update symbols set total = " + symbol.getTotal() + ", status = " + symbol.getStatus() + " where sid=" + symbol.getSid() ;
		String query = "update symbols set total = ? , status = ? where sid = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, symbol.getTotal());
			pst.setInt(2, symbol.getStatus());
			pst.setInt(3, symbol.getSid());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//updateExecutor(query);
	}

	@Override
	public Symbol getSymbolByName(String name) throws InvalidSymbolIdException{
		
		//String query = "select * from symbols where name='" + name + "'" ;
		String query = "select * from symbols where name = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, name);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				return new Symbol(rs.getInt("sid"), rs.getString("name"), rs.getString("company"), rs.getInt("total"), rs.getInt("status"));
			}
			else {
				throw new InvalidSymbolIdException();
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		//ResultSet rs = queryExecutor(query);
		/*
		try {
			if (rs.next()) {
				return new Symbol(rs.getInt("sid"), rs.getString("name"), rs.getString("company"), rs.getInt("total"), rs.getInt("status"));
			}
			else {
				throw new InvalidSymbolIdException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		*/
		
	}

	@Override
	public void log() {
		
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
	
	public ArrayList<Symbol> convertSymbolResultSetToSymbolArrayList(ResultSet rs){
		
		ArrayList<Symbol> converted = new ArrayList<Symbol>();
		try {
			while (rs.next()) {
				converted.add(new Symbol(rs.getInt("sid"), rs.getString("name"), rs.getString("company"), rs.getInt("total"), rs.getInt("status")));
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
			ResultSet rs = st.executeQuery("select max(sid) as max_sid from symbols");
			int maxId = 0;
			if (rs.next()) {
				maxId = rs.getInt("max_sid");
			}
			//con.close();
			return maxId + 1;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public ArrayList<Symbol> getCompanySymbols(String company) {
		
		//String query = "select * from symbols where company='" + company + "'" ;
		String query = "select * from symbols where company = ? " ;
		
		try {
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, company);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Symbol> companySymbols = convertSymbolResultSetToSymbolArrayList(rs);
			
			return companySymbols;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
