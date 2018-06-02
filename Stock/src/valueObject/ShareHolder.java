package valueObject;

import java.util.ArrayList;
import java.util.HashMap;

import logic.Constants;
import model.Order;
import model.Share;
import model.Symbol;

import dao.OrderDao;
import dao.ShareDao;
import dao.SymbolDao;
import daoInterface.OrderDaoInterface;
import daoInterface.ShareDaoInterface;
import daoInterface.SymbolDaoInterface;
import exception.InvalidSymbolIdException;

public class ShareHolder {
	
	String symbolName ;
	int symbolStatus ;
	HashMap<String, Integer> holders ;
	
	ArrayList<Order> activeOrders, approvedOrders, declinedOrders;
	
	public ShareHolder(String symbolName) throws InvalidSymbolIdException{
		
		this.symbolName = symbolName;
		fill();
		
	}
	
	public void fill() throws InvalidSymbolIdException{
		
		OrderDaoInterface orders = OrderDao.getInstance();
		ShareDaoInterface shares = ShareDao.getInstance();
		SymbolDaoInterface symbols = SymbolDao.getInstance();
		
		Symbol namad = symbols.getSymbolByName(symbolName);
		symbolStatus = namad.getStatus();
		
		activeOrders = orders.getSymbolOrdersByName(symbolName, Constants.ORDER_ACTIVE_STATUS);
		approvedOrders = orders.getSymbolOrdersByName(symbolName, Constants.ORDER_APPROVED_STATUS);
		declinedOrders = orders.getSymbolOrdersByName(symbolName, Constants.ORDER_DECLINED_STATUS);
		
		// symbol free shares
		ArrayList<Share> free = shares.getSymbolSharesByName(symbolName);
		for(Share sahm : free){
			holders.put(sahm.getUserId(), sahm.getQuantity());
		}
		// symbol blocked share in order queue
		for(Order order : activeOrders){
			if(order.isSell()){
				String holderName = order.getUserId();
				if(holders.get(holderName) == null){
					holders.put(holderName, order.getRemainingQuantity());
				}
				else{
					holders.put(holderName,  holders.get(holderName) + order.getRemainingQuantity() );
				}
			}
		}
		
	}
	
}
