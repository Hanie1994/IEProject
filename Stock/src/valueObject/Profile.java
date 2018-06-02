package valueObject;

import java.util.ArrayList;
import java.util.HashMap;

import dao.CreditRequestDao;
import dao.CustomerDao;
import dao.ExchangeDao;
import dao.OrderDao;
import dao.ShareDao;
//import dao.SymbolDao;
import daoInterface.CreditRequestDaoInterface;
import daoInterface.CustomerDaoInterface;
import daoInterface.ExchangeDaoInterface;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
//import daoInterface.SymbolDaoInterface;
//import exception.CriticalException;
import exception.UnknownUserIdException;

import logic.Constants;
import model.CreditRequest;
import model.Customer;
import model.Exchange;
import model.Order;
import model.Share;

public class Profile {
	
	Customer user ;
	
	String id, name, family ;
	int freeCredit, blockedCredit ;
	HashMap<String, Integer> freeShares, blockedShares ;
	
	ArrayList<Order> activeOrders, approvedOrders, declinedOrders, limitedOrders ;
	ArrayList<CreditRequest> pendingRequests, acceptedRequests, rejectedRequests ;
	ArrayList<Exchange> userExchanges;
	
	ArrayList<String> roles ;
	
	public Profile(String uid) throws UnknownUserIdException{
		this.id = uid;
		fill();
	}
	
	private void fill() throws UnknownUserIdException{
		
		CustomerDaoInterface customers ;
		OrderDaoInterface orders;
		ShareDaoInterface shares;
		//SymbolDaoInterface symbols;
		ExchangeDaoInterface exchanges;
		CreditRequestDaoInterface requests;
		
		customers = CustomerDao.getInstance();
		orders = OrderDao.getInstance();
		shares = ShareDao.getInstance();
		//symbols = SymbolDao.getInstance();
		exchanges = ExchangeDao.getInstance();
		requests = CreditRequestDao.getInstance();
		
		
		user = customers.getCustomerById(id);
		
		name = user.getName();
		family = user.getFamily();
		freeCredit = user.getCredit();
		
		activeOrders = orders.getCustomerOrdersById(id, Constants.ORDER_ACTIVE_STATUS);
		approvedOrders = orders.getCustomerOrdersById(id, Constants.ORDER_APPROVED_STATUS);
		declinedOrders = orders.getCustomerOrdersById(id, Constants.ORDER_DECLINED_STATUS);
		limitedOrders = orders.getCustomerOrdersById(id, Constants.ORDER_LIMITED_STATUS);
		
		pendingRequests = requests.getCustomerCreditRequestsById(id, Constants.CREDIT_PENDING_STATUS);
		acceptedRequests = requests.getCustomerCreditRequestsById(id, Constants.CREDIT_ACCEPTED_STATUS);
		rejectedRequests = requests.getCustomerCreditRequestsById(id, Constants.CREDIT_REJECTED_STATUS);
		
		userExchanges = exchanges.getCustomerExchangesById(id);
		
		ArrayList<Share> free = shares.getCustomerSharesById(id);
		for(Share sahm : free){
			freeShares.put(sahm.getSymbol(), sahm.getQuantity());
		}
		
		blockedCredit = 0;
		blockedShares = new HashMap<String, Integer>();
		for(Order order : activeOrders){
			
			// get blocked credit from queued buy orders
			if(order.isBuy()){
				blockedCredit += order.getRemainingQuantity() * Integer.valueOf(order.getPrice()) ;
			}
			
			// get blocked share from queued sell orders
			else if(order.isSell()){
				String symbolName = order.getSymbol();
				if(blockedShares.get(symbolName) == null){
					blockedShares.put(symbolName, order.getRemainingQuantity());
				}
				else{
					blockedShares.put(symbolName,  blockedShares.get(symbolName) + order.getRemainingQuantity() );
				}
			}
			
			else{
				//throw new CriticalException();
			}
		}
		
		roles = customers.getCustomerRoles(id);
	}
	
}
