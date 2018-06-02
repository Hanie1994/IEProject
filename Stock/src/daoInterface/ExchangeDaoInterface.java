package daoInterface;

import java.util.ArrayList;

import model.Exchange;

public interface ExchangeDaoInterface {
	//public ExchangeDaoInterface getInstance();
	public ArrayList<Exchange> getAllExchanges();
	public ArrayList<Exchange> getSymbolExchangesByName(String symbol);
	public ArrayList<Exchange> getCustomerExchangesById(String id);
	public void updateExchange(Exchange exchange);
	public void deleteExchange(Exchange exchange);
	public void addExchange(Exchange exchange);
	public void log();
	public ArrayList<Exchange> filterBy(String lowerPrice, String upperPrice, String id, String name, String symbol, String fromDate, String toDate);
}
