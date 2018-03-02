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

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.isNew()) {
			json.put("status", "not logged in");
			session.invalidate();
		} else {
			json.put("admin", session.getAttribute("admin"))
				.put("name", session.getAttribute("name"))
				.put("lastname", session.getAttribute("last name"))
				.put("password", session.getAttribute("password"))
				.put("email", session.getAttribute("email"))
				.put("id_user", session.getAttribute("user_id"));
		}
		out.print(json.toString());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(reqBody);
		JSONObject json = new JSONObject();	
		if(session.isNew()) {
			storeValue(reqBody.getInt("user_id"), /*reqBody.getBoolean("admin"),*/ reqBody.getString("name"), 
					   reqBody.getString("lastname"), reqBody.getString("username"), 
					   reqBody.getString("password"), reqBody.getString("email"), 
					   session);
		}
		else {
			storeValue(reqBody.getInt("user_id"), /*reqBody.getBoolean("admin"),*/ reqBody.getString("name"), 
					   reqBody.getString("lastname"), reqBody.getString("username"), 
					   reqBody.getString("password"), reqBody.getString("email"), 
					   session);
		}
			out.println(json.toString());
	}

	
	private void storeValue(int id,/* boolean admin,*/String name, String lastname, String username,String password, String email, HttpSession session) {
		if(email == null) {
			session.setAttribute("user_id", "");
			//session.setAttribute("admin", "");
			session.setAttribute("name", "");
			session.setAttribute("lastname", "");
			session.setAttribute("username", "");
			session.setAttribute("password", "");
		} 
		else {
			session.setAttribute("user_id", id);
		//	session.setAttribute("admin", admin);
			session.setAttribute("name", name);
			session.setAttribute("lastname", lastname);
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			session.setAttribute("email", email);
		}
	}
}
