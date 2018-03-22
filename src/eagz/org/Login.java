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
 * Servlet implementation class test1
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Login() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.isNew()) {
			json.put("response", "not logged in").put("status", "200");
			session.invalidate();
		} else {
			json.put("status", "200")
				.put("response", "logged in")
				.put("password", session.getAttribute("password"))
				.put("email", session.getAttribute("email"))
				.put("username", session.getAttribute("username"))
				.put("name", session.getAttribute("name"));
		}
		out.print(json.toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Database db = new Database();
		if(session.isNew()) {
			String email = reqBody.getString("email"); String pass = reqBody.getString("password");
			if(db.checkUser(email,pass) == true) {
				if (db.checkAdmin(email)) {
					storeValue(email, pass
							, session);
					json.put("status", "200").put("response", "admin");
				}else {
					storeValue(email, pass, session);
					json.put("status", "200").put("response", "user");
				}
			}else {
				json.put("response", "Wrong email or password").put("status", "400");
				session.invalidate();
			}
		}else {
			json.put("response", "you're logged in").put("status", "400");
		}out.println(json.toString());
	}		
	
	public void storeValue(String email, String password,HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
		} else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
		}
	}
}