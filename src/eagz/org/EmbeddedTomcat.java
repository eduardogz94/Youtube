package eagz.org;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcat {
	
	static Login servlet = new Login();

	public static void main(String[] args) throws IOException, LifecycleException, ServletException {

		Integer port = 8080;
		Tomcat tomcat = new Tomcat();
		Context ctxt = null;
		System.out.println("Tomcat levantara en el puerto: " + port);
		ctxt = tomcat.addWebapp("/", System.getProperty("user.dir"));
		Tomcat.addServlet(ctxt, "login", servlet);
		ctxt.addServletMappingDecoded("/login", "login");
		tomcat.start();
		tomcat.getServer().await();
	}
}