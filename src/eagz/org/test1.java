package eagz.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Database db = new Database();
		String query = PropertiesReader.getInstance().getValue("query2");
		System.out.println(query);
		json.put("query", db.executeQuery(query,reqBody.getString("email")));
		System.out.println(query+reqBody.getString("email"));
		db.closeCon();
		out.print(json.toString());
	}
}