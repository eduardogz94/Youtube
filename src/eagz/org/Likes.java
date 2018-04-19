package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/Likes")
public class Likes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = new Database();   
    public Likes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String filename = request.getParameter("filename");
		
		int videoId = db.videoId(filename);
		int like = db.getLike(videoId);
		
		System.out.println("Get Likes> " + like + " On Video->"  + videoId);
		out.print("Likes :" + like);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		
		String filename = reqBody.getString("filename");
		String email = (String) request.getSession(false).getAttribute("email");
		int id = db.userId(email);
		int videoId = db.videoId(filename);
		int like = db.getLike(videoId) + 1;
		if(db.checkLike(videoId,id) == true) {
			json.put("status", "403").put("response", "already liked");
			System.out.println("Already has a like");
		}else {
			db.like(id, videoId, like);
			json.put("status", "200").put("response", "liked");
			System.out.println("Liked -> " + videoId + " User -> " + id + " Like number " + like);
		}
		out.println(json.toString());
	}
}
