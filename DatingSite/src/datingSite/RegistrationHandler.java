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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null, redirect = "";
		if (! (request.getParameter("password").equals(request.getParameter("repassword")))){ 
			error = "Your passwords dont match";
			redirect = "Home.jsp";
		}
		else if (!(request.getParameter("studentstatus").equals("on"))){
			error = "You must be from Roosevelt to make an account";
			redirect= "home.jsp";
		}
		else{
			String email = request.getParameter("email");
			String password = Global.hash(request.getParameter("password"));
			StatusCodes code = Global.createAndAddNewUser(email, password);
			
			switch(code) {
				case Success:
					Global.logInUser(email, password, response);
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
				default:
					error = "An unknown error occurred.";
					redirect = "Home.jsp";
					break;
				}
				Global.setError(request, error);
				response.sendRedirect(redirect);
		}
	}
}
