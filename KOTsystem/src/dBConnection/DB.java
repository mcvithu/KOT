package dBConnection;
import java.sql.*;


public class DB {

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					return conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/" + "kotsystem" + "?useSSL=true", "root", "");
				}
		catch (Exception e ) {
			e.printStackTrace();
		}
		return conn;
	
	}

	}
