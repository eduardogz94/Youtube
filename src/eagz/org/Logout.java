package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Logout() { 
    	super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String nombre = session.getAttribute("email").toString();
		JSONObject json = new JSONObject();
		if(session.isNew()) { 
			json.put("status", "You're not logged in");
			session.invalidate();
		}
		else { 
			json.put("status","You're logged out");
			session.invalidate();
		}
		out.print("Logout para: "+nombre);
	}
	
}
