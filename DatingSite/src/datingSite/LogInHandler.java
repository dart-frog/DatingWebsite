package datingSite;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datingSite.Global.StatusCodes;

/**
 * Servlet implementation class LogInHandler
 */
@WebServlet("/LogInHandler")
public class LogInHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInHandler() {
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
		String error = null, redirect = "";
		StatusCodes code = StatusCodes.UnspecifiedError;
		String email = request.getParameter("email");
		System.out.print("made it");
		String password = Global.hash(request.getParameter("password"));
		try {
			code = Global.logInUser(email, password, response);
			switch(code) {
			case Success:
				redirect = "ProfilePage.jsp";
				break;
			case PasswordIncorrect:
				error = "Your username or password was incorrect";
				redirect = "Home.jsp";
				break;
			case SQLError:
				error = "We had a database error creating your session";
				redirect = "Home.jsp";
				break;
			default:
				error = "An unknown error occurred.";
				redirect = "Home.jsp";
				break;
		}	
		Global.setError(request, error);
		response.sendRedirect(redirect);
		} catch(Exception e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("error", "An error occurred.");
			response.sendRedirect("Home.jsp");
		}
	}

}
