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
	
	
}
