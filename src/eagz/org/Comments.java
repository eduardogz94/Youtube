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

import eagz.org.utilities.Database;

@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Comments() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		
		String filename = reqBody.getString("filename");
		String comment = reqBody.getString("comment"); 
		int ses = (int) request.getSession(false).getAttribute("user_id");
		Database db = new Database();
		db.checkUser(email, password);
		db.commentVideo(15, db.videoId(filename), 38, comment);
		json.put("status", "200").put("response", "commented");
		System.out.println("Comment-> " + comment + " Video-> " + filename);
		out.println(json.toString());
	}
}

