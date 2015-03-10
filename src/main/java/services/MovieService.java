package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.tools.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.people.*;
import commands.DB;

import java.util.ArrayList;

@Path("/movies")
public class MovieService {
	private static String apikey = "";
	
	public static void main(String[] args) {
		TmdbMovies movies = new TmdbApi(apikey).getMovies();
		TmdbGenre genre = new TmdbApi(apikey).getGenre();
	
		//-----------------------------------------------------------------------------------------
		//MOVIES
		System.out.println("\n1. getMovie = " + movies.getMovie(157336, "en"));
	
		System.out.println("\n2. Result of getAlternativeTitles = ");
		ArrayList<AlternativeTitle> tlist = new ArrayList<AlternativeTitle>();
		tlist.addAll(movies.getAlternativeTitles(11282, "us"));
		for ( AlternativeTitle item : tlist) {
			System.out.println(item.getTitle());
		}
	
		System.out.println("\n3. Result of getCredits:");
		Credits credit = movies.getCredits(11282);
		System.out.println("CAST");
		for(PersonCast cast : credit.getCast()) {
			System.out.println(cast.getName());
		}
	
		System.out.println("\nCREW");
		for(PersonCrew crew : credit.getCrew()) {
			System.out.println(crew.getName());
		}
	
		System.out.println("\n4. getImages = ");
		MovieImages i = movies.getImages(157336, "en");
		for (Artwork art : i.getPosters()) {
			System.out.println(art.getFilePath() + " - " + art.getArtworkType() + " - " + art.getLanguage());
		}
	
		System.out.println("\n5. getKeywords = ");
		for(Keyword key : movies.getKeywords(157336)) {
			System.out.println(key.getName());
		}
	
		System.out.println("\n6. getReleaseInfo = ");
		for(ReleaseInfo info : movies.getReleaseInfo(157336, "en")) {
			System.out.println(info.getCountry() + " " + info.getReleaseDate() + " " + info.getCertification());
		}
	
		System.out.println("\n7. getVideos = ");
		for(Video v : movies.getVideos(157336, "en")) {
			System.out.println(v.getType() + " - " + v.getName() + " - " + v.getSize() + " - " + v.getSite());
		}
	
		System.out.println("\n8. getTranslations = ");
		for(Translation t : movies.getTranslations(157336)) {
			System.out.println(t.getEnglishName());
		}
	
		System.out.println("\n9. getSimilarMovies = ");
		MovieResultsPage ms = movies.getSimilarMovies(157336, "en", 1);
		System.out.println("Total Results = " + ms.getTotalResults() + "\nTotal Pages = " + ms.getTotalPages());
		for(MovieDb m : ms.getResults()) {
			System.out.println(m.getTitle());	
		}
	
		System.out.println("\n10. getUpcoming = ");
		MovieResultsPage mu = movies.getUpcoming("en", 1);
		System.out.println("Total Results = " + mu.getTotalResults() + "\nTotal Pages = " + mu.getTotalPages());
		for(MovieDb m : mu.getResults()) {
			System.out.println(m.getTitle());	
		}
	
		System.out.println("\n11. getNowPlayingMovies = ");
		MovieResultsPage mnp = movies.getNowPlayingMovies("en", 1);
		System.out.println("Total Results = " + mnp.getTotalResults() + "\nTotal Pages = " + mnp.getTotalPages());
		for(MovieDb m : mnp.getResults()) {
			System.out.println(m.getTitle());	
		}
	
		System.out.println("\n12. getPopularMoviesList = ");
		MovieResultsPage mp = movies.getPopularMovieList("en", 1);
		System.out.println("Total Results = " + mp.getTotalResults() + "\nTotal Pages = " + mp.getTotalPages());
		for(MovieDb m : mp.getResults()) {
			System.out.println(m.getTitle());	
		}
	
		System.out.println("\n13. getTopRatedMovies = ");
		MovieResultsPage mt = movies.getTopRatedMovies("en", 1);
		System.out.println("Total Results = " + mt.getTotalResults() + "\nTotal Pages = " + mt.getTotalPages());
		for(MovieDb m : mt.getResults()) {
			System.out.println(m.getTitle());	
		}
		
		//-----------------------------------------------------------------------------------------
		//GENRES
		System.out.println("\n1. getGenreList = ");
		for(Genre g : genre.getGenreList("en")) {
			System.out.println(g.getName() + " " + g.getId());
		}
		
		System.out.println("\n2. getGenreMovies");
		MovieResultsPage mg = genre.getGenreMovies(27, "en", 1, true);
		System.out.println("Total Results = " + mg.getTotalResults() + "\nTotal Pages = " + mg.getTotalPages());
		for(MovieDb m : mg.getResults()) {
			System.out.println(m.getTitle());	
		}	
		
		//-----------------------------------------------------------------------------------------
		
	}
}