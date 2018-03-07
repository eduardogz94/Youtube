package eagz.org.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {
	public ResultSet rs;
	private PreparedStatement pstmt;
	private Connection con;
	private PropertiesReader prop = PropertiesReader.getInstance();
	
	public Database(){
		try {
			Class.forName(prop.getValue("dbDriver"));
			this.con= DriverManager.getConnection(prop.getValue("dbUrl"),prop.getValue("dbUser"),prop.getValue("dbPassword"));
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}

	public  boolean checkUser(String email) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkuser"));
			this.pstmt.setString(1,email);
			this.rs = pstmt.executeQuery();
			state = this.rs.next();
			System.out.println(state);
			} catch (Exception e) {
				e.getStackTrace();
			}
		return state;
	}
	
	public boolean checkAdmin(String email) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_admin"));
			this.pstmt.setString(1,email);
			this.rs = this.pstmt.executeQuery();
			rs.next();
			if(this.rs.getString("type_id").equals("1")) {
				state = true;
			System.out.println(state);
			}
			
		} catch(Exception e) {
			e.getStackTrace();
		}
		return state;
	}
	
	public void closeCon() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}