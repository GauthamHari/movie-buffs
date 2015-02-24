package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Movie;
import connectionprovider.ConnectionProvider;

public class UpdateMovieCommand {
	public String execute(Movie m) {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE MOVIES SET title=?, duration=?, language=?, year=? WHERE id=?");
			stmt.setString(1, m.getTitle());
			stmt.setString(2, m.getDuration());
			stmt.setString(3, m.getLanguage());
			stmt.setString(4, m.getYear());
			stmt.setInt(5, m.getId());
			stmt.executeUpdate();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}
}
