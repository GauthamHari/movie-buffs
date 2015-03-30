package model;

public class User {
	private String username;
	private static String sessionid;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static String getSessionid() {
		return sessionid;
	}

	public static void setSessionid(String sid) {
		sessionid = sid;
	}
}
