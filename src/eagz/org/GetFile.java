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

import eagz.org.utilities.Database;

@WebServlet("/GetFile")
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database db = new Database();

    public GetFile() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	response.setContentType("file");
        String name = request.getParameter("filename");
        
        response.setHeader("Content-disposition","attachment; filename="+name);
        File my_file = new File("./WebContent/videos"+"/"+name);
        
        if (db.checkVideo(name)) {
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
}