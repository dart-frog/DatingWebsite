package datingSite;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datingSite.Global.User;

/**
 * Servlet implementation class SearchHandler
 */
@WebServlet("/SearchHandler")
public class SearchHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		User myself = new User(request.getParameter("myself"));
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String gender = request.getParameter("Gender");
		String Class = request.getParameter("Class");
		List<User> users = Global.getUsersForInfo(myself, firstName, lastName , gender , Class );
		response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
		StringBuilder sb = new StringBuilder();
		sb.append("<users>");
		if(users != null) {
			for(User user : users) {
				sb.append("<user>");
				sb.append("<id>" + user.getUserID() + "</id>");
				sb.append("<firstName>" + user.info.get(Global.PersonalInfo.FirstName) + "</firstName>");
				sb.append("<lastName>" + user.info.get(Global.PersonalInfo.LastName) + "</lastName>");
				sb.append("<class>" + user.info.get(Global.PersonalInfo.Class) + "</class>");
				sb.append("<gender>" + user.info.get(Global.PersonalInfo.Gender) + "</gender>");
				sb.append("</user>");
			}
		} else {
			sb.append("<user>");
			sb.append("<id>0</id>");
			sb.append("<firstName>Test</firstName>");
			sb.append("<lastName>User</lastName>");
			sb.append("<class>2012</class>");
			sb.append("<gender>Pants</gender>");
			sb.append("</user>");
			sb.append("<user>");
			sb.append("<id>0</id>");
			sb.append("<firstName>Another</firstName>");
			sb.append("<lastName>Test</lastName>");
			sb.append("<class>1945</class>");
			sb.append("<gender>Damp Moss</gender>");
			sb.append("</user>");
		}
		sb.append("</users>");
		System.out.println(sb.toString());
		out.write(sb.toString());
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
