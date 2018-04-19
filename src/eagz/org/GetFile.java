package eagz.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import eagz.org.utilities.PropertiesReader;

@WebServlet("/GetFile")
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = new Database();
	private PropertiesReader prop = new PropertiesReader();

    public GetFile() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("file");
        String name = request.getParameter("filename");
        response.setHeader("Content-disposition","attachment; filename="+name);
        File my_file = new File(prop.getValue("baseDir")+"/"+name);	
        response.sendRedirect("http://localhost:8080/index.html");
        if (db.checkVideo(name) == true) {
        	System.out.println("------------------------------------------------------------");
        	System.out.println("Get-> " +my_file);
        	
        	OutputStream out = response.getOutputStream();
        	FileInputStream in = new FileInputStream(my_file);
        	
        	byte[] buffer = new byte[4096];
        	int length;
        		while ((length = in.read(buffer)) > 0){
        			out.write(buffer, 0, length);
        		}
	        in.close();
	        out.flush();
	        out.close();
	    } else {
        	JSONObject json = new JSONObject();
    		PrintWriter out = response.getWriter();
    		json.put("res","not found");
    		out.println(json.toString());
        }        
    }
    
}