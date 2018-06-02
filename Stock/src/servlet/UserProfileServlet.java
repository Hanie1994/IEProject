package servlet;

import exception.UnknownUserIdException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.JsonServices;

import java.io.IOException;
import java.util.ArrayList;


public class UserProfileServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String message = "" ;
    	
    	boolean hasError = false;
		ArrayList<String> errMessage = new ArrayList<String>();
		
    	String id = request.getParameter("id");
        
        if(id==null || id.equals("")){
        	hasError = true ;
        	errMessage.add("id can not be empty");
        }

        
        if(!hasError){
        	try {
				message = JsonServices.getInstance().getCustomerProfileById(id);
				request.setAttribute("json", true);
			} catch (UnknownUserIdException e) {
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
