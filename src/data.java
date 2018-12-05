import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.Server;

public class data {
	
	static Connection connection;
	Server hsqlServer;
		
	//Creates a database variable to store DB info.
		public data(String DBName, String directory)
		{
			
			hsqlServer = new Server();
			hsqlServer.setDatabaseName(0, DBName);
			hsqlServer.setDatabasePath(0, "file:" + directory + "/" + DBName);
		}
		//not sure why this is needed, but it seemed like it was
		public static void start(){
	        //start database class
	    }
		
		//connects to the Database
		 public static void connect(String directory, String databaseName){
		        //create the connection, something like this:
		        try {
		        	Class.forName("org.hsqldb.jdbcDriver");
					// Default user of the HSQLDB is 'sa' with an empty password
						connection = DriverManager.getConnection(
							"jdbc:hsqldb:file:" + directory + "/" + databaseName, "sa", "");
		            } catch (ClassNotFoundException e) {

		            } catch (SQLException e) {

		        }
		    }
		 
		 //doStuff is the general SQL commands that do not return a table (So basically everything besides
		 //SELECT)
		 public void doStuff(String statement) throws SQLException {
			 
				 connection.prepareStatement(""+statement).execute();
			 
		 }
		 //fetch is used for SQL select statements. 
		 public ResultSet fetch(String statement) throws SQLException {
			 ResultSet rs = null;
			 
			 rs = connection.prepareStatement(""+statement).executeQuery();
			 
			 return rs;
		 }
		 
		 //stops the SQL server
		 public void stop() {
			 if (hsqlServer != null)
					hsqlServer.stop();
		 }
		 //disconnects from the SQL server
		 public void disconnect() throws SQLException {
				if (connection != null)
					connection.close();
		 }
	}