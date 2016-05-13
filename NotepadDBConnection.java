package notepadConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class NotepadDBConnection {

	private final String host="localhost:3306";
	private final String root="root";
	private final String password="Root1";
	private final String nameDb="notepadusers";
	private String url;
	
	protected Properties properties=new Properties();
	protected Connection mysqlConnection;
	
	public Connection getMySqlConnection(){
		return mysqlConnection;
	}
	
	public NotepadDBConnection(){

	}
	
	public void initializeProperties(){
		
		url="jdbc:mysql://"+host+"/"+nameDb;
		
		properties.setProperty("user", root);
		properties.setProperty("password", password);
		properties.setProperty("characterEncoding", "UTF-8");
		properties.setProperty("useUnicode", "true");		
	}
	
	public Connection establishDBConnection() {
		if (mysqlConnection==null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				mysqlConnection=DriverManager.getConnection(url, properties);
				//		mysqlConnect=DriverManager.getConnection(url, root, password);
			} catch (SQLException e) {
				System.out.println("SQL exception - establishDBConnection");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFound exception - establishDBConnection");
				e.printStackTrace();
			}
			System.out.println("Connection to DataBase \""+nameDb+"\" has been successfully established." );
			System.out.println("+----------------------------------------------------------------------+");
		}
		return mysqlConnection;
	}	
	
	public void closeDBConnection(){
		try {
			mysqlConnection.close();
			System.out.println("+---------------------------------------------------------+");
			System.out.println("DataBase \""+nameDb+"\" has been successfully disconnected." );		
		} catch (SQLException e) {
			System.out.println("SQL exception - closeDBConnection");
			e.printStackTrace();
		}
	}
	
}
