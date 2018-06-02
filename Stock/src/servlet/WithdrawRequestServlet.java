package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.Constants;
import logic.Core;
import model.Customer;
import exception.MismatchedParametersException;
import exception.UnknownUserIdException;


public class WithdrawRequestServlet{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//String id = request.getParameter("id");
		HttpSession session = request.getSession();
		Customer user = (Customer) session.getAttribute("user");
	
		String id = user.getId();
		
        String amount = request.getParameter("amount");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();
        
        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }        

        if(amount==null || amount.equals("")){
            hasError = true ;
            errMessage.add("amount can not be empty");
        }
        int amountInt = 0;
        try{
            amountInt = Integer.parseInt(amount);
        }catch(NumberFormatException e){
            hasError = true;
            errMessage.add("enter valid amount");
        }
        if(amountInt < 0){
        	hasError = true;
        	errMessage.add("amount can not be negative");
        }

        String message = "" ;

        if(!hasError){
            try {
                message = Core.getInstance().makeCreditRequest(id, Integer.valueOf(amount), Constants.CREDIT_WITHDRAW_TYPE);
            } catch (MismatchedParametersException | UnknownUserIdException e) {
                message = e.getMessage();
            }
            //Core.getInstance().log();
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;

    }

}
