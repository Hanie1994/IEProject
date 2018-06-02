package servlet;

import exception.InvalidCompanyException;
import exception.InvalidSymbolIdException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.JsonServices;

import java.io.IOException;
import java.util.ArrayList;

import model.Customer;


public class ShareHolderServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String message = "" ;
    	
    	boolean hasError = false;
		ArrayList<String> errMessage = new ArrayList<String>();
		
		HttpSession session = request.getSession();
		Customer user = (Customer) session.getAttribute("user");
		
    	String company = user.getId();
    	
    	String symbolName = request.getParameter("symbol");
        
        if(symbolName==null || symbolName.equals("")){
        	hasError = true ;
        	errMessage.add("symbol name can not be empty");
        }

        
        if(!hasError){
        	try {
				message = JsonServices.getInstance().getSymbolShareHolders(symbolName, company);
				response.setContentType("application/json");
			} catch ( InvalidSymbolIdException | InvalidCompanyException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
        }
        else{
        	message = "some errors occured" ;
        	request.setAttribute("errors", errMessage);
        }

        return message;
        
    }

}
