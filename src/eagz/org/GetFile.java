package eagz.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetFile")
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetFile() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Find this file id in database to get file name, and file type
    	
        // You must tell the browser the file type you are going to send
        // for example application/pdf, text/plain, text/html, image/jpg
        response.setContentType("file");

        // Assume file name is retrieved from database
        String name = request.getParameter("name");
        response.setHeader("Content-disposition","attachment; filename="+name);
        File my_file = new File("./videos"+"/"+name);
        System.out.println(my_file);
        
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        
        byte[] buffer = new byte[4096];
        int length;
        	while ((length = in.read(buffer)) > 0){
        		out.write(buffer, 0, length);
        	}
        
        	in.close();
        out.flush();
	}
}