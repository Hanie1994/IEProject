package daoInterface;

import java.util.ArrayList;
import java.util.Comparator;

import exception.InvalidOrderIdException;

import model.Order;

public interface OrderDaoInterface {
	//public OrderDaoInterface getInstance();
	public ArrayList<Order> getAllOrders(int status);
	public ArrayList<Order> getSymbolOrdersByName(String symbol, int status);
	public ArrayList<Order> getCustomerOrdersById(String id, int status);
	public ArrayList<Order> getSymbolOrdersByOperation(String symbol, String operation, Comparator<Order> comparator, int status);
	public void updateOrder(Order order);
	public void deleteOrder(Order order);
	public void addOrder(Order order);
	public void log();
	public void updateOrdersStatus();
	public Order getOrderByOid(int oid) throws InvalidOrderIdException;
}
