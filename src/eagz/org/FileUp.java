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

@MultipartConfig()
@WebServlet("/FileUp")

public class FileUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			OutputStream os = null;
			try {
				String baseDir = "./videos";
				os = new FileOutputStream(baseDir + "/" + this.getFileName(file));
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
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

	// Esta funcion permite obtener el nombre del archivo
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}	