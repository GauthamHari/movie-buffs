package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Movie;
import connectionprovider.ConnectionProvider;

public class ListMoviesCommand {
	public ArrayList<Movie> execute() {
		ArrayList<Movie> ret = new ArrayList<Movie>();
		try {
			Connection connection = ConnectionProvider.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Movies");
			while (rs.next()) {
				Movie m = new Movie();
				m.setId(rs.getInt("id"));
				m.setTitle(rs.getString("title"));
				m.setDuration(rs.getString("duration"));
				m.setLanguage(rs.getString("language"));
				m.setCountry(rs.getString("country"));
				ret.add(m);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}