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

	public  boolean checkUser(String email, String password) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkuser"));
			this.pstmt.setString(1, email);
			this.pstmt.setString(2,password);
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
	
	public boolean checkSign(String email) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkusersign"));
			this.pstmt.setString(1, email);
			this.rs = this.pstmt.executeQuery();
			state = this.rs.next();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return state;
	}
	
	public boolean newAccount(String name, String lastname, String username, String password,String email ) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_new"));
			this.pstmt.setString(1,name);
			this.pstmt.setString(2,lastname);
			this.pstmt.setString(3,username);
			this.pstmt.setString(4,password);
			this.pstmt.setString(5,email);
			this.rs = this.pstmt.executeQuery();
			state = this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return state;
	}
	
	public boolean newType(String descrip) {
		int type_id = 2;
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_newType"));
			this.pstmt.setInt(1,type_id);
			this.pstmt.setString(2,descrip);
			this.rs = this.pstmt.executeQuery();
			state = this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
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