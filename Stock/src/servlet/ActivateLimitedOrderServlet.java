package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Core;
import exception.InvalidOrderIdException;
import exception.InvalidOrderTypeException;
import exception.NotEnoughCreditException;
import exception.NotEnoughShareException;
import exception.OrderIsDeclinedException;
import exception.OrderIsQueuedException;
import exception.UnknownUserIdException;


public class ActivateLimitedOrderServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String message = "" ;
    	
    	boolean hasError = false;
		ArrayList<String> errMessage = new ArrayList<String>();
		
    	String oid = request.getParameter("oid");
        
        if(oid==null || oid.equals("")){
        	hasError = true ;
        	errMessage.add("oid can not be empty");
        }

        
        if(!hasError){
        	try {
				message = Core.getInstance().activateLimitedOrderByOid(Integer.valueOf(oid));
			} catch (UnknownUserIdException | NumberFormatException | OrderIsDeclinedException | OrderIsQueuedException | NotEnoughShareException | NotEnoughCreditException | InvalidOrderTypeException | InvalidOrderIdException e) {
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
