package datingSite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import datingSite.Global.StatusCodes;

/**
 * Servlet implementation class RegistrationHandler
 */
@WebServlet("/RegistrationHandler")
public class RegistrationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null, redirect = "";
		StatusCodes code = StatusCodes.UnspecifiedError;
		String email = request.getParameter("email"),
				password = Global.hash(request.getParameter("password")),
				repassword = Global.hash(request.getParameter("password"));
		if (!password.equals(repassword)){
			code = StatusCodes.PasswordsDidNotMatch;
		}
		else if (!(request.getParameter("studentstatus").equals("on"))){
			code = StatusCodes.UnspecifiedError; //TODO
		}
		else{
			code = Global.createAndAddNewUser(email, password, response);	
		}
		
		switch(code) {
			case Success:
				redirect = "ProfilePage.jsp";
				break;
			case UserAlreadyExists:
				error = String.format("The email %s is already registered.", email);
				redirect = "Register.jsp";
				break;
			case PasswordInvalid:
				error = "The given password was invalid.";
				redirect = "Register.jsp";
				break;
			case PasswordsDidNotMatch:
				error = "The given passwords did not match.";
				redirect = "Register.jsp";
			default:
				error = "An unknown error occurred.";
				redirect = "Home.jsp";
				break;
		}
		
		Global.setError(request, error);
		response.sendRedirect(redirect);
		
	}
}
