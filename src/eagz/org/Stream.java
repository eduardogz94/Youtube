package eagz.org;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eagz.org.utilities.PropertiesReader;

@WebServlet("/Stream")
public class Stream extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PropertiesReader prop = PropertiesReader.getInstance();

    public Stream() {
        super();	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		String name = request.getParameter("filename");
		
		InputStream in = new FileInputStream (prop.getValue("baseDir")+ "/" + name);
		
		System.out.println("Streaming-> "+ name);
		
		String mimeType = "video/mp4";
		
		byte[] bytes = new byte[1024];
		int bytesRead;
		response.setContentType(mimeType);

		while ((bytesRead = in.read(bytes)) != -1) {
		    out.write(bytes, 0, bytesRead);
		}
		in.close();
		out.close();		
		}	
}