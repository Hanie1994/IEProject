package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Core;
import exception.CreditRequestIsNotPendingException;
import exception.MismatchedParametersException;
import exception.NotEnoughCreditException;
import exception.RequestNotFoundException;
import exception.UnknownUserIdException;


public class AcceptCreditServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String creditId = request.getParameter("crid");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();
        
        int crid = -1;
        try{
            crid = Integer.parseInt(creditId);
        }catch(NumberFormatException e){
            hasError = true;
            errMessage.add("enter valid credit id");
        }

        String message = "" ;
        
        if(!hasError){
        	try {
    			message = Core.getInstance().acceptCreditRequest(crid);
    		} catch (MismatchedParametersException | RequestNotFoundException
    				| UnknownUserIdException | NotEnoughCreditException
    				| CreditRequestIsNotPendingException e) {

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
