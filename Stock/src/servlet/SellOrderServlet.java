package servlet;

import exception.*;
import logic.Core;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;


public class SellOrderServlet{
    
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//String id = request.getParameter("id");
		HttpSession session = request.getSession();
		Customer user = (Customer) session.getAttribute("user");
		
    	String id = user.getId();
    	
        String inst = request.getParameter("instrument");
        String price = request.getParameter("price");
        //int quantity = Integer.parseInt(request.getParameter("quantity"));
        int quantity = 0;
        String quantityString = request.getParameter("quantity");
        String type = request.getParameter("type");

        boolean hasError = false;
        ArrayList<String> errMessage = new ArrayList<String>();

        if(id==null || id.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }

        if(inst==null || inst.equals("")){
            hasError = true ;
            errMessage.add("instrument can not be empty");
        }

        if(price==null || price.equals("")){
            hasError = true ;
            errMessage.add("price can not be empty");
        }
        int priceInt = 0;
        try{
            priceInt = Integer.parseInt(price);
        }catch(NumberFormatException e){
            hasError = true;
            errMessage.add("enter valid price");
        }
        if(priceInt < 0){
        	hasError = true;
        	errMessage.add("price can not be negative");
        }
        

        if(quantityString==null || quantityString.equals("")){
            hasError = true ;
            errMessage.add("quantity can not be empty");
        }
        try{
            quantity = Integer.parseInt(quantityString);
        }catch(NumberFormatException e){
            hasError = true;
            errMessage.add("enter valid number for quantity");
        }
        
        if(quantity < 0){
        	hasError = true;
        	errMessage.add("quantity can not be negative");
        }

        if(type==null || type.equals("")){
            hasError = true ;
            errMessage.add("id can not be empty");
        }

        String message = "" ;

        if(!hasError){
            try {
                message = Core.getInstance().sellOrder(id, inst, price , quantity , type);
            } catch (InvalidOrderTypeException | OrderIsDeclinedException | InvalidSymbolIdException | MismatchedParametersException | OrderIsQueuedException | UnknownUserIdException | NotEnoughShareException | OrderIsLimitedException e) {
                message = e.getMessage();
            }
            Core.getInstance().log();
            //request.setAttribute("resultMessage", message);
            //request.getRequestDispatcher("../result.jsp").forward(request, response);
            response.setContentType("text/plain");
            response.getWriter().print(message);
        }

        else{
            message = "some errors occured" ;
            request.setAttribute("errors", errMessage);
        }

        return message;
    }
}
