import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.Server;

public class data {
	
	static Connection connection;
	Server hsqlServer;
	
		public data(String DBName, String directory)
		{
			
			hsqlServer = new Server();
			hsqlServer.setDatabaseName(0, DBName);
			hsqlServer.setDatabasePath(0, "file:" + directory + "/" + DBName);
		}
		public static void start(){
	        //start database class
	    }
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
		 public void doStuff(String statement) throws SQLException {
			 
				 connection.prepareStatement(""+statement).execute();
			 
		 }
		 public ResultSet fetch(String statement) throws SQLException {
			 ResultSet rs = null;
			 
			 rs = connection.prepareStatement(""+statement).executeQuery();
			 
			 return rs;
		 }
		 public void stop() {
			 if (hsqlServer != null)
					hsqlServer.stop();
		 }
		 public void disconnect() throws SQLException {
				if (connection != null)
					connection.close();
		 }
	}