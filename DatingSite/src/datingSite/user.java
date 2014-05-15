package datingSite;

import java.sql.Connection;
import java.sql.ResultSet;

public class user {
	private String firstName;
	private String lastName;
	private String grade;
	private boolean gender;
	private String description;
	private String personality;
	public user(String i){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String query = "SELECT * FROM datingsite.Users WHERE UserID = ?";
			
		}
		catch(Exception e) {
		e.printStackTrace();
		}
	}
}
