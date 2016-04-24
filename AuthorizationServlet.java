package ua.kiev.javalife;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AuthorizationServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login= (String) request.getParameter("login");
		String pas= (String) request.getParameter("password");
		PrintWriter out = response.getWriter();
		
		System.out.println(login);
		System.out.println(pas);
	
		if(true){
		getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
		}
	
	}

}
