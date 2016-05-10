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
			e.printStackTrace();
		}
		return result;
	}
	
	public void initialCreationOfTables(){
	/* it`s not used in Java code. it was moved to Mysql  server for direct execution.*/
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
