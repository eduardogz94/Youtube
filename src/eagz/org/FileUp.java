package eagz.org;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import eagz.org.utilities.PropertiesReader;
@MultipartConfig
@WebServlet("/FileUp")

public class FileUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PropertiesReader prop = PropertiesReader.getInstance();
	private Database db = new Database();
	OutputStream os = null;
	
    public FileUp() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("{\"status\":200,\"res\":\"OK/GET\"}");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Part file = request.getPart("file");
			InputStream filecontent = file.getInputStream();
			JSONObject json = new JSONObject();
			PrintWriter out = response.getWriter();
			
			try {
				if(file != null) {
					System.out.println();
					if(db.checkVideo(getFileName(file)) == true){
						json.put("response", "video already exists");
						out.println("video already exists");
					} else {
						db.newVideo(file.getName(), getFileName(file), "descrip");
						System.out.println("Video-> " + file.getName() +" "+ getFileName(file)+ " descrip");
						os = new FileOutputStream(prop.getValue("baseDir") + "/" + this.getFileName(file));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = filecontent.read(bytes)) != -1) {
							os.write(bytes, 0, read);
							out.print("File uploaded");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (filecontent != null) {
					filecontent.close();
				}
				if (os != null) {
					os.close();
				}
			}
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}