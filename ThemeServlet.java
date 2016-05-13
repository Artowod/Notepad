package ua.kiev.javalife;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notepadConnection.NotepadDBConnection;
import notepadMySqlQueries.MySqlQueries;

@WebServlet("/ThemeServlet")
public class ThemeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> themeValueToThemeString= new HashMap<String, String>();
	private MySqlQueries mySqlQuery=null;
	private Integer id_author=0;
	private Integer id_topic=0;
	private String noteName="";
	private String selectedThemeName="";
	private String loginFromUserBox="";
	private String realNameFromUserBox="";
	private String noteCreateDate;
	private String noteChangeDate;
	private String fullNoteWithDate="";
	private String noteContent="";
	
 
	public ThemeServlet() {
       // super();
		fillOutHashMapWithThemes();
		/* just once - prepare DBconnection for any query */
		NotepadDBConnection connect=new NotepadDBConnection();
		connect.initializeProperties();
		connect.establishDBConnection();
		Connection mySqlConnectionForQuery = connect.getMySqlConnection();		
		mySqlQuery = new MySqlQueries(mySqlConnectionForQuery);


    }
	
	public void fillOutHashMapWithThemes(){
		themeValueToThemeString.put("itSphere", "IT sphere");
		themeValueToThemeString.put("javaProgramming", "Java programming");
		themeValueToThemeString.put("general", "General info");
		themeValueToThemeString.put("english", "English");

	}	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		loginFromUserBox = AuthorizationServlet.getUserBox().get(0);
		realNameFromUserBox = AuthorizationServlet.getUserBox().get(1);
		selectedThemeName = themeValueToThemeString.get(request.getParameter("themes"));
		noteName = request.getParameter("noteName");
		id_author = mySqlQuery.getId_author(loginFromUserBox);
		id_topic = mySqlQuery.getId_theme(selectedThemeName);
		
		/* Convert Date to mySql datetime view shown as string */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		noteCreateDate=dateFormat.format(new Date());
		
		noteChangeDate = noteCreateDate;		
		noteContent = request.getParameter("textArea");
//		fullNoteWithDate = noteCreateDate + "Name: " + noteName + "\n" + request.getParameter("textArea") + "\n\n";	
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		if (request.getParameter("AddButton") != null) {
			addNoteAndShowOnPage(request, out);
		}
		if (request.getParameter("ViewButton") != null) {
			viewNoteAndShowOnPage(request, out);
		}
	}
	
	
	public void addNoteAndShowOnPage(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		
		/* step 1 - add the current note to MySql DB */
		String queryAddNote="INSERT INTO notes ("
				+"id_author, id_topic, notename, notecreatedate, notechangedate, notetext) "
				+"VALUE ('"
				+id_author+"','"
				+id_topic+"','"
				+noteName+"','"
				+noteCreateDate+"','"
				+noteChangeDate+"','"
				+noteContent+"')";
		// System.out.println("addNoteAndShowOnPage - queryAddNote: " + queryAddNote);
		mySqlQuery.withoutResultQuery(queryAddNote);

		/* step 2 - show this note on screen */
		fullNoteWithDate=noteCreateDate + "\n" + "Name: " + noteName + "\n" + request.getParameter("textArea");
		createHtmlPage(out, "Add", selectedThemeName, fullNoteWithDate);			
	}
	
	public void viewNoteAndShowOnPage(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {

		/* step 1 - get all notes of a selected theme for the selected user from MySql DB*/
		String queryViewNote="SELECT notename, notecreatedate, notetext FROM notes WHERE id_author='"+id_author+"' AND id_topic='"+id_topic+"'";		
		ResultSet allNotesOfSelectedThemeForThisUser=mySqlQuery.withResultQuery(queryViewNote);
		
		String fromDbNoteName="";
		String fromDbnoteCreateDate="";
		String fromDbNoteText="";
		String showTextOnScreen="";
		try {
			if(allNotesOfSelectedThemeForThisUser.next()){
				fromDbnoteCreateDate=allNotesOfSelectedThemeForThisUser.getString("notecreatedate");
				fromDbNoteName=allNotesOfSelectedThemeForThisUser.getString("notename");
				fromDbNoteText=allNotesOfSelectedThemeForThisUser.getString("notetext");
				showTextOnScreen=showTextOnScreen + fromDbnoteCreateDate + "\n" + fromDbNoteName+"\n" + fromDbNoteText+"\n";				
			} 
		} catch (SQLException e) {
			System.out.println("SQL exception - viewNoteAndShowOnPage");
			e.printStackTrace(); 
		}
		
		/* step 2 - show all notes of a selected theme on screen */
		createHtmlPage(out, "View", selectedThemeName, showTextOnScreen);	
		
	}
	
	
	public void createHtmlPage(PrintWriter out, String viewOrAdd, String selectedTheme, String receivedText){
						
			out.println("<!DOCTYPE html><html>");		
			out.println("<style>");
			out.println(".brd {");
			out.println("border: 4px double black;");
			out.println("background: #fc3;");
			out.println("width: 800px;");
			out.println("padding: 5px;");
			out.println("}");
			out.println("</style>");		
			out.println("<body>");
			out.println("<center>");			
			out.println("<div align= center class=\"brd\" style=\"color:#0000FF\">");
			out.println("<h3>Selected theme: "+selectedTheme +"</h3>");		
			if(viewOrAdd=="Add") {
				out.println("<h4 style=\"color:green;\"> The new Note has been successfully added.</h4>");
			}		
			out.println("</div>");
			out.println("<div align= center class=\"brd\" style=\"color:#0000F0\">");
			out.println("<textarea rows=\"20\" cols=\"80\">");
			out.println(receivedText);
			out.println("</textarea></div>");
			out.println("</body></html>");
			out.close();
	}
		
	



}
