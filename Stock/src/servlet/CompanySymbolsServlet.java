package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.InvalidCompanyException;

import model.Customer;

public class CompanySymbolsServlet {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer company = (Customer) session.getAttribute("user");
		 
		String companyId = company.getId();
		 
		response.setContentType("application/json");
		 
		String message = "";
		
		try {
			message = service.JsonServices.getInstance().getCompanySymbolList(companyId);
		} catch (InvalidCompanyException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		
		return message;
	}
	 
}
