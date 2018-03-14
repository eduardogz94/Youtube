package eagz.org;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcat {
	
	static Login login = new Login();
	static Signup signup = new Signup();
	static Logout logout = new Logout();

	public static void main(String[] args) throws IOException, LifecycleException, ServletException {

		Integer port = 8080;
		Tomcat tomcat = new Tomcat();
		Context ctxt = null;
		String web_app = "WebContent";
		System.out.println("Tomcat levantara en el puerto: " + port);
		ctxt = tomcat.addWebapp("/", System.getProperty("user.dir") + "\\"+web_app);
		System.out.printf("/", System.getProperty("user.dir") + "\\"+web_app);	
		
		Tomcat.addServlet(ctxt, "Login", login);
		ctxt.addServletMappingDecoded("/Login", "Login");
		
		Tomcat.addServlet(ctxt, "Logout", logout);
		ctxt.addServletMappingDecoded("/Logout", "Logout");
		
		Tomcat.addServlet(ctxt, "Signup", signup );
		ctxt.addServletMappingDecoded("/Signup", "Signup");
		
		tomcat.start();
		tomcat.getServer().await();
	}
}