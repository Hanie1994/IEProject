package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet{
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.getRequestDispatcher("customer/add.jsp").forward(request, response);
        //request.getRequestDispatcher("customer/add.jsp").

        // Set response content type
        response.setContentType("text/html");

        // New location to be redirected
        String site = new String("customer/add.jsp");

        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);

        String msg = "done";
        return msg;
    }
}
