package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();

        String lowerPrice = request.getParameter("lowerPrice");
        if(lowerPrice==null || lowerPrice.equals("")){
            hasError = true ;
            errMessage.add("lowerPrice can not be empty");
        }
        
        
        String upperPrice = request.getParameter("upperPrice");
        if(upperPrice==null || upperPrice.equals("")){
            hasError = true ;
            errMessage.add("upperPrice can not be empty");
        }
 

        String id = request.getParameter("id");
        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }
    
        String name = request.getParameter("name");
        if(name==null || name.equals("")){
            hasError = true ;
            errMessage.add("name can not be empty");
        }

        String symbol = request.getParameter("symbol");
        if(symbol==null || symbol.equals("")){
            hasError = true ;
            errMessage.add("symbol can not be empty");
        }

        String fromDate = request.getParameter("fromDate");
        if(fromDate==null || fromDate.equals("")){
            hasError = true ;
            errMessage.add("fromDate can not be empty");
        }

        String toDate = request.getParameter("toDate");
        if(toDate==null || toDate.equals("")){
            hasError = true ;
            errMessage.add("toDate can not be empty");
        }

        String message = "" ;

        if(!hasError){
            response.setContentType("application/json");
            message = service.JsonServices.getInstance().filterExchanges(lowerPrice, upperPrice, id, name, symbol, fromDate, toDate);
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;
    }

}
