package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import eagz.org.utilities.Database;

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
			json.put("email", session.getAttribute("email"))
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
		System.out.println(reqBody);
		JSONObject b = new JSONObject();	
		try {
			Database db = new Database();
			while (db.rs.next()) {
				b.put("email", db.rs.getString(1))
			 	 .put("password", db.rs.getString(2));
			 }
			
			if(session.isNew()) {
				storeValue(reqBody.getString("email"), reqBody.getString("password"), session);
				session.invalidate();
			}
			else {
				storeValue(reqBody.getString("email"), reqBody.getString("password"),   session);
			}
				out.println(b);
				System.out.println(b);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void storeValue(String email, String password,HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
		} 
		else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
		}
	}
}
