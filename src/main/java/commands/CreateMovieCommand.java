package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Movie;
import connectionprovider.ConnectionProvider;

public class CreateMovieCommand {
	
	public String execute(Movie m) {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO MOVIES(title, duration, language, country) VALUES(?, ?, ?, ?) Returning id");
			stmt.setString(1, m.getTitle());
			stmt.setString(2, m.getDuration());
			stmt.setString(3, m.getLanguage());
			stmt.setString(4, m.getCountry());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getString("id");
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}
}