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
 		
 		String email = reqBody.getString("email"); String password = reqBody.getString("password");
 		String name = reqBody.getString("email"); String lastname = reqBody.getString("lastname");
 		String username = reqBody.getString("username");
 		
 		if(session.isNew()) {
 			if(db.checkUser(email,password) == false) {
 		    	db.newAccount(name,lastname,username,password,email);
 		    	json.put("response", "Signup Finished").put("status", "200");
 				storeValue(name,lastname,username,password,email, session);
 				session.invalidate();
 		    }else {
 		    	json.put("response", "email already used").put("status", "200");
 					storeValue(name,lastname,username,password,email, session);
 					session.invalidate(); 
 			}
 		out.println(json.toString());
 		}
    }   
		
	private void storeValue(String name, String lastname,String password, String username,String email, HttpSession session) {
		if(email == null) {
			session.setAttribute("name", "");
			session.setAttribute("lastname", "");
			session.setAttribute("username", "");
			session.setAttribute("password", "");
			session.setAttribute("email", "");
		} 
		
		else {
			session.setAttribute("name", name);
			session.setAttribute("lastname", lastname);
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			session.setAttribute("email", email);
		}
	}

}
