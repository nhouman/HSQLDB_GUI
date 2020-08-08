import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;


import org.hsqldb.Server;

public class Main{
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		String directory = GUI.directory;
		Scanner reader = new Scanner(System.in);
		String databaseName = GUI.databaseName;
		Server hsqlServer = null;
		Connection connection;
		data db = new data(databaseName, directory);
		db.start();
		db.connect(directory, databaseName);
		System.out.println("Please create a table");
		String statement = reader.nextLine();
		db.doStuff(statement);
		System.out.println("Please enter an SQL fetch statement");
		statement = reader.nextLine();
		ResultSet rs = db.fetch(statement);
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		for (int i = 1; i <= numberOfColumns; i++) {
			if (i > 1)
				System.out.print(",\t\t");
			String columnName = rsmd.getColumnName(i);
			System.out.print(columnName);
		}
		System.out.println("");
		while (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) { 
				if (i > 1)
					System.out.print(",\t\t");
				String columnValue = rs.getString(i);
				System.out.print(columnValue);
			}
			System.out.println("");
		}
		db.disconnect();
		db.stop();
	}

}



