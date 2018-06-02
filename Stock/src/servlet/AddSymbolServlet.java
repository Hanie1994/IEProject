package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.Core;
import model.Customer;
import exception.RepeatedSymbolException;
import exception.UnknownUserIdException;


public class AddSymbolServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	HttpSession session = request.getSession();
		Customer user = (Customer) session.getAttribute("user");
		
    	String id = user.getId();
    	
        String symbol = request.getParameter("symbol");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();
        
        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }        

        if(symbol==null || symbol.equals("")){
            hasError = true ;
            errMessage.add("symbol can not be empty");
        }
        
        
        String message = "" ;

        if(!hasError){
            try {
            	message = "added" ;
                Core.getInstance().addSymbol(symbol, id);
            } catch (RepeatedSymbolException | UnknownUserIdException e) {
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
