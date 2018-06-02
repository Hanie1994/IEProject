package old;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerCreditById{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");

        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();

        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }
	   
        String message = "successfully added" ;

        if(!hasError){
            response.setContentType("application/json");
            message = service.JsonServices.getInstance().getCustomerCreditById(id);
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;

    }
}
