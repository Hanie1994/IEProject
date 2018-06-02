package daoInterface;

import java.util.ArrayList;

import exception.RequestNotFoundException;

import model.CreditRequest;

public interface CreditRequestDaoInterface {
	public ArrayList<CreditRequest> getAllCreditRequests(int status) ;
	public ArrayList<CreditRequest> getCustomerCreditRequestsById(String id, int status);
	public void updateCreditRequest(CreditRequest request);
	public void deleteCreditRequest(CreditRequest request);
	public void addCreditRequest(CreditRequest request);
	public CreditRequest getCreditRequestByCrid(int crid) throws RequestNotFoundException;
}
