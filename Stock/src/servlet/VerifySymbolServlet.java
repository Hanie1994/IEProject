package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Core;
import exception.InvalidSymbolIdException;


public class VerifySymbolServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String symbol = request.getParameter("symbol");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();

        String message = "" ;
        
        if(!hasError){
        	try {
				message = Core.getInstance().verifySymbol(symbol);
			} catch (InvalidSymbolIdException e) {
				// TODO Auto-generated catch block
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
