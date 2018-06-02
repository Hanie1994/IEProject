package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Core;
import exception.RepeatedRoleException;


public class AddRoleServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String id = request.getParameter("id");
        String role = request.getParameter("role");
        
        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();
        
        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }        

        if(role==null || role.equals("")){
            hasError = true ;
            errMessage.add("role can not be empty");
        }
        
        
        String message = "" ;

        if(!hasError){
            try {
                message = Core.getInstance().addRole(id, role);
            } catch (RepeatedRoleException e) {
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
