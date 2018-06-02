package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import dao.CustomerDao;

import model.Customer;

public class FrontController extends HttpServlet {

	// URLs must have the form /polling/ControllerClass.action
	// the execute() method of the ControllerClass will be called
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        
		String className = request.getServletPath().substring(1 , request.getServletPath().indexOf(".action"));
		if(className.contains("/")){
			className = className.substring(className.indexOf("/")+1);
		}
		//String subPackage ;
		
		//try{
			//subPackage = request.getServletPath().substring(1, request.getServletPath().lastIndexOf("/"));
		//}catch(IndexOutOfBoundsException e){
		//	subPackage = null;
		//}
		
		try {
			
			HttpSession session = request.getSession();
			if (session != null && session.getAttribute("user") == null) {
				Customer user = CustomerDao.getInstance().getCustomerById(request.getRemoteUser());
				if (user != null) {
					session.setAttribute("user", user);
				}
			}
			
			
			Class ctrlClass;
			//if(subPackage != null)
			//	 ctrlClass = Class.forName("servlet." + subPackage + "." + className + "Servlet");
			//else
				 ctrlClass = Class.forName("servlet." + className + "Servlet");
			System.err.println("servlet." + className + "Servlet");
			Method m = ctrlClass.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
			String message = (String)m.invoke(ctrlClass.newInstance(), request, response);
			//request.getRequestDispatcher(forward).forward(request, response);
			
			response.getWriter().println(message);
			
			ArrayList<String> err = (ArrayList<String>) request.getAttribute("errors");
			if(err != null){
				for(String e : err){
					response.getWriter().println(e);
				}
			}
			
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
			response.setContentType("text/html");
			if (ex.getTargetException() instanceof SQLException)
				response.getOutputStream().println("Error in accessing database!<p>Contact system administrator");
			else
				response.getOutputStream().println("Internal system error!<p>Contact system administrator");
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html");
			response.getOutputStream().println("Internal system error!<p>Contact system administrator");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
