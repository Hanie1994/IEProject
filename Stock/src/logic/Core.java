package logic;

//import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.CreditRequest;
import model.Customer;
import model.Order;
import model.Share;
import model.Symbol;
import dao.ConfigDao;
import dao.CreditRequestDao;
import dao.CustomerDao;
import dao.ExchangeDao;
import dao.OrderDao;
import dao.ShareDao;
import dao.SymbolDao;
import daoInterface.ConfigDaoInterface;
import daoInterface.CreditRequestDaoInterface;
import daoInterface.CustomerDaoInterface;
import daoInterface.ExchangeDaoInterface;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
import daoInterface.SymbolDaoInterface;
import exception.*;

public class Core{
	
	static final int PENDING_STATUS = 0 ;
	
	private static Core instance;
	
	CustomerDaoInterface customers ;
	OrderDaoInterface orders;
	ShareDaoInterface shares;
	SymbolDaoInterface symbols;
	ExchangeDaoInterface exchanges;
	CreditRequestDaoInterface requests ;
	ConfigDaoInterface configs;
	
	Map<String, Integer> consistency = new HashMap<String, Integer>();
	int totalSystemCredit = 0;
	
	public Core(){
		
		customers = CustomerDao.getInstance();
		orders = OrderDao.getInstance();
		shares = ShareDao.getInstance();
		symbols = SymbolDao.getInstance();
		exchanges = ExchangeDao.getInstance();
		requests = CreditRequestDao.getInstance();
		configs = ConfigDao.getInstance();
/*		
		try {
			customers.addCustomer(new Customer("1", "admin", "adminian", 0, 1));
		} catch (RepeatedIdException e) {
			e.printStackTrace();
		}
*/		
	}
		
	public static Core getInstance(){
		if(instance == null)
			instance = new Core();
		return instance;
	}
	
	public void addCustomer(String id, String name, String family, String password, String email) throws RepeatedIdException, MismatchedParametersException{
		//if(id==null || name==null || family==null || id.equals("") || password==null || email==null)
		//	throw new MismatchedParametersException();
		customers.addCustomer(new Customer(id, password, name, family, email, 0, -1));
		//customers.print();
	}
	
	public void depositCustomer(String id, int amount) throws UnknownUserIdException, MismatchedParametersException{
		if(id==null /*|| amount==null*/ || id.equals(""))
			throw new MismatchedParametersException();
		
		//customers.depositById(id, amount);
		Customer user = customers.getCustomerById(id);
		
		//user.setCredit( user.getCredit() + amount);
		user.deposit(amount);
		customers.updateCustomer(user);
		
		totalSystemCredit += amount;
	}
	
	public void withdrawCustomer(String id, int amount) throws UnknownUserIdException, NotEnoughCreditException, MismatchedParametersException{
		if(id==null /*|| amount==null*/ || id.equals(""))
			throw new MismatchedParametersException();
		
		Customer user = customers.getCustomerById(id);
		
		int currentCredit = user.getCredit();
		if(currentCredit < amount)
			throw new NotEnoughCreditException();
		
		//user.setCredit( user.getCredit() - amount);
		user.withdraw(amount);
		customers.updateCustomer(user);
		
		totalSystemCredit -= amount;
	}
	
	public String sellOrder(String id, String instrument, String price, int quantity, String type) throws MismatchedParametersException, OrderIsQueuedException , UnknownUserIdException, InvalidOrderTypeException, NotEnoughShareException, InvalidSymbolIdException, OrderIsDeclinedException, OrderIsLimitedException{

		if(id==null || instrument==null || price==null || type==null || id.equals("") || instrument.equals("") || price.equals("") || type.equals(""))
			throw new MismatchedParametersException();
		
		Customer user = customers.getCustomerById(id);
		symbols.getSymbolByName(instrument);
		Order sellOrder = new Order(instrument, price, type, id, quantity, "Sell", -1, (new Date()).getTime(), quantity);
		if(Integer.valueOf(price) * quantity >= configs.getUpperBoundLimit())
			sellOrder.limitOrder();
		orders.addOrder(sellOrder);
/*		
		if(!user.isAdmin()){
			symbols.getSymbolByName(instrument);
		}
		else{
			symbols.addSymbol(new Symbol(-1, instrument, id, quantity, 0));
			
				//symbol's share consistency
				String symbol = instrument;
				int val = quantity;
				if(consistency.get(symbol) != null){
					int currentQty = consistency.get(symbol);
					consistency.put(symbol, val+currentQty);
				}
				else{
					consistency.put(symbol, val);
				}
				//System.out.println("%%%%%%%%%%\t" + consistency.get("RENA1"));
		}
*/
		String response = "";
		if(sellOrder.isLimited())
			throw new OrderIsLimitedException();
		else{
			try{
				Class clazz = Class.forName("types.Sell" + type);
				Sell sellRequest = (Sell)clazz.newInstance();
				response = sellRequest.sell(user, sellOrder);
			}catch(ClassNotFoundException ex){
				throw new InvalidOrderTypeException();
			}catch(InstantiationException | IllegalAccessException ex){
				ex.printStackTrace();
			}
		}
		
		return response;
	}

	public String buyOrder(String id, String instrument, String price, int quantity, String type) throws MismatchedParametersException, UnknownUserIdException, InvalidOrderTypeException, InvalidSymbolIdException, OrderIsDeclinedException, OrderIsQueuedException, NotEnoughCreditException, OrderIsLimitedException{
		
		if(id==null || instrument==null || price==null || type==null || id.equals("") || instrument.equals("") || price.equals("") || type.equals(""))
			throw new MismatchedParametersException();
		
		Customer user = customers.getCustomerById(id);
		symbols.getSymbolByName(instrument);
		Order buyOrder = new Order(instrument, price, type, id, quantity, "Buy", -1, (new Date()).getTime(), quantity);
		if(Integer.valueOf(price) * quantity >= configs.getUpperBoundLimit())
			buyOrder.limitOrder();
		orders.addOrder(buyOrder);
		
		String response = "";
		if(buyOrder.isLimited())
			throw new OrderIsLimitedException();
		else{
			try{
				Class clazz = Class.forName("types.Buy" + type);
				Buy buyRequest = (Buy)clazz.newInstance();
				response = buyRequest.buy(user, buyOrder);
			}catch(ClassNotFoundException ex){
				throw new InvalidOrderTypeException();
			}catch(InstantiationException | IllegalAccessException ex){
				ex.printStackTrace();
			}
		}
		
		return response;
	}
	
	public String activateLimitedOrderByOid(int oid) throws OrderIsDeclinedException, OrderIsQueuedException, UnknownUserIdException, NotEnoughShareException, NotEnoughCreditException, InvalidOrderTypeException, InvalidOrderIdException{
		
		Order limitedOrder = orders.getOrderByOid(oid);
		limitedOrder.activateOrder();
		orders.updateOrder(limitedOrder);
		
		String response = "";
		
		try{
			Customer user = customers.getCustomerById(limitedOrder.getUserId());
			if(limitedOrder.getOperation().equals("sell")){
				Class clazz = Class.forName("types.Sell" + limitedOrder.getType());
				Sell sellRequest = (Sell)clazz.newInstance();
				response = sellRequest.sell(user, limitedOrder);
			}
			else{
				Class clazz = Class.forName("types.Buy" + limitedOrder.getType());
				Buy buyRequest = (Buy)clazz.newInstance();
				response = buyRequest.buy(user, limitedOrder);
			}
		}catch(ClassNotFoundException ex){
			throw new InvalidOrderTypeException();
		}catch(InstantiationException | IllegalAccessException ex){
			ex.printStackTrace();
		}
		
		return response;
	}
	
	
	public String declineLimitedOrderByOid(int oid) throws InvalidOrderIdException {
		
		String responsse = "limited order declined" ;
		
		Order limitedOrder = orders.getOrderByOid(oid);
		limitedOrder.declineOrder();
		orders.updateOrder(limitedOrder);
		
		return responsse;
	}
	public void changeOrderLimitationRules(int upperBound, int enable){
		configs.setUpperBoundLimit(upperBound, enable);
	}
	
	public void addSymbol(String name, String company) throws RepeatedSymbolException, UnknownUserIdException {
		customers.getCustomerById(company); // check company exist
		symbols.addSymbol(new Symbol(-1, name, company, 0, PENDING_STATUS));
	}
	
	public String chargeSymbol(String name, String company, String price, int amount) throws InvalidSymbolIdException, SymbolNotVerifiedException, MismatchedParametersException, InvalidCompanyException, OrderIsQueuedException, UnknownUserIdException, InvalidOrderTypeException, NotEnoughShareException, OrderIsDeclinedException, OrderIsLimitedException{
		
		String response = "charged successfully";
		
		if(amount < 0)
			throw new MismatchedParametersException();
		
		Symbol s = symbols.getSymbolByName(name);
		
		if(!s.isVerified())
			throw new SymbolNotVerifiedException();
		
		if(!s.getCompany().equals(company))
			throw new InvalidCompanyException();
			
		s.charge(amount);
		symbols.updateSymbol(s);
		
		// add charged symbol as an order to sell queue
		sellOrder(company, name, price, amount, Constants.ORDER_ADD_TYPE);
		
		return response;
	}
	
	public String verifySymbol(String name) throws InvalidSymbolIdException{
		
		String response = "symbol verified" ;
		
		Symbol s = symbols.getSymbolByName(name);
		s.verify();
		symbols.updateSymbol(s);
		
		return response;
	}
	
	public String makeCreditRequest(String id, int amount, int type) throws UnknownUserIdException, MismatchedParametersException {
		
		String response = "credit request created" ;
		
		customers.getCustomerById(id); // check valid customer
		
		if(amount < 0)
			throw new MismatchedParametersException();
		
		if(type!=Constants.CREDIT_DEPOSIT_TYPE && type!=Constants.CREDIT_WITHDRAW_TYPE)
			throw new MismatchedParametersException();
		
		requests.addCreditRequest(new CreditRequest(id, PENDING_STATUS, amount, type, -1));
		
		return response;
	}
	
	public String acceptCreditRequest(int crid) throws RequestNotFoundException, UnknownUserIdException, MismatchedParametersException, NotEnoughCreditException, CreditRequestIsNotPendingException{
		
		String response = "credit request accepted" ;
		
		CreditRequest request = requests.getCreditRequestByCrid(crid);
		if(!request.isPending())
			throw new CreditRequestIsNotPendingException();
		//Customer user = customers.getCustomerById(request.getUserId());
		
		if(request.isDeposit()){
			//user.deposit(request.getAmount());
			depositCustomer(request.getUserId(), request.getAmount());
		}
		else{
			//user.withdraw(request.getAmount());
			withdrawCustomer(request.getUserId(), request.getAmount());
		}
		
		//customers.updateCustomer(user);
		
		request.accept();
		requests.updateCreditRequest(request);
		
		return response;
	}
	
	public String rejectCreditRequest(int crid) throws RequestNotFoundException, CreditRequestIsNotPendingException{
		
		String response = "credit request rejected" ;
		
		CreditRequest request = requests.getCreditRequestByCrid(crid);
		if(!request.isPending())
			throw new CreditRequestIsNotPendingException();
		
		request.reject();
		requests.updateCreditRequest(request);
		
		return response;
	}
	
	public String addRole(String uid, String role) throws RepeatedRoleException{
		String message = "role added" ;
		customers.addRole(uid, role);
		return message;
	}
	
	public String makeResponse(Customer seller, Customer buyer, int quantity, int price, String namad){
		return seller.getId() + " sold " + quantity + " shares of " + namad + " @" + price + " to " + buyer.getId() + "\n" ;
	}
	public void prt(int i){
		System.out.println(i);
	}
	public void log(){
		System.out.println("*------------------ Welcome to Log ------------------*");
		customers.log();
		System.out.println("............Orders.....");
		orders.log();
		System.out.println("............Shares.....");
		shares.log();
		System.out.println("............Exchanges..");
		exchanges.log();
		System.out.println("------------------------------------------------------");
	}
	public void check(){
		int totalCrdt = 0;
		int totalShare = 0;
		for(Customer c : customers.getAllCustomers()){
			totalCrdt += c.getCredit();
		}
		for(Order o : orders.getAllOrders(0)){
			if(o.getOperation().equals("Buy")){
				totalCrdt += o.getRemainingQuantity() * Integer.valueOf(o.getPrice()) ;
			}
			else{
				totalShare += o.getRemainingQuantity() ;
			}
		}
		for(Share s : shares.getAllShares()){
			totalShare += s.getQuantity();
		}
		
		int totalSystemShare = 0;
		for(Map.Entry<String, Integer> e : consistency.entrySet()){
			totalSystemShare += e.getValue();
			System.out.println("==" + e.getKey() + "==" + e.getValue());
		}
		
		System.out.println("Credit: " + totalSystemCredit + "\t" +  totalCrdt );
		System.out.println("Share: " + totalSystemShare + "\t" +  totalShare );
		System.out.println(":::: " + model.Order.cnt);
		
		for(Order o : orders.getAllOrders(0)){
			if(!o.getType().equals("GTC"))
				System.err.println("status error");
		}
	}
}