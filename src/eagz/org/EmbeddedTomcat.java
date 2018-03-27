package eagz.org;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcat {
	
	static Login login = new Login();
	static Signup signup = new Signup();
	static Logout logout = new Logout();
	static FileUp fileUp = new FileUp();
	static GetFile getFile = new GetFile();
	static Stream stream = new Stream();
	static Comments comment = new Comments();
	static Likes likes = new Likes();
	
	public static void main(String[] args) throws IOException, LifecycleException, ServletException {

		Integer port = 8080;
		Tomcat tomcat = new Tomcat();
		Context ctxt = null;
		
		Connector con = new Connector();
		con.setPort(port);
		con.setMaxPostSize(60000000);
		tomcat.setConnector(con);
		
		String web_app = "WebContent";
		ctxt = tomcat.addWebapp("/", System.getProperty("user.dir") + "\\" +web_app);
		
		Tomcat.addServlet(ctxt, "Login", login);
		ctxt.addServletMappingDecoded("/Login", "Login");
		
		Tomcat.addServlet(ctxt, "Logout", logout);
		ctxt.addServletMappingDecoded("/Logout", "Logout");
		
		Tomcat.addServlet(ctxt, "Signup", signup );
		ctxt.addServletMappingDecoded("/Signup", "Signup");
		
		Tomcat.addServlet(ctxt, "FileUp", fileUp );
		ctxt.addServletMappingDecoded("/FileUp", "FileUp");
		
		Tomcat.addServlet(ctxt, "GetFile", getFile);
		ctxt.addServletMappingDecoded("/GetFile", "GetFile");
		
		Tomcat.addServlet(ctxt, "Stream", stream);
		ctxt.addServletMappingDecoded("/Stream", "Stream");
		
		Tomcat.addServlet(ctxt, "Comments", comment);
		ctxt.addServletMappingDecoded("/Comments", "Comments");
		
		Tomcat.addServlet(ctxt, "Likes", likes);
		ctxt.addServletMappingDecoded("/Likes", "Likes");
		
		ctxt.setAllowCasualMultipartParsing(true);
		
		tomcat.start();
		tomcat.getServer().await();
	}
}