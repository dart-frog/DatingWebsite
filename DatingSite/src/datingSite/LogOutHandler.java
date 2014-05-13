package datingSite;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datingSite.Global.StatusCodes;

/**
 * Servlet implementation class LogOutHandler
 */
@WebServlet("/LogOutHandler")
public class LogOutHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogOutHandler() {
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
		code = Global.logOutUser(request);
		switch(code) {
			case Success:
				redirect = "Home.jsp";
				break;
			case SQLError:
				error = "A database side error occured";
				redirect = "Home.jsp";
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
