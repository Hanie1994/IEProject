package daoInterface;

import java.util.ArrayList;

import model.Share;

public interface ShareDaoInterface {
	//public ShareDaoInterface getInstance();
	public ArrayList<Share> getAllShares();
	public ArrayList<Share> getSymbolSharesByName(String symbol);
	public ArrayList<Share> getCustomerSharesById(String id);
	public Share getCustomerShareBySymbol(String id, String symbol);
	public void updateShare(Share Share);
	public void deleteShare(Share Share);
	public void addShare(Share Share);
	public void log();
}
