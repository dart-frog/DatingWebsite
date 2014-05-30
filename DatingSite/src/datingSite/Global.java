package datingSite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datingSite.Global.User;

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
	
	private static int executeQueryWithParamsWithoutResults(String query, String...params) throws SQLException {
		Connection con = new Connect().getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		for(int i = 0; i < params.length; i++) {
			pstmt.setString(i+1, params[i]);
		}
		int count = pstmt.executeUpdate();
		return count;
	}
	
	public static boolean isSessionValid(HttpServletRequest request) {
		return getUserFromRequest(request) != null;
	}
	
	public static String getNavBar(){
		return("<ul> \n" +
				"<li> <a href= 'ProfilePage.jsp'> Account Page </a> </li> \n" +
				"<li> <a href= 'MessageHub.jsp'> Message Hub </a> </li> \n" +
				"<li> <a href= 'MatchMaker.jsp'> Match Maker </a> </li> \n" +
				"<li> <a href= 'Lists.jsp'> Lists </a> </li> \n" + 
				"</ul> \n");
	}

	public static String getSessionIDFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = getSessionCookie(cookies);
		if(cookie == null) return "";
		return cookie.getValue().trim();
	}
	
	public static User getUserFromRequest(HttpServletRequest request) {
		String sessionID = getSessionIDFromRequest(request);
		System.out.println(sessionID);
		
		try {
			String query = "SELECT UserID FROM datingsite.Sessions WHERE SessionID = ?";
			ResultSet rs = executeQueryWithParams(query, sessionID);
			rs.next();
			return new User(rs.getString(1));
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getEmailFromUserID(String userID) {
		try {
			String query = "SELECT Email FROM datingsite.Users WHERE ID = ?";
			ResultSet rs = executeQueryWithParams(query, userID);
			rs.next();
			return rs.getString(1);
		} catch(Exception e) {
			e.printStackTrace();
			return "INVALID EMAIL";
		}
	}
	
	public static StatusCodes logOutUser(HttpServletRequest request) { //TODO: Move to LogOutHandler
		if(!isSessionValid(request)) return StatusCodes.SessionInvalid;
		String SessionID = getSessionIDFromRequest(request);
		String query = "DELETE FROM datingsite.Sessions WHERE SessionID = ?";
		try{
			executeQueryWithParamsWithoutResults(query, SessionID);
			return StatusCodes.Success;
		} catch(SQLException e) {
			setError(request, "A SQL error occurred.");
			return StatusCodes.SQLError;
		} catch(Exception e) {
			setError(request, "An error occurred.");
			return StatusCodes.UnspecifiedError;
		}
	}
	
	public static StatusCodes logInUser(String email, String password, HttpServletResponse response){ //TODO: Move to LogInHandler
		Connect stream = new Connect();
		Connection con = stream.getConnection();
		try{
			String query;
			int userID;
			try{
				query = "SELECT ID FROM datingsite.Users WHERE Email = ? AND Password = ?;";
				ResultSet rs = executeQueryWithParams(query, email, password);
				rs.next();
				if(rs.isAfterLast()) return null;
				userID = rs.getInt(1);
				System.out.println(userID);
				rs.close();
			}
			catch(Exception e){
				e.printStackTrace();
				return StatusCodes.PasswordIncorrect;
			}
			try{
				String sessionID = UUID.randomUUID().toString();
				query = "INSERT INTO datingsite.Sessions (UserID, SessionID) VALUES (?,?)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt(1, userID);
				pstmt.setString(2, sessionID);
				int count = pstmt.executeUpdate();
				System.out.println("ROWS AFFECTED:" + count);
				if(count == 0) throw new IllegalStateException();
				pstmt.close();
				
				response.addCookie(new Cookie("sessionID", sessionID));
				return StatusCodes.Success;
			}
			catch(Exception e){
				e.printStackTrace();
				return StatusCodes.SQLError;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return StatusCodes.UnspecifiedError;
		}
	}
	
	public static StatusCodes createAndAddNewUser(String email, String password, HttpServletResponse response){ //TODO: Move to RegistrationHandler
		try{
			String SQL = "INSERT INTO datingsite.Users (email, password) VALUES (?,?); SCOPE_IDENTITY();"; 
			ResultSet rs = executeQueryWithParams(SQL, email, password);
			rs.next();
			String userID = rs.getString(1);
			rs.close();
			
			try {
				SQL = "INSERT INTO datingsite.UserData (UserID) VALUES (?)";
				executeQueryWithParamsWithoutResults(SQL, userID);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				StatusCodes code = logInUser(email, password, response);
				if(code != StatusCodes.Success) throw new Error("Something is very wrong.");
			} catch(Exception e) {
				e.printStackTrace();
				throw new Error("Something is very, very wrong.");
			}
			return StatusCodes.Success;
		} catch(Exception e){
			e.printStackTrace();
			return StatusCodes.SQLError;
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
	
	public static List<User> getUsersForInfo(String firstName, String lastName, String gender, String Class){
		if(firstName.equals("")) firstName = "*";
		else{
			firstName = "%" + firstName + "%";
		}
		if(lastName.equals("")) lastName = "*";
		else{
			lastName = "%" + lastName + "%";
		}
		if(gender.equals("")) gender = "*";
		if(gender.equals("")) Class = "*";
		String query = "SELECT UserID FROM datingsite.UserData WHERE FirstName LIKE ? OR LastName LIKE ? OR Gender = ? OR Class = ?;";
		ArrayList<User> matches = new ArrayList<User>();
		try {
			ResultSet rs = executeQueryWithParams(query,firstName, lastName, gender,Class);
			System.out.println(rs);
			while(rs.next()){
				User match = new User(rs.getString("UserID"));
				matches.add(match);
			}
			System.out.println(matches.toString());
			return matches;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void setError(HttpServletRequest request, String error) {
		setError(request.getSession(false), error);
	}
	
	public static void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
	
	public static enum StatusCodes {
		Success,				//No error occurred.
		UnspecifiedError,		//Default in case an unknown error occurs.
		AccessDenied,			//A user tried to change a value that they shouldn't be able to.
		UserAlreadyExists,		//A user registration request was sent, but the user name was already taken.
		UsernameInvalid,		//A user registration request was sent, but the user name specified was invalid for some reason.
		PasswordInvalid,		//A user registration request was sent, but the password specified was invalid for some reason.
		PasswordsDidNotMatch,	//A user registration request was sent, but the when the password was retyped it was different from the previous one.
		NotFromRossevelt,		//A user registration request was sent, but they did not check the box that they were from roosevelt
		UserNotRecognized,		//A user log in request was sent, but the user name didn't exist in the database.
		PasswordIncorrect,		//A user log in request was sent, and the user name existed, but the password was incorrect.
		SessionInvalid,
		SQLError,				//An unspecified SQL error occurred.
		;
	}
	
	public static enum PersonalInfo {
		//TODO: PersonalInfoHandler?
		FirstName(true, "FirstName", "First Name", "text"),
		LastName(true, "LastName", "Last Name", "text"),
		Birthday(true, "Birthday", "Birthday", "date"), //TODO: make a special case 
		Class("Class", "Class"),
		Gender("Gender", "Gender"),
		;
		
		private final boolean required;
		private final String varName;
		private final String displayName;
		private final String HTMLInputType;

		private PersonalInfo(boolean req, String sql, String disp, String html) {
			required = req;
			varName = sql;
			displayName = disp;
			HTMLInputType = html;
		}

		private PersonalInfo(String sql, String disp) {
			required = true;
			varName = sql;
			displayName = disp;
			HTMLInputType = "ERROR";
		}
		
		public String getVarName() {
			return varName;
		}
		
		public boolean isRequired() {
			return required;
		}
		
		public String getInfoForUser(User user) {
			try {
				String query = String.format("SELECT %s FROM datingsite.UserData WHERE UserID = ?;", this.getVarName());
				ResultSet rs = executeQueryWithParams(query, user.userID);
				rs.next();
				return rs.getString(1);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public String getHTMLInputTag(User user) { //TODO: Create special cases for certain personal info types
			switch(this) {
				case Gender:
					return String.format("%s: <select name=\"%s\" required>\n<option value=0>Male</option>\n<option value=1>Female</option></select><br>", getDisplayName(), getVarName());
				case Class:
					return String.format("%s: <input type=\"number\" name=\"%s\" value=\"%d\" min=\"2014\" max=\"2019\" required><br>", getDisplayName(), getVarName(), Integer.parseInt(getInfoForUser(user)));
				default:
					return String.format("%s: <input type=\"%s\" name=\"%s\" value=\"%s\" %s><br>", getDisplayName(), HTMLInputType, getVarName(), getInfoForUser(user), (required ? "required" : ""));
			}
		}
		
		public String getBlankHTMLInputTag() { //TODO: Create special cases for certain personal info types
			switch(this) {
				case Gender:
					return String.format("%s: <select name=\"%s\" required>\n<option value=0>Male</option>\n<option value=1>Female</option></select><br>", getDisplayName(), getVarName());
				case Class:
					return String.format("%s: <input type=\"number\" name=\"%s\" min=\"2014\" max=\"2019\" required><br>", getDisplayName(), getVarName());
				default:
					return String.format("%s: <input type=\"%s\" name=\"%s\" %s><br>", getDisplayName(), HTMLInputType, getVarName(), (required ? "required" : ""));
			}
		}

		public String getDisplayName() {
			return displayName;
		}
	}
	
	public static class Message {
		private User sender;
		private User recipient;
		private String messageText;
		private String messageID;
		private boolean sent;
		
		public Message(User sender, User recipient, String messageText) {
			this.sender = sender;
			this.recipient = recipient;
			this.messageText = messageText;
			this.messageID = null;
			this.sent = false;
		}
		
		public Message(User sender, User recipient, String messageText, String messageID) {
			this.sender = sender;
			this.recipient = recipient;
			this.messageText = messageText;
			this.messageID = messageID;
			this.sent = true;
		}
		
		public boolean isSent() {
			return sent;
		}
		
		public void send() {
			String query = "INSERT INTO datingsite.Messages (SenderID, RecipientID, Text) VALUES (?, ?, ?); SELECT SCOPE_IDENTITY;";
			try {
				ResultSet rs = executeQueryWithParams(query, sender.getUserID(), recipient.getUserID(), messageText);
				rs.next();
				messageID = rs.getString(1);
				sent = true;
			} catch(SQLException e) {
				e.printStackTrace();
				//TODO: Error handling
			}
		}
		
		public User getSender() {
			return sender;
		}
		
		public User getRecipient() {
			return recipient;
		}
		
		public String getText() {
			return messageText;
		}
	}

	/*
	0 not compatible
	50 average
	100 super compatible
	*/

	public static class User {
		Map<PersonalInfo, String> info = null;
		private String userID;
		
		public User(String ID) {
			ResultSet rs;
			try {
				rs = executeQueryWithParams("SELECT COUNT(*) FROM datingsite.Users WHERE ID = ?;", ID);
				rs.next();
				int count = rs.getInt(1);
				rs.close();
				if(count <= 0) throw new IllegalArgumentException(ID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IllegalArgumentException(ID);
			}
			userID = ID;
			info = getAllUserInfo();
		}
		
		public String getUserID() {
			return userID;
		}
		
		public List<Message> getRecievedMessages() {
			List<Message> messages = new ArrayList<Message>();
			try {
				String query = "SELECT * FROM datingsite.Messages WHERE RecipientID = ?;";
				ResultSet rs = executeQueryWithParams(query, userID);
				String text, messageID;
				User sender;
				while(rs.next()) {
					messageID = rs.getString("MessageID");
					sender = new User(rs.getString("SenderID"));
					text = rs.getString("Text");
					messages.add(0, new Message(sender, this, text, messageID));
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return messages;
		}
		
		public List<Message> getSentMessages() {
			List<Message> messages = new ArrayList<Message>();
			try {
				String query = "SELECT * FROM datingsite.Messages WHERE SenderID = ?;";
				ResultSet rs = executeQueryWithParams(query, userID);
				String text, messageID;
				User recipient;
				while(rs.next()) {
					messageID = rs.getString("MessageID");
					recipient = new User(rs.getString("RecipientID"));
					text = rs.getString("Text");
					messages.add(0, new Message(this, recipient, text, messageID));
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return messages;
		}
		
		public StatusCodes updatePersonalInfo(Map<PersonalInfo, String> infoMap) {
			System.out.println("map equals" +infoMap.toString());
			String queryTemplate = "UPDATE datingsite.UserData SET %s = ? WHERE UserID = ?;";
			String query;
			try {
				for(PersonalInfo pi : infoMap.keySet()) {
					query = String.format(queryTemplate, pi.getVarName());
					Global.executeQueryWithParamsWithoutResults(query, infoMap.get(pi), userID);
				}
				this.info = getAllUserInfo();
				return StatusCodes.Success;
			} catch(Exception e) {
				e.printStackTrace();
				return StatusCodes.SQLError;
			}
		}
		
		public Map<PersonalInfo, String> getAllUserInfo() {
			if(info != null) {
				return info;
			} else {
				try {
					String query = "SELECT COUNT(*) FROM datingsite.UserData WHERE UserID = ?";
					ResultSet rs = executeQueryWithParams(query, userID);
					rs.next();
					int count = rs.getInt(1);
					if(count < 1) {
						query = "INSERT INTO datingsite.UserData (UserID) VALUES (?)";
						executeQueryWithParamsWithoutResults(query, userID);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				try {
					String query = "SELECT * FROM datingsite.UserData WHERE UserID = ?;";
					ResultSet rs = executeQueryWithParams(query, userID);
					rs.next();
					Map<PersonalInfo, String> info = new HashMap<PersonalInfo, String>();
					for(PersonalInfo pi : PersonalInfo.values()) {
						String columnName = pi.getVarName();
						String data = rs.getString(columnName);
						if(pi == PersonalInfo.Gender) data = (data.equals("1") ? "Female" : "Male");
						info.put(pi, data);
					}
					return info;
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
	}
}
