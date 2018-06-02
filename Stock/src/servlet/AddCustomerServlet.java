package servlet;

import exception.MismatchedParametersException;
import exception.RepeatedIdException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EmailValidator;

import java.io.IOException;
import java.util.ArrayList;

import logic.Core;


public class AddCustomerServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String message = "successfully added" ;
    	
    	boolean hasError = false;
		ArrayList<String> errMessage = new ArrayList<String>();
		
    	String id = request.getParameter("id");
        String name = request.getParameter("name");
        String family = request.getParameter("family");
        String password = request.getParameter("password");
        String repeat = request.getParameter("repeat");
        String email = request.getParameter("email");
        
        if(id==null || id.equals("")){
        	hasError = true ;
        	errMessage.add("id can not be empty");
        }
        
        if(name==null || name.equals("")){
        	hasError = true ;
        	errMessage.add("name can not be empty");
        }
        
        if(family==null || family.equals("")){
        	hasError = true ;
        	errMessage.add("family can not be empty");
        }
        
        if(password==null || password.equals("")){
        	hasError = true ;
        	errMessage.add("password can not be empty");
        }
        
        if(repeat==null || repeat.equals("")){
        	hasError = true ;
        	errMessage.add("repeat can not be empty");
        }
        
        if(!password.equals(repeat)){
        	hasError = true;
        	errMessage.add("password and repeat don't match");
        }
        
        if(email==null || email.equals("")){
        	hasError = true ;
        	errMessage.add("email can not be empty");
        }
        if(!EmailValidator.getInstance().validate(email)){
        	hasError = true ;
        	errMessage.add("email format is not valid");
        }
        
        if(!hasError){
        	try {
				Core.getInstance().addCustomer(id, name, family, password, email);
			} catch (RepeatedIdException | MismatchedParametersException e) {
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
