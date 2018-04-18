package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/DeleteLike")
public class DeleteLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = new Database();

	public DeleteLike() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String filename = request.getParameter("filename");
		
		System.out.println("Get Likes> " + " On Video->" );
		out.print("Likes :");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
