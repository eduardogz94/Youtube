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
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.isNew()) {
			json.put("status", "not registered");
			session.invalidate();
		} else {
			json.put("email", session.getAttribute("email"))
				.put("name", session.getAttribute("name"))
				.put("lastname", session.getAttribute("lastname"))
				.put("username", session.getAttribute("username"))
				.put("password", session.getAttribute("password"));
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
		System.out.println(reqBody.getInt("user_id"));
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

	private void storeValue(int int1, String string, String string2, String string3, String string4, String string5,
			HttpSession session) {
		// TODO Auto-generated method stub
		if(string2 == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("email", "");
			session.setAttribute("password", "");
		} 
		else {
			session.setAttribute("email", int1);
			session.setAttribute("password", string);
		}
	}

}
