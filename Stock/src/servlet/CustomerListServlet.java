package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.JsonServices;

import java.io.IOException;


public class CustomerListServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String message = "" ;
    	
        message = JsonServices.getInstance().getAllCustomers();
        response.setContentType("application/json");

        return message;
        
    }

}
