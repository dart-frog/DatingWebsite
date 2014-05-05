package datingSite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet("/Global")
public class Global {
	public static final String schema = "datingsite";
	public static final String sessionsTable = schema + "." + "Sessions";
	public static final String usersTable = schema + "." + "Users";
	public static final String userDataTable = schema + "." + "UserData";
	
	private static Cookie getSessionCookie(Cookie[] cookies) {
		if(cookies == null) return null;
		for(Cookie c : cookies) {
			if(c.getName().equals("sessionID")) {
				return c;
			}
		}
		return null;
	}
	
	private static ResultSet executeQueryWithParams(String query, String...params) throws SQLException {
		Connection con = new Connect().getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		for(int i = 0; i < params.length; i++) {
			pstmt.setString(i+1, params[i]);
		}
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	public static boolean isSessionValid(HttpServletRequest request) {
		return getUserIDFromRequest(request) != -1;
	}
	
	public static int getUserIDFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = getSessionCookie(cookies);
		if(cookie == null) return -1;
		String sessionID = cookie.getValue().trim();
		
		try {
			String query = "SELECT UserID FROM datingsite.Sessions WHERE SessionID = ?";
			ResultSet rs = executeQueryWithParams(query, sessionID);
			rs.next();
			return rs.getInt(1);
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static boolean createAndAddNewUser(String email, String password){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String SQL = "INSERT INTO datingsite.Users (email, password) VALUES (?,?)"; 
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
	
	public static String getUserInfo(String infoType, String userID) {
		try {
			String query = "SELECT ? FROM datingsite.UserData WHERE UserID = ?;";
			ResultSet rs = executeQueryWithParams(query, infoType, userID);
			rs.next();
			return rs.getString(1);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String, String> getAllUserInfo(String userID) {
		try {
			String query = "SELECT * FROM datingsite.UserData WHERE UserID = ?;";
			ResultSet rs = executeQueryWithParams(query, userID);
			rs.next();
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			Map<String, String> info = new HashMap<String, String>();
			for(int i = 1; i <= columns; i++) {
				String columnName = md.getColumnName(i);
				info.put(columnName, rs.getString(columnName));
			}
			return info;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Cookie tryLogIn(String email, String password){
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String query = "SELECT ID FROM datingsite.Users WHERE Email = ? AND Password = ?;";
			ResultSet rs = executeQueryWithParams(query, email, password);
			rs.next();
			if(rs.isAfterLast()) return null;
			String userID = rs.getString(1);
			rs.close();
			
			String sessionID = UUID.randomUUID().toString();
			query = "INSERT INTO datingsite.Sessions (UserID, SessionID) VALUES (?,?)"; 
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, userID);
			pstmt.setString(2, sessionID);
			int count = pstmt.executeUpdate();
			System.out.println("ROWS AFFECTED:" + count);
			if(count == 0) throw new IllegalStateException();
			pstmt.close();
			
			return new Cookie("sessionID", userID);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isEmailAvalible(String email){
		Connection con = new Connect().getConnection();
		try{
			String SQL = "SELECT email FROM datingsite.Users;";
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
	
	public static String hash(String password){
		return Integer.toString(password.hashCode());
	}
	
	public static String getError(HttpSession session) {
		String out = "";
		if(session.getAttribute("error") != null) {
			out = String.format("<span style=\"color:red;\">%s</span>", session.getAttribute("error"));
			session.setAttribute("error", null);
		}
		return out;
	}
	
	public static void setError(HttpServletRequest request, String error) {
		setError(request.getSession(false), error);
	}
	
	public static void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
}
