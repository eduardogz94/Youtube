package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import eagz.org.utilities.Database;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
 		HttpSession session = request.getSession();
 		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
 		JSONObject json = new JSONObject();
 		Database db = new Database();
 		if(session.isNew()) {
 			if(db.checkUser(reqBody.getString("email")) == true) {
 				json.put("status", "email already used");
 				storeValue(reqBody.getString("email"), reqBody.getString("username"), 
 						   reqBody.getString("pass"), reqBody.getInt("type_id"), session);
 				session.invalidate();
 			} else {
 				json.put("status", "200");
 				storeValue(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), 
 						   reqBody.getInt("type_id"), session);
 			}
 		} else {
 			json.put("status", "you are registered already");
 			storeValue(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), reqBody.getInt("type_id"), session);
 			session.invalidate(); 
 		}
 		out.println(json.toString());
 	}

	private void storeValue(String email, String username, String password, int type_id, HttpSession session) {
		// TODO Auto-generated method stub
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("username", "");
			session.setAttribute("type_id", "");
		} 
		else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			session.setAttribute("username", username);
			session.setAttribute("type_id", type_id);
		}
	}

}
