package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MarketInfoServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        String message = service.JsonServices.getInstance().getMarketInfo();
        
        return message;
    }

}
