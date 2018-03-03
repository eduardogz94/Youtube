package eagz.org;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {
	protected Connection conn;
	protected Properties prop = new Properties();
	protected InputStream is = null;
	
	public Database() {
		try {
			is = new FileInputStream("C:\\Users\\Eduardo\\Documents\\GitHub\\Youtube\\src\\eagz\\org\\config.txt");
			prop.load(is);
	    	Class.forName(prop.getProperty("jdbc.driver"));
			conn = DriverManager.getConnection(prop.getProperty("jdbc.url"),
					prop.getProperty("jdbc.user"), prop.getProperty("jdbc.password"));
			System.out.println("conectado");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
