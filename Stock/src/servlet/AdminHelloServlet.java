package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminHelloServlet {
    
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doGet(req, resp);

        //resp.getOutputStream().print("hey dude");
    	return "Hiiiiiiiii" ;
    }
}
