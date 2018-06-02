package types;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import dao.CustomerDao;
import dao.ExchangeDao;
import dao.OrderDao;
import dao.ShareDao;
import daoInterface.CustomerDaoInterface;
import daoInterface.ExchangeDaoInterface;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
import exception.NotEnoughCreditException;
import exception.OrderIsDeclinedException;
import exception.OrderIsQueuedException;
import exception.UnknownUserIdException;
import logic.Buy;
import logic.Constants;
import model.Customer;
import model.Exchange;
import model.Order;
import model.Share;

public class BuyGTC extends Buy {
	
	OrderDaoInterface orders ;
	ShareDaoInterface shares ;
	ExchangeDaoInterface exchanges ;
	CustomerDaoInterface customers ;
	
	@Override
	public String buy(Customer buyer, Order buyOrder) throws OrderIsDeclinedException, OrderIsQueuedException, UnknownUserIdException, NotEnoughCreditException {
		
		String response = "";

		orders = OrderDao.getInstance();
		shares = ShareDao.getInstance();
		exchanges = ExchangeDao.getInstance();
		customers = CustomerDao.getInstance();
		
		String symbol = buyOrder.getSymbol();
		
		//orders.addOrder(buyOrder);
		
		if(!hasEnoughCredit(buyer, buyOrder)){
			
			buyOrder.declineOrder();
			orders.updateOrder(buyOrder);
			
			throw new NotEnoughCreditException();
		}
		
		ArrayList<Order> sellOrders = orders.getSymbolOrdersByOperation(symbol, Constants.ORDER_SELL, new Comparator<Order>() {
	        	@Override
	        	public int compare(Order o1, Order o2)
	        	{
	        		//return  o1.getPrice().compareTo(o2.getPrice());
	        		return  Integer.valueOf(o1.getPrice()).compareTo(Integer.valueOf(o2.getPrice()));
	        	}
	    	}
			, Constants.ORDER_ACTIVE_STATUS);
		
		//System.out.println("+++++++++++" + sellOrders.size());
		
		int buyPrice = Integer.valueOf(buyOrder.getPrice());
		for(Order sellOrder : sellOrders){
			
			if(buyOrder.getRemainingQuantity() <= 0){
				//buyOrder.setStatus(1);
				break;
			}
			
			int sellPrice = Integer.valueOf(sellOrder.getPrice());
			if(sellPrice < buyPrice){
				
				int sellQty = sellOrder.getRemainingQuantity();
				int buyQty  = buyOrder.getRemainingQuantity();
				int diff = sellQty - buyQty ;
				int exQty = 0 ; //exchange quantity
				int exPrc = buyPrice; //exchange price
				
				if(diff > 0){
					exQty = buyQty;
				}
				else{
					exQty = sellQty;
				}
				
				//sellOrder.setRemainingQuantity(sellQty - exQty);
				sellOrder.decreaseRemainingQuantity(exQty);
				orders.updateOrder(sellOrder);
				//buyOrder.setRemainingQuantity( buyQty  - exQty);
				buyOrder.decreaseRemainingQuantity(exQty);
				orders.updateOrder(buyOrder);
				
				Customer seller = customers.getCustomerById(sellOrder.getUserId());
				
				Share newShare = new Share(buyer.getId(), symbol, exQty, -1);
				shares.addShare(newShare);
				
				seller.deposit(exQty * exPrc);
				customers.updateCustomer(seller);
				
				Exchange newEx = new Exchange(symbol, sellOrder.getPrice(), buyOrder.getPrice(), Constants.ORDER_GTC_TYPE, sellOrder.getUserId(), buyOrder.getUserId(), exQty, 0, sellOrder.getOid(), buyOrder.getOid(), (new Date()).getTime() );
				exchanges.addExchange(newEx);
				
				response += makeResponse(seller, buyer, exQty, exPrc, symbol);
				
				//if(sellOrder.getRemainingQuantity() <= 0){
				//	sellOrder.setStatus(1);
				//}
				
			}
			else{
				break;
			}
		}
		
		//no khardi foroosh
		if(buyOrder.getInitQuantity() == buyOrder.getRemainingQuantity())
			throw new OrderIsQueuedException();
				
		if(buyOrder.getRemainingQuantity() > 0)
			response += "Remaining Quantity Queued\n";

		orders.updateOrdersStatus();
		
		return response;
	}
	
	public boolean hasEnoughCredit(Customer buyer, Order buyOrder){
		int currentCredit = buyer.getCredit();
		int neededCredit = Integer.valueOf(buyOrder.getPrice()) * buyOrder.getInitQuantity();
		if(neededCredit > currentCredit)
			return false;
		
		//buyer.setCredit(currentCredit - neededCredit);
		buyer.withdraw(neededCredit);
		customers.updateCustomer(buyer);
		
		return true;
	}

}
