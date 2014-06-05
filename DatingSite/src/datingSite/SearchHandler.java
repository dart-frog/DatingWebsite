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
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String gender = request.getParameter("Gender");
		String Class = request.getParameter("Class");
		List<User> users = Global.getUsersForInfo(request.getParameter("ME"),firstName, lastName , gender , Class );
		response.setContentType("text/xml");
		StringBuilder sb = new StringBuilder();
		sb.append("<users>");
		if(users != null) {
			for(User user : users) {
				sb.append("<user>\n");
				sb.append("<id>" + user.getUserID() + "</id>\n");
				sb.append("<firstName>" + user.info.get(Global.PersonalInfo.FirstName) + "</firstName>\n");
				sb.append("<lastName>" + user.info.get(Global.PersonalInfo.LastName) + "</lastName>\n");
				sb.append("<class>" + user.info.get(Global.PersonalInfo.Class) + "</class>\n");
				sb.append("<gender>" + user.info.get(Global.PersonalInfo.Gender) + "</gender>\n");
				sb.append("<user>\n");
			}
		} else {
			sb.append("<user>\n");
			sb.append("<id>0</id>\n");
			sb.append("<firstName>Test</firstName>\n");
			sb.append("<lastName>User</lastName>\n");
			sb.append("<class>2012</class>\n");
			sb.append("<gender>Wolf</gender>\n");
			sb.append("<user>\n");
		}
		sb.append("<users>\n");
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
