package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SymbolInfoServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String symbol = request.getParameter("symbol");
	
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();

        if(symbol==null || symbol.equals("")){
            hasError = true ;
            errMessage.add("symbol can not be empty");
        }

        String message = "successfully added" ;

        if(!hasError){
            response.setContentType("application/json");
            message = service.JsonServices.getInstance().getSymbolInfoByName(symbol);
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;
    }

}
