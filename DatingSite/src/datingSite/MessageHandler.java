package datingSite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datingSite.Global.Message;
import datingSite.Global.User;

/**
 * Servlet implementation class MessageHandler
 */
@WebServlet("/MessageHandler")
public class MessageHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String recipientID = request.getParameter("recipient");
		response.sendRedirect("Users.jsp?user=" + recipientID);
		try {
			String text = request.getParameter("message");
			User recipient = new User(request.getParameter("recipient"));
			User sender = new User (request.getParameter("sender"));
			Message m = new Message(sender, recipient, text);
			m.send();
			Global.setError(request, "Message sent!");
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			Global.setError(request, "User not found.");
		} catch(Exception e) {
			e.printStackTrace();
			Global.setError(request, "An unknown error occurred.");
		}
	}

}
