package logic;

import exception.NotEnoughShareException;
import exception.OrderIsDeclinedException;
import exception.OrderIsQueuedException;
import exception.UnknownUserIdException;
import model.Customer;
import model.Order;

public abstract class Sell{
	public abstract String sell(Customer seller, Order sellOrder/*, OrderDaoInterface orders, ShareDaoInterface shares, SymbolDaoInterface symbols*/) throws OrderIsDeclinedException, OrderIsQueuedException, UnknownUserIdException, NotEnoughShareException;
	public String makeResponse(Customer seller, Customer buyer, int quantity, int price, String symbol){
		return seller.getId() + " sold " + quantity + " shares of " + symbol + " @" + price + " to " + buyer.getId() + "\n" ;
	}
}
