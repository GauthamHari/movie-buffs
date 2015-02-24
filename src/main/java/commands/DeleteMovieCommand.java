package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import connectionprovider.ConnectionProvider;

public class DeleteMovieCommand {
	public void execute(int id) {
		boolean result;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM MOVIES WHERE id=?");
			stmt.setInt(1, id);
			result = stmt.execute();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
