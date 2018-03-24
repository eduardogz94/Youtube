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

@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Signup() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
 		HttpSession session = request.getSession();
 		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
 		JSONObject json = new JSONObject();
 		Database db = new Database();
 		
 		String email = reqBody.getString("email"); 
 		String password = reqBody.getString("password");
 		String name = reqBody.getString("name"); 
 		String lastname = reqBody.getString("lastname");
 		String username = reqBody.getString("username");
 		
 		if(session.isNew()) {
 			if(db.checkSign(email)) {
 		    	db.newAccount(name,lastname,username,password,email);
 		    	json.put("response", "signup finished").put("status", "200");
 		    	System.out.println("Register --");
 		    	session.invalidate();
 			}else {
 		    	json.put("response", "email already used").put("status", "400");
 		    	System.out.println("Fail register --");
 				session.invalidate(); 
 			}
 		out.println(json.toString());
 		}
   }   
}