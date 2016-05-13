package notepadMySqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlQueries {
	private Connection mySqlConnection; 
	
	public MySqlQueries(Connection mySqlConnection){
		this.mySqlConnection=mySqlConnection;			
	}
	
	public void withoutResultQuery(String query){
		Statement stmt;
		try {
			stmt = mySqlConnection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("SQL exception - withResultQuery");
			e.printStackTrace();
		}

	}
	
	public ResultSet withResultQuery(String query){
		ResultSet result=null;
		Statement stmt;
		try {
			stmt = mySqlConnection.createStatement();
			result=stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL exception - ResultSet withResultQuery");
			e.printStackTrace();
		}
		return result;
	}
	
	public int getId_author(String loginFromUserBox){
		int id_author=0;
		String queryId_author="SELECT id_user FROM users WHERE login='"+loginFromUserBox+"'";
		ResultSet fromUsersTable=withResultQuery(queryId_author);
		try {
			if(fromUsersTable.next()){
				id_author=Integer.parseInt(fromUsersTable.getString("id_user"));
			} else {
				System.out.println("ResultSet is empty! <getId_author>");
			}
		} catch (SQLException e) {
			System.out.println("SQL exception - getId_author");
			e.printStackTrace();
		}
		return id_author;
	}

	public int getId_theme(String themeName){
		int id_theme=0;
		String queryId_theme="SELECT id_theme FROM themes WHERE themename='"+themeName+"'";
		ResultSet fromThemesTable=withResultQuery(queryId_theme);
		try {
			if(fromThemesTable.next()){
			id_theme=Integer.parseInt(fromThemesTable.getString("id_theme"));
			} else {
				System.out.println("ResultSet is empty! <getId_theme>");
			}
		} catch (SQLException e) {
			System.out.println("SQL exception - getId_theme");
			e.printStackTrace();
		}
		return id_theme;
	}
	
	public void initialFillingOfThemesTableInDB(){
		/* it`s never used in Java code. it was moved to Mysql  server for direct execution.*/

		String addThemeToDB = "INSERT INTO themes (themename) VALUE ('IT sphere')";
		withoutResultQuery(addThemeToDB);
		addThemeToDB = "INSERT INTO themes (themename) VALUE ('Java programming')";
		withoutResultQuery(addThemeToDB);
		addThemeToDB = "INSERT INTO themes (themename) VALUE ('General info')";
		withoutResultQuery(addThemeToDB);
		addThemeToDB = "INSERT INTO themes (themename) VALUE ('English')";
		withoutResultQuery(addThemeToDB);
	}
	
	public void initialCreationOfTables(){
	/* it`s never used in Java code. it was moved to Mysql  server for direct execution.*/
	/* Tables:   */
	/* Table 1 - users (id, login, name, pass) */
	/* Table 2 - themes (id, themename) */
	/* Table 3 - notes (id, userid, themeid, notename, notecreatedate, notechangedate, notetext) */
		
		String tablesAllDelete="DROP TABLE users, themes, notes";
		String tableUsers="CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
				" login VARCHAR(20), name VARCHAR(50), pass VARCHAR(50))";
		String tableThemes="CREATE TABLE themes(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
				" themename VARCHAR(50))";
		String tableNotes="CREATE TABLE notes(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
				" userid INT, themeid INT, notename VARCHAR(250), notecreatedate DATETIME,"+
				" notechangedate DATETIME, notetext TEXT)";
		withoutResultQuery(tablesAllDelete);
		withoutResultQuery(tableUsers);
		withoutResultQuery(tableThemes);
		withoutResultQuery(tableNotes);
		
	}
	
	
}
