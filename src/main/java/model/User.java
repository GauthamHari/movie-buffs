package model;

public class User {
	private static String username;
	private static String password;
	private static String firstname;
	private static String lastname;
	private static String gender;
	private static String tmdbsessionid;
	private static String tmdbrequesttoken;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		User.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		User.password = password;
	}
	public static String getFirstname() {
		return firstname;
	}
	public static void setFirstname(String firstname) {
		User.firstname = firstname;
	}
	public static String getLastname() {
		return lastname;
	}
	public static void setLastname(String lastname) {
		User.lastname = lastname;
	}
	public static String getGender() {
		return gender;
	}
	public static void setGender(String gender) {
		User.gender = gender;
	}
	public static String getTmdbsessionid() {
		return tmdbsessionid;
	}
	public static void setTmdbsessionid(String tmdbsessionid) {
		User.tmdbsessionid = tmdbsessionid;
	}
	public static String getTmdbrequesttoken() {
		return tmdbrequesttoken;
	}
	public static void setTmdbrequesttoken(String tmdbrequesttoken) {
		User.tmdbrequesttoken = tmdbrequesttoken;
	}
}
