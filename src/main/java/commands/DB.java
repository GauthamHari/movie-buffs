package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import twitter4j.auth.AccessToken;
import connectionprovider.ConnectionProvider;

public class DB {
	
	public AccessToken getOAuthToken(String user, String application) {
		AccessToken accessToken = null;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
				.prepareStatement("SELECT oauth, secret FROM TOKENS WHERE username = ? AND application=?");
			stmt.setString(1, user);
			stmt.setString(2, application);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				accessToken = new AccessToken(rs.getString("oauth"), rs.getString("secret"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public void saveOAuthToken(String otoken, String user, String app, String secret) {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO tokens(oauth, username, application, secret) VALUES(?, ?, ?, ?)");
			stmt.setString(1, otoken);
			stmt.setString(2, user);
			stmt.setString(3, app);
			stmt.setString(4, secret);
			stmt.executeUpdate();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public ArrayList<String> getUsernames() { 
		ArrayList<String> usernames = new ArrayList<String>();
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT username FROM tokens");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				usernames.add(rs.getString("username"));
			} 
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernames; 
	}

	//-----------------------------------------------------------------------------------------------------------------
	public boolean addUser(String uname, String pwd, String fname, String lname, String gender) {
		boolean result = false;
		if(isUsernameAvailable(uname)) {
			try {
				Connection connection = ConnectionProvider.getConnection();
				PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO users(username, password, firstname, lastname, gender) VALUES(?, ?, ?, ?, ?)");
				stmt.setString(1, uname);
				stmt.setString(2, pwd);
				stmt.setString(3, fname);
				stmt.setString(4, lname);
				stmt.setString(5, gender);
				stmt.executeUpdate();
				result = true;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void removeUser() {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE username=?");
			stmt.setString(1, User.getUsername());
			stmt.execute();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public boolean login(String username, String password) {
		boolean result = false;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
				.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = true;
				User.setFirstname(rs.getString("firstname"));
				User.setLastname(rs.getString("lastname"));
				User.setUsername(rs.getString("username"));
				User.setPassword(rs.getString("password"));
				User.setGender(rs.getString("gender"));
				User.setTmdbsessionid(rs.getString("tmdbsessionid"));
				User.setTmdbrequesttoken(rs.getString("tmdbrequesttoken"));
				System.out.println("Login successful.");
			}
			else
				System.out.println("Login Failed. Incorrect username or password.");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public boolean isUsernameAvailable(String username) {
		boolean result = true;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) 
				result = false;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void saveTmdbRequestToken(String username, String tmdbrequesttoken) {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET tmdbrequesttoken=? WHERE username=?");
			stmt.setString(1, tmdbrequesttoken);
			stmt.setString(2, username);
			stmt.executeUpdate();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void saveTmdbAccessToken(String tmdbaccesstoken) {
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET tmdbsessionid=? WHERE username=?");
			stmt.setString(1, tmdbaccesstoken);
			stmt.setString(2, User.getUsername());
			stmt.executeUpdate();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public String getTmdbRequestToken(String username) {
		String result = null;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				result = rs.getString("tmdbrequesttoken");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}