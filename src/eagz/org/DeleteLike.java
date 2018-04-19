package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
@WebServlet("/DeleteLike")
public class DeleteLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = new Database();

	public DeleteLike() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String filename = request.getParameter("filename");
		String email = (String) request.getSession(false).getAttribute("email");
		int id = db.userId(email);
		int videoId = db.videoId(filename);
		
		if(db.checkLike(videoId,id) == true) {
			db.deleteLike(id,videoId);
			json.put("status", "200").put("response", "like deleted");
			System.out.println("Deleted like");
		}else {
			json.put("status", "403").put("response", "no like");
		}
		out.println(json.toString());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
