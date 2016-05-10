package ua.kiev.javalife;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notepadConnection.NotepadDBConnection;
import notepadMySqlQueries.MySqlQueries;

@WebServlet("/AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<String> userBox=new ArrayList<String>();
	
	private MySqlQueries mySqlQuery=null;

	private String realName="";
	private String login="";
	private String password="";

	public AuthorizationServlet() {	
		System.out.println("Constructor!");
		/* just once - prepare DBconnection for any query */
		NotepadDBConnection connect=new NotepadDBConnection("localhost:3306","root","Root1","notepadusers");
		connect.initializeProperties();
		connect.establishDBConnection();
		Connection mySqlConnectionForQuery = connect.getMySqlConnection();		
		mySqlQuery = new MySqlQueries(mySqlConnectionForQuery);

	}
	
	public static void setUserBox(String login, String realName){
		userBox.add(0, login);
		userBox.add(1, realName);		
	}
	
	public static ArrayList<String> getUserBox(){
		return userBox;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setUserBox("","");
		getServletContext().getRequestDispatcher("/authorization.html").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		realName = (String) request.getParameter("realname");
		login = (String) request.getParameter("login");
		password = (String) request.getParameter("password");
		
		
		System.out.println("+---------------------------+");
		System.out.println("Real Name: " + realName);
		System.out.println("Login: " + login);
		System.out.println("+---------------------------+");
		
		if (login == "") {
			System.out.println("Login is null. Please type login.");
			request.setAttribute("warningText", "Login is null. Please type login.");
			getServletContext().getRequestDispatcher("/warning.jsp").forward(request, response);
		} else {
			if (realName != "") {
				System.out.println("New user ... lets check it in DB by login...");
				ResultSet result = checkUserInDB("login", login);
				try {
					if (result.first()) {
						System.out.println("Such login already exists. Please type another login.");
						request.setAttribute("warningText", "Such login already exists. Please type another login.");
						getServletContext().getRequestDispatcher("/warning.jsp").forward(request, response);
					} else {
						System.out.println("Login is unique. Lets add new user to DB.");
						addNewUserToDB(realName,login,password);
						setUserBox(login,realName);
						request.setAttribute("sendRealNameToJspPage", realName);
						getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
					}
				} catch (SQLException e) {
					System.out.println("exception 1");
					e.printStackTrace();
				}
			} else {
				System.out.println("The user exists in the DB ... lets check it in DB by login...");
				ResultSet result = checkUserInDB("login", login);
				try {
					if (!result.first()) {
						System.out.println("Such login is missing in DB. Please type another login.");
						request.setAttribute("warningText", "Such login is missing in DB. Please type another login.");
						getServletContext().getRequestDispatcher("/warning.jsp").forward(request, response);
					} else {
						System.out.println("Login is found and unique. Lets check his password ...");
						if(result.getString("pass").equals(password)){
							System.out.println("Password is correct. Authentication is done.");
							realName=result.getString("name");
							setUserBox(login,realName);
							request.setAttribute("sendRealNameToJspPage", realName);
							getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
						} else{
							request.setAttribute("warningText", "The password is incorrect.");
							getServletContext().getRequestDispatcher("/warning.jsp").forward(request, response);							
						}

					}
				} catch (SQLException e) {
					System.out.println("exception 2");
					e.printStackTrace();
				}
			}
		}
	}
		
	protected ResultSet checkUserInDB(String column, String value){	
		
		/* ********************Query With Result***************** */
		String withResultQuery="SELECT * FROM users WHERE "+column+"='"+value+"'";
		ResultSet result = mySqlQuery.withResultQuery(withResultQuery);
		/* ****************************************************** */
		showResultSet(result);
		return result;
	}

	private void showResultSet(ResultSet result){
		try {
			while(result.next()){			
			String resultToString_login=result.getString("login");
			String resultToString_name=result.getString("name");			
			System.out.println("ID / Name / Login");
			System.out.println(result.getRow()+" / "+resultToString_login+" / "+resultToString_name);
			}
		} catch (SQLException e) {
			System.out.println("exception 3");
			e.printStackTrace();
		}

	}
	
	protected void addNewUserToDB(String newRealName, String newLogin, String newPassword){
		String query="";

		/* ********************Query Without Result************** */
			query="INSERT INTO users (login, name, pass) VALUE ('"+newLogin+"','"+newRealName+"','"+newPassword+"')";
			mySqlQuery.withoutResultQuery(query);
		/* ****************************************************** */		
	}

}
