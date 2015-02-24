package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Movie;
import connectionprovider.ConnectionProvider;

public class GetMovieCommand {
	public Movie execute(int id) {
		Movie m = new Movie();
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Movies WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setTitle(rs.getString("title"));
				m.setDuration(rs.getString("duration"));
				m.setLanguage(rs.getString("language"));
				m.setYear(rs.getString("year"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	public Movie execute1(String title) {
		Movie m = new Movie();
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Movies WHERE title = ?");
			stmt.setString(1, title);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setTitle(rs.getString("title"));
				m.setDuration(rs.getString("duration"));
				m.setLanguage(rs.getString("language"));
				m.setYear(rs.getString("year"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
}
