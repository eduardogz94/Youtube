package eagz.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import eagz.org.utilities.Encrypt;
import eagz.org.utilities.PropertiesReader;
import eagz.org.utilities.SingleDB;


public class Database {
	public ResultSet rs;
	private PreparedStatement pstmt;
	private Connection con;
	private PropertiesReader prop = PropertiesReader.getInstance();
	private Encrypt md;
	public ArrayList<String> arr = new ArrayList<String>();
	
	public Database(){
		try {
			SingleDB db = SingleDB.getInstance();
			Class.forName(db.getDriver());
			this.con= DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
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
			}
		} catch(Exception e) {
			e.getStackTrace();
		}
		return state;
	}
	
	public boolean checkLike(int videoId, int user) {
		boolean state = false;
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_checkLike"));
			this.pstmt.setInt(1, videoId);
			this.pstmt.setInt(2, user);
			this.rs = this.pstmt.executeQuery();
			rs.next();
			if(this.rs.getString("is_like").equals("1")) {
				state = true;
			}
		}catch(Exception e) {
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
	
	public void newVideo(String name, String filename,String descripcio) {
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_newVideo"));
			this.pstmt.setString(1, prop.getValue("baseDir"));
			this.pstmt.setString(2, name);
			this.pstmt.setString(3, filename);
			this.pstmt.setString(4, descripcio);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
		e.printStackTrace();
		}		
	}
	
	public void newAccount(String name, String lastname, String username, String password,String email ) {
		try {
			md = new Encrypt(password);
			this.pstmt = con.prepareStatement(prop.getValue("query_new"));
			this.pstmt.setString(1,name);
			this.pstmt.setString(2,lastname);
			this.pstmt.setString(3,username);
			this.pstmt.setString(4,md.returnEncrypt());
			this.pstmt.setString(5,email);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					id = this.rs.getInt("id_user");
				return id;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}
	
	public int commentId() {
		int cid = 0;
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_commentId"));
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("comment_id");
					cid = id + 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return cid;
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
	
	
	public void like(int user, int video, int likes) {
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_like"));
			this.pstmt.setInt(1,user);
			this.pstmt.setInt(2,video);
			this.pstmt.setInt(3,likes);
			this.pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void commentVideo(int commentId, int vidId,int id, String comment) {
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
	}
	
	public void deleteLike(int user, int video) {
		try {
			this.pstmt = con.prepareStatement(prop.getValue("query_deleteLike"));
			this.pstmt.setInt(1, user);
			this.pstmt.setInt(2, video);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public ArrayList<String> getComment(int vid) {
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_getComment"));
			this.pstmt.setInt(1, vid);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					arr.add(this.rs.getString("comment_text"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return arr;
	}
	
	public int getLike(int videoId) {
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement(prop.getValue("query_getLike"));
			this.pstmt.setInt(1,videoId);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("n_like");
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