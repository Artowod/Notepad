package notepadConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class NotepadDBConnection {
	private String host;
	private String root;
	private String password;
	private String nameDb;
	private String url;
	
	protected Properties properties=new Properties();
	protected Connection mysqlConnection;
	
	public Connection getMySqlConnection(){
		return mysqlConnection;
	}
	
	public NotepadDBConnection(String host, String root, String password, String nameDb){
		this.host=host;
		this.root=root; //user
		this.password=password;
		this.nameDb=nameDb;
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
				System.out.println("exception 4");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("exception 5");
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
			System.out.println("exception 6");
			e.printStackTrace();
		}
	}
	
}
