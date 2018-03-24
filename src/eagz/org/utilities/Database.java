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
	private Encrypt md;
	
	public Database(){
		try {
			Class.forName(prop.getValue("dbDriver"));
			this.con= DriverManager.getConnection(prop.getValue("dbUrl"),prop.getValue("dbUser"),prop.getValue("dbPassword"));
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}

	public boolean checkUser(String email, String password) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkuser"));
			this.pstmt.setString(1, email);
			this.pstmt.setString(2,password);
			this.rs = pstmt.executeQuery();
			state = this.rs.next();
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
			System.out.println("is admin? "+ state);
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
			boolean state1 = this.rs.next();
			System.out.println("Results:"+ state1);
			if(state1) {
				System.out.println("Email exists");
			} else{
				state = true;
				System.out.println("Sign up completed");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return state;
	}
	
	public boolean checkVideo(String filename) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkVideo"));
			this.pstmt.setString(1, filename);
			this.rs = this.pstmt.executeQuery();
			state = this.rs.next();
		} catch (SQLException e) {
		e.printStackTrace();
		}		
		return state;
	}
	
	public boolean newVideo(String name, String filename,String descripcio) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_newVideo"));
			this.pstmt.setString(1, prop.getValue("baseDir"));
			this.pstmt.setString(2, name);
			this.pstmt.setString(3, filename);
			this.pstmt.setString(4, descripcio);
			this.rs = this.pstmt.executeQuery();
			state = this.rs.next();
		} catch (SQLException e) {
		e.printStackTrace();
		}		
		return state;
	}
	
	
	public boolean commentVideo(int commentId, int vidId,int id, String comment) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_newComment"));
			this.pstmt.setInt(1, commentId);
			this.pstmt.setInt(2, vidId);
			this.pstmt.setInt(3, id);
			this.pstmt.setString(4, comment);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
	
	public boolean newAccount(String name, String lastname, String username, String password,String email ) {
		boolean state = false;
		try {
			md = new Encrypt(password);
			this.pstmt = con.prepareStatement(prop.getValue("query_new"));
			this.pstmt.setString(1,name);
			this.pstmt.setString(2,lastname);
			this.pstmt.setString(3,username);
			this.pstmt.setString(4,md.returnEncrypt());
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
	
	public int userId(String email) {
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_getId"));
		    this.pstmt.setString(1, email);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("id");
				return id;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}
	
	public int videoId(String filename) {
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_videoId"));
		    this.pstmt.setString(1,filename);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("media_id");
				return id;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}
	
	public void closeCon() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}