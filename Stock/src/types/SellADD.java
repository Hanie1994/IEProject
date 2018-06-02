package types;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import dao.CustomerDao;
import dao.ExchangeDao;
import dao.OrderDao;
import dao.ShareDao;
import daoInterface.ExchangeDaoInterface;
import daoInterface.CustomerDaoInterface;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
import logic.Constants;
import logic.Sell;
import model.Customer;
import model.Exchange;
import model.Order;
import model.Share;
import exception.NotEnoughShareException;
import exception.OrderIsDeclinedException;
import exception.OrderIsQueuedException;
import exception.UnknownUserIdException;

public class SellADD extends Sell{
	
	CustomerDaoInterface customers ;
	ShareDaoInterface shares;
	
	public String sell(Customer seller, Order sellOrder/*, OrderDaoInterface orders, ShareDaoInterface shares, SymbolDaoInterface symbols*/) throws OrderIsDeclinedException, UnknownUserIdException, OrderIsQueuedException, NotEnoughShareException{
		
		String response = "symbol charged\n";

		OrderDaoInterface orders = OrderDao.getInstance();
		shares = ShareDao.getInstance();
		ExchangeDaoInterface exchanges = ExchangeDao.getInstance();
		customers = CustomerDao.getInstance();
		
		String symbol = sellOrder.getSymbol();
		
		//orders.addOrder(sellOrder);
		
		//if(/*!seller.isAdmin() &&*/ !hasEnoughShare(seller, sellOrder)){
			
		//	sellOrder.declineOrder();
		//	orders.updateOrder(sellOrder);
			
		//	throw new NotEnoughShareException();
		//}
		
		ArrayList<Order> buyOrders = orders.getSymbolOrdersByOperation(symbol, Constants.ORDER_BUY, new Comparator<Order>() {
	        	@Override
	        	public int compare(Order o1, Order o2)
	        	{
	        		//return  o2.getPrice().compareTo(o1.getPrice());
	        		return  Integer.valueOf(o2.getPrice()).compareTo(Integer.valueOf(o1.getPrice()));
	        	}
	    	}
			, Constants.ORDER_ACTIVE_STATUS);
		
		//System.out.println("+++++++++++" + buyOrders.size());
		
		int sellPrice = Integer.valueOf(sellOrder.getPrice());
		for(Order buyOrder : buyOrders){
			
			if(sellOrder.getRemainingQuantity() <= 0){
				//sellOrder.setStatus(1);
				break;
			}
			
			int buyPrice = Integer.valueOf(buyOrder.getPrice());
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
				
				Customer buyer = customers.getCustomerById(buyOrder.getUserId());
				Share newShare = new Share(buyer.getId(), symbol, exQty, -1);
				shares.addShare(newShare);
				
				seller.deposit(exQty * exPrc);
				customers.updateCustomer(seller);
				
				Exchange newEx = new Exchange(symbol, sellOrder.getPrice(), buyOrder.getPrice(), Constants.ORDER_ADD_TYPE, sellOrder.getUserId(), buyOrder.getUserId(), exQty, 0, sellOrder.getOid(), buyOrder.getOid(), (new Date()).getTime() );
				exchanges.addExchange(newEx);
				
				response += makeResponse(seller, buyer, exQty, exPrc, symbol);
				
				//if(buyOrder.getRemainingQuantity() <= 0){
				//	buyOrder.setStatus(1); //approve buy order
				//}
			}
			else{
				break;
			}
		}
		
		//no khardi foroosh
		if(sellOrder.getInitQuantity() == sellOrder.getRemainingQuantity())
			throw new OrderIsQueuedException();
		
		if(sellOrder.getRemainingQuantity() > 0)
			response += "Remaining Quantity Queued\n";
		
		orders.updateOrdersStatus();
		
		return response;
	}
	
	/*public boolean hasEnoughShare(Customer seller, Order sellOrder){
		Share x = ShareDao.getInstance().getCustomerShareBySymbol(seller.getId(), sellOrder.getSymbol());
		int requiredShare = sellOrder.getInitQuantity();
		
		if(x==null || x.getQuantity()<requiredShare)
			return false;
		
		//int qty = x.getQuantity();
		//x.setQuantity(qty - requiredShare);
		x.decreaseQuantity(requiredShare);
		shares.updateShare(x);
		
		return true;
	}*/

}
