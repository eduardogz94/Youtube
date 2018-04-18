package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = new Database();  
	
	
    public Comments() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	JSONObject json = new JSONObject();
		
		String filename = request.getParameter("filename");
		if(db.checkVideo(filename) == true) {
			int videoId = db.videoId(filename);
			ArrayList<String> comment = db.getComment(videoId);
			
			for(int i=0; i<comment.size();i++) {
				System.out.println(comment.get(i)); 
				out.println("Comment: " + comment.get(i));
				comment.remove(i);
			}
						
			
			/*json.put("Comment", comment);
			out.println("Last Comment: " + comment);*/
		}else {
			json.put("status", "404");
			out.println("Video not found");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		
		String filename = reqBody.getString("filename");
		String comment = reqBody.getString("comment"); 
		String email = (String) request.getSession(false).getAttribute("email");
		
		int id = db.userId(email);
		int videoId = db.videoId(filename);
		int commentId = db.commentId();
		
		db.commentVideo(commentId, videoId, id, comment);
		json.put("status", "200").put("response", "commented");
		
		System.out.println("Post Comment -> " + commentId + " Comment: "+ comment + " On Video-> " + filename);
		out.println(json.toString());
	}
}

