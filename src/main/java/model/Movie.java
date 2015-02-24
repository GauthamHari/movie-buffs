package model;

public class Movie {

	private int id;
	private String title;
	private String duration;
	private String language;
	private String country;
	
	public int getId() { 
		return id;	
	}
	public void setId(int id) {	
		this.id = id; 
	}
	public String getTitle() { 
		return title; 
	}
	public void setTitle(String title) { 
		this.title = title; 
	}
	public String getDuration() {
		return duration; 
	}
	public void setDuration(String duration) { 
		this.duration = duration; 
	}
	public String getLanguage() { 
		return language; 
	}
	public void setLanguage(String language) { 
		this.language = language; 
	}
	public String getCountry() { 
		return country;	
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
