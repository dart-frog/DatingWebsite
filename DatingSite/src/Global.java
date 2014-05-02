import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Global {
	public static boolean checkSessionID(){
		return false;
	}
	public static boolean addNewUser(String email, String password){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String SQL = "INSERT INTO datingsite.RegisteredUsers (email, password) VALUES (?,?)"; 
			PreparedStatement pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			int count = pstmt.executeUpdate();
			System.out.println("ROWS AFFECTED:" + count);
			pstmt.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	
	}
	public static boolean doesUserExists(String email, String password){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String SQL = "SELECT email, password FROM datingsite.RegisteredUsers;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			
			Map users = new HashMap();
			while(rs.next()){
				users.put(rs.getString("email"), rs.getString("password"));
			}
			if(users.get(email).equals(password)){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isEmailAvalible(String email){
		Connection con = new Connect().getConnection();
		try{
			String SQL = "SELECT email FROM datingsite.RegisteredUsers;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()){
				if (rs.getString("email").equals(email)){
					return false; 
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	public static int hash(String password){
		return password.hashCode();
	}
}
