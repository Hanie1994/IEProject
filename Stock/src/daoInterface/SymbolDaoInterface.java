package daoInterface;

import java.util.ArrayList;

import model.Symbol;

import exception.InvalidSymbolIdException;
import exception.RepeatedSymbolException;


public interface SymbolDaoInterface {
	//public abstract SymbolDaoInterface getInstance();
	public ArrayList<Symbol> getAllSymbols(int status);
	//public void updateSymbol(String Symbol);
	public void deleteSymbol(Symbol symbol);
	public void addSymbol(Symbol symbol) throws RepeatedSymbolException;
	public Symbol getSymbolByName(String name) throws InvalidSymbolIdException;
	public void log();
	public void updateSymbol(Symbol symbol);
	public ArrayList<Symbol> getCompanySymbols(String company);
}
