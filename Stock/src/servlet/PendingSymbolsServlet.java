package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PendingSymbolsServlet{
	
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        String info = service.JsonServices.getInstance().getPendingSymbols();

        return info;

    }

}
