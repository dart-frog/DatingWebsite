import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Global {
	public static boolean checkSessionID(){
		return false;
	}
	public static boolean addNewUser(String username, String password){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String SQL = "INSERT INTO datingsite.RegisteredUsers (email, password) VALUES (?,?)"; 
			PreparedStatement pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, username);
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
	
}
