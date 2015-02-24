package connectionprovider;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProvider {
	public static Connection getConnection() throws URISyntaxException,
			SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String dbUrl = "jdbc:postgresql://"
				+ dbUri.getHost()
				+ ':'
				+ dbUri.getPort()
				+ dbUri.getPath()
				+ "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		System.out.println(dbUrl);
		return DriverManager.getConnection(dbUrl, username, password);
	}

	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM movies");
			while (rs.next()) {
				System.out.println("Id: " + rs.getString("id"));
				System.out.println("Title: " + rs.getString("title"));
				System.out.println("Language: " + rs.getString("language"));
				System.out.println("Country: " + rs.getString("country"));
				System.out.println("Running time: " + rs.getString("duration"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}