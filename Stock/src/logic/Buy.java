package logic;

import exception.NotEnoughCreditException;
import exception.OrderIsDeclinedException;
import exception.OrderIsQueuedException;
import exception.UnknownUserIdException;
import model.Customer;
import model.Order;

public abstract class Buy{
	public abstract String buy(Customer buyer, Order buyOrder/*, OrderDaoInterface orders, ShareDaoInterface shares*/) throws OrderIsDeclinedException, OrderIsQueuedException, UnknownUserIdException, NotEnoughCreditException;
	public String makeResponse(Customer seller, Customer buyer, int quantity, int price, String symbol){
		return seller.getId() + " sold " + quantity + " shares of " + symbol + " @" + price + " to " + buyer.getId() + "\n" ;
	}
}
