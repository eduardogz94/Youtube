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

import eagz.org.utilities.Encrypt;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Encrypt md;
	Database db = new Database();
	
    public Login() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.isNew()) {
			json.put("status", "401")
			.put("response", "not logged in");
			session.invalidate();
		} else {
			json.put("status", "200")
				.put("response", "logged in")
				.put("password", session.getAttribute("password"))
				.put("email", session.getAttribute("email"));
		}
		out.print(json.toString());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		if(session.isNew()) {
			String email = reqBody.getString("email"); 
			String pass = reqBody.getString("password");
			int userId = db.userId(email);
			md = new Encrypt(pass);
			if(db.checkUser(email,md.returnEncrypt()) == true) {
				if (db.checkAdmin(email)) {
					storeValue(email, pass, userId, session);
					json.put("status", "200").put("response", "admin");
		        	System.out.println("------------------------------------------------------------");
					System.out.println("Admin-> " + email);
				}else {
					storeValue(email, pass,userId, session);
					json.put("status", "200").put("response", "user");
		        	System.out.println("------------------------------------------------------------");
					System.out.println("User-> " + email);
				}
			}else {
				json.put("response", "Wrong email or password").put("status", "400");
				session.invalidate();
				System.out.println("Wrong data --");
			}
		}else {
			json.put("response", "you're logged in").put("status", "400");
			System.out.println("Already log --");
		}
		out.println(json.toString());
	}		
	
	public void storeValue(String email, String password,int userId,HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("id", "");
		} else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			session.setAttribute("id", userId);
		}
	}
}