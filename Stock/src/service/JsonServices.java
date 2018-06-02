package service;

import java.awt.Container;
import java.util.ArrayList;

import logic.Constants;
import model.Symbol;
import valueObject.Profile;
import valueObject.ShareHolder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.CreditRequestDao;
import dao.CustomerDao;
import dao.ExchangeDao;
import dao.OrderDao;
import dao.ShareDao;
import dao.SymbolDao;
import daoInterface.CreditRequestDaoInterface;
import daoInterface.CustomerDaoInterface;
import daoInterface.ExchangeDaoInterface;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
import daoInterface.SymbolDaoInterface;
import exception.InvalidCompanyException;
import exception.InvalidSymbolIdException;
import exception.UnknownUserIdException;

public class JsonServices {
	
	private static JsonServices instance;
	
	CustomerDaoInterface customers ;
	OrderDaoInterface orders;
	ShareDaoInterface shares;
	SymbolDaoInterface symbols;
	ExchangeDaoInterface exchanges;
	CreditRequestDaoInterface requests;
	
	Gson gson ;
	
	public JsonServices(){
		
		customers = CustomerDao.getInstance();
		orders = OrderDao.getInstance();
		shares = ShareDao.getInstance();
		symbols = SymbolDao.getInstance();
		exchanges = ExchangeDao.getInstance();
		requests = CreditRequestDao.getInstance();
		
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public static JsonServices getInstance(){
		if(instance == null)
			instance =new JsonServices();
		return instance;
	}

	public String getSymbolList(){
		return gson.toJson(symbols.getAllSymbols(Constants.SYMBOL_VERIFIED_STSTUS));
	}
	
	public String getSymbolInfoByName(String symbol){
		return gson.toJson(orders.getSymbolOrdersByName(symbol, Constants.ORDER_ACTIVE_STATUS));
	}
	
	public String getCustomerEconomicInfoById(String id){
		ArrayList<Object> info = new ArrayList<Object>();
		try {
			info.add(customers.getCustomerById(id));
		} catch (UnknownUserIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		info.add(orders.getCustomerOrdersById(id, Constants.ORDER_ACTIVE_STATUS));
		info.add(orders.getCustomerOrdersById(id, Constants.ORDER_APPROVED_STATUS));
		info.add(orders.getCustomerOrdersById(id, Constants.ORDER_DECLINED_STATUS));
		info.add(shares.getCustomerSharesById(id));
		
		return gson.toJson(info);
	}
	
	public String getCustomerCreditById(String id){
		try {
			return gson.toJson(customers.getCustomerById(id));
		} catch (UnknownUserIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getCustomerActiveOrdersById(String id){
		return gson.toJson(orders.getCustomerOrdersById(id, Constants.ORDER_ACTIVE_STATUS));
	}
	
	public String getCustomerApprovedOrdersById(String id){
		return gson.toJson(orders.getCustomerOrdersById(id, Constants.ORDER_APPROVED_STATUS));
	}
	
	public String getCustomerDeclinedOrdersById(String id){
		return gson.toJson(orders.getCustomerOrdersById(id, Constants.ORDER_DECLINED_STATUS));
	}
	
	public String getCustomerSharesById(String id){
		return gson.toJson(shares.getCustomerSharesById(id));
	}
	
	public String getMarketInfo(){
		return gson.toJson(orders.getAllOrders(Constants.ORDER_ACTIVE_STATUS));
	}
	
	public String filterExchanges(String lowerPrice, String upperPrice, String id, String name, String symbol, String fromDate, String toDate){
		return gson.toJson(exchanges.filterBy(lowerPrice, upperPrice, id, name, symbol, fromDate, toDate));
	}
	
	public String getCustomerPendingCreditRequestsById(String id){
		return gson.toJson(requests.getCustomerCreditRequestsById(id, Constants.CREDIT_PENDING_STATUS));
	}
	
	public String getCustomerProfileById(String id) throws UnknownUserIdException{
		//try {
			return gson.toJson(new Profile(id));
		//} catch (UnknownUserIdException e) {
		//	return gson.toJson(e);
		//}
	}
	
	public String getAllCustomers(){
		return gson.toJson(customers.getAllCustomers());
	}
	
	public String getSymbolShareHolders(String symbolName, String company) throws InvalidSymbolIdException, InvalidCompanyException{
		
		Symbol symbol = symbols.getSymbolByName(symbolName);
		
		if(!symbol.getCompany().equals(company))
			throw new InvalidCompanyException();
		
		return gson.toJson(new ShareHolder(symbolName));
	}
	
	public String getCompanySymbolList(String company) throws InvalidCompanyException {
		
		// check company validity
		try {
			customers.getCustomerById(company);
		} catch (UnknownUserIdException e) {
			throw new InvalidCompanyException();
		}
		
		return gson.toJson(symbols.getCompanySymbols(company));
	}
	
	public String getPendingSymbols(){
		return gson.toJson(symbols.getAllSymbols(Constants.SYMBOL_PENDING_STATUS));
	}
}
