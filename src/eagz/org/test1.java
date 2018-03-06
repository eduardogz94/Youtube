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
import eagz.org.utilities.PropertiesReader;

/**
 * Servlet implementation class test1
 */
@WebServlet("/test1")
public class test1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();	
		Database db = new Database();
		String query = PropertiesReader.getInstance().getValue("query1");
		System.out.println("asd"+PropertiesReader.getInstance().getValue("query1"));
		json.put("query", db.executeQuery(query));
		db.closeCon();
		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Database db = new Database();
		
		if(session.isNew()) {
			String email = reqBody.getString("email");
			String pass = reqBody.getString("password");
			if(db.checkUser(email, pass) == true) {
				storeValue(email, pass, session);
				json.put("status", "200");
			} else {
				storeValue(email, pass, session);
				json.put("status", "200");
			} session.invalidate();
		}else {
			json.put("status", "you're already logged in");
		}
		out.println(json.toString());
	}
		public void storeValue(String email, String password,HttpSession session) {
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