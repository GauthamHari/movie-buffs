package services;

import java.util.ArrayList;
import java.util.HashMap;

import util.Constants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.keywords.Keyword;

@Path("/movies")
public class MovieService {
	private static String apikey = "e688f51c2289b388729acfe277687a99"; // insert	
	TmdbMovies movies = new TmdbApi(apikey).getMovies();
	ObjectMapper mapper = new ObjectMapper();

	//---------------------------------------------------------------------------------------------
	// MOVIE SERVICES (13)
	// 1) Get a movie by id 
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
	
	//---------------------------------------------------------------------------------------------
	// 2) Get alternative titles for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 3) Get credits for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 4) Get Images for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 5) Get Keywords for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 6) Get Release Info for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 7) Get Videos for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 8) Get Translations for a movie 
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
	
	//---------------------------------------------------------------------------------------------
	// 9) Get a list of similar movies for a movie
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
	
	//---------------------------------------------------------------------------------------------
	// 10) Get a list of upcoming movies
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
	
	//---------------------------------------------------------------------------------------------
	// 11) Get a list of now-playing movies
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
	
	//---------------------------------------------------------------------------------------------
	// 12) Get a list of popular movies
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
	
	//---------------------------------------------------------------------------------------------
	// 13) Get a list of top-rated movies
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
	
	//---------------------------------------------------------------------------------------------
	// 14) Get a list of forthcoming movies
	@GET
	@Path("/getmoviesforthcoming")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getForthcomingMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		int numberOfItems = 0;
		StringBuilder sb = new StringBuilder("UPCOMING MOVIES AND THEIR RELEASE DATES (YYYY-MM-DD): ");
		sb.append(System.lineSeparator());
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getUpcoming("en", 1);
		mdb.addAll(mrp.getResults());
		
		for(MovieDb item: mdb) {
			sb.append(System.lineSeparator());
			sb.append((++numberOfItems) + ") " + item.getOriginalTitle() + " - " + item.getReleaseDate());
		}
		return Response.status(200).entity(sb.toString()).build();
	}
}