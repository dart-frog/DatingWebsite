package datingSite;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datingSite.Global.PersonalInfo;
import datingSite.Global.User;

/**
 * Servlet implementation class PersonalInfoHandler
 */
@WebServlet("/PersonalInfoHandler")
public class PersonalInfoHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalInfoHandler() {
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
		User user = Global.getUserFromRequest(request);
		Map<PersonalInfo, String> infoMap = user.getAllUserInfo();
		for(PersonalInfo pi : PersonalInfo.values()) {
			infoMap.put(pi, request.getParameter(pi.getVarName()));
		}
		Global.StatusCodes code = user.updatePersonalInfo(infoMap);
		switch(code) {
			case UnspecifiedError:
				Global.setError(request, "An error occurred.");
				return;
			case SQLError:
				Global.setError(request, "A SQL error occurred.");
			case Success:
			default:
		}
	}

}
