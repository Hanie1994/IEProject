package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Core;


public class ChangeLimitServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//String id = request.getParameter("id");
    	
    	
    	
        String amount = request.getParameter("upperBound");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();      

        if(amount==null || amount.equals("")){
            hasError = true ;
            errMessage.add("limit can not be empty");
        }
        int amountInt = 0;
        try{
            amountInt = Integer.parseInt(amount);
        }catch(NumberFormatException e){
            hasError = true;
            errMessage.add("enter valid limit");
        }
        if(amountInt < 0){
        	hasError = true;
        	errMessage.add("limit can not be negative");
        }

        String message = "" ;

        if(!hasError){
            Core.getInstance().changeOrderLimitationRules(amountInt, 1);
			message = "limits changed";
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;

    }
    
}
