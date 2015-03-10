package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import util.Constants;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.tools.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.people.*;
import commands.DB;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/movies")
public class MovieService {
	private static String apikey = "";
	
	TmdbGenre genre = new TmdbApi(apikey).getGenre();
	TmdbMovies movies = new TmdbApi(apikey).getMovies();
	
	ObjectMapper mapper = new ObjectMapper();

	// Browse all genres
	@GET
	@Path("/getgenrelist")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response browseGenres(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<Genre> glist = new ArrayList<Genre>();
		glist.addAll(genre.getGenreList("en"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, glist);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Browse all movies in a particular genre
	@GET
	@Path("/getgenremovies/{genreid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response browseMoviesInGenre(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("genreid") int genreid) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mg = genre.getGenreMovies(genreid, "en", 1, true);
		mdb.addAll(mg.getResults());	
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Search a movie by id 
	@GET
	@Path("/getmovie/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieById(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		MovieDb m = movies.getMovie(movieid, "en");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, m);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get alternative titles for a movie 
	@GET
	@Path("/getmoviealternativetitles/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieAlternativeTitles(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<AlternativeTitle> tlist = new ArrayList<AlternativeTitle>();
		tlist.addAll(movies.getAlternativeTitles(movieid, "us"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, tlist);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get credits for a movie 
	@GET
	@Path("/getmoviecredits/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieCredits(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		Credits credit = movies.getCredits(movieid);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, credit);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get Images for a movie 
	@GET
	@Path("/getmovieimages/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieImages(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		MovieImages image = movies.getImages(movieid, "en");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, image);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}	
	
	// Get Keywords for a movie 
	@GET
	@Path("/getmoviekeywords/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieKeywords(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<Keyword> klist = new ArrayList<Keyword>();
		klist.addAll(movies.getKeywords(movieid));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, klist);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get Release Info for a movie 
	@GET
	@Path("/getmoviereleaseinfo/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieReleaseInfo(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<ReleaseInfo> rinfo = new ArrayList<ReleaseInfo>();
		rinfo.addAll(movies.getReleaseInfo(movieid, "en"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, rinfo);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get Videos for a movie 
	@GET
	@Path("/getmovievideos/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieVideos(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<Video> vlist = new ArrayList<Video>();
		vlist.addAll(movies.getVideos(movieid, "en"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, vlist);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get Translations for a movie 
	@GET
	@Path("/getmovietranslations/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieTranslations(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
	
		ArrayList<Translation> tlist = new ArrayList<Translation>();
		tlist.addAll(movies.getTranslations(movieid));
			
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, tlist);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get a list of similar movies for a movie
	@GET
	@Path("/getmoviessimilar/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSimilarMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getSimilarMovies(movieid, "en", 1);
		mdb.addAll(mrp.getResults());	
			
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get a list of upcoming movies
	@GET
	@Path("/getmoviesupcoming")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUpcomingMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getUpcoming("en", 1);
		mdb.addAll(mrp.getResults());	
				
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get a list of now-playing movies
	@GET
	@Path("/getmoviesnowplaying")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getNowPlayingMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getNowPlayingMovies("en", 1);
		mdb.addAll(mrp.getResults());	
				
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get a list of popular movies
	@GET
	@Path("/getmoviespopular")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPopularMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getPopularMovieList("en", 1);
		mdb.addAll(mrp.getResults());	
					
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	// Get a list of top-rated movies
	@GET
	@Path("/getmoviestoprated")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTopRatedMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
			
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getTopRatedMovies("en", 1);
		mdb.addAll(mrp.getResults());	
						
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mdb);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
	
	public static void main(String[] args) {
	
	}
}