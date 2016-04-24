package ua.kiev.javalife;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ThemeServlet")
public class ThemeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> themeValueToThemeString= new HashMap<String, String>();
	private static String path, viewOrAdd="";
 
	public ThemeServlet() {
        super();
		fillOutHashMapWithThemes();
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

		PrintWriter out=response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		if(request.getParameter("AddButton")!=null){
			addNoteAndShowOnPage(request, out);
		}
		if(request.getParameter("ViewButton")!=null){
			viewNoteAndShowOnPage(request, out);
		}
	}
	

	
	public void addNoteAndShowOnPage(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		
		Date date=new Date();				
		String selectedTheme=request.getParameter("themes");		
				
		String text="Date: " + date + "\n";
		text=text + request.getParameter("textArea");
		text=text + "\n\n";
		
	/* step 1 */
		try {
			String readFile=getDeSerializing(selectedTheme);
			setSerializing(selectedTheme,readFile+text);
			text=getDeSerializing(selectedTheme);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}		

	/* step 2 */
		createHtmlPage(out, viewOrAdd="Add", selectedTheme, text);	
		
	}
	
	public void viewNoteAndShowOnPage(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
		String selectedTheme=request.getParameter("themes");
		String text="";
		/* step 1 */
		try {
			String readFile=getDeSerializing(selectedTheme);
			text=getDeSerializing(selectedTheme);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}		

	/* step 2 */
		createHtmlPage(out, viewOrAdd="View", selectedTheme, text);	
		
	}
	
	public static void setSerializing(String selectedTheme, String receivedText) throws IOException
	{	String sourceFile=selectedTheme+".txt";
		File file1=new File(sourceFile);
		if(!file1.exists()) file1.createNewFile();

			FileOutputStream fos = new FileOutputStream(sourceFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(receivedText);
					oos.flush();
				oos.close();
			fos.close();
	}

	public static String getDeSerializing(String selectedTheme) throws IOException, ClassNotFoundException
	{	String sourceFile=selectedTheme+".txt";
		String textFromFile="";
		File file1=new File(sourceFile);
		if(!file1.exists()) System.out.println(" the source file does not exists. Please check the destination."); 
		else {
		  FileInputStream fis = new FileInputStream(sourceFile);
		  	ObjectInputStream ois = new ObjectInputStream(fis);
		  		textFromFile=(String) ois.readObject();
		  	ois.close();
		  fis.close();
		}
		  return textFromFile;
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

			String selectedThemeAsReadableString = themeValueToThemeString.get(selectedTheme);

			out.println("<h3>Selected theme: "+selectedThemeAsReadableString +"</h3>");
		
			if(viewOrAdd=="Add") {
				out.println("<h4 style=\"color:green;\"> The new Note has been successfully added.</h4>");
			}		
			out.println("</div>");
			out.println("<div align= center class=\"brd\" style=\"color:#0000F0\">");
			out.println("<textarea rows=\"20\" cols=\"80\">");
			out.println(receivedText);
			out.println("</textarea></div>");
			out.println("<div align= center class=\"brd\" style=\"color:#0000FF\">");
			out.println("<form action=\"main.jsp\">");
			out.println("<input type=\"submit\" value=\"Press to step back\"> ");
			out.println("</form></div>");		
			out.println("</body></html>");
			out.close();
	}
		
	



}
