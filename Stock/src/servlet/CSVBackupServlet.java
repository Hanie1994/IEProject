package servlet;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CSVBackupServlet{
    
    public String execute(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"backup.csv\"");
        try
        {
            OutputStream outputStream = response.getOutputStream();
            String outputResult = service.csvBackup.getInstance().make_backup();;
            outputStream.write(outputResult.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        String msg = "done";
        return msg;

    }
}