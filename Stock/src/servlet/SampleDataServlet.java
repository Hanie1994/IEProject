package servlet;

//import org.apache.tomcat.util.http.fileupload.IOUtils;
import service.SampleDataLoader;
//import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class SampleDataServlet{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream input = request.getServletContext().getResourceAsStream("sampleData.txt");
        String resp = SampleDataLoader.getInstance().load(input);

        response.getWriter().print(resp);
    	
    	String msg = "done";
    	return msg;

    }

}