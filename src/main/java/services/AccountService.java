package services;

import java.util.ArrayList;
import java.util.HashMap;
import util.Constants;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.TmdbAccount.MovieListResultsPage;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;

@Path("/account")
public class AccountService {
	private static String apikey = ""; 						// insert
	TmdbAccount account = new TmdbApi(apikey).getAccount();
	SessionToken st = new SessionToken(""); 				// insert
	AccountID aid = new AccountID(1); 						// insert
	ObjectMapper mapper = new ObjectMapper();
	info.movito.themoviedbapi.TmdbAccount.MediaType m = info.movito.themoviedbapi.TmdbAccount.MediaType.valueOf("MOVIE");
	
	//---------------------------------------------------------------------------------------------
	// ACCOUNT SERVICES (5)
	// 1) Get the basic information for an account.
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountInfo(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.getAccount(st));
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
	// 2) Get the lists that you have created and marked as a favorite
	@GET
	@Path("/lists")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountLists(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieList> mlist = new ArrayList<MovieList>();
		MovieListResultsPage mlrp = account.getLists(st, aid, "en", 1);
		mlist.addAll(mlrp.getResults());
			
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, mlist);
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
	// 3) Get the list of rated movies (and associated rating) for an account
	@GET
	@Path("/rated/movies")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountRatedMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		MovieResultsPage mrp = account.getRatedMovies(st, aid, 1);
		hm.put(Constants.Pagination.DATA, mrp);
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
	// 4) Get the list of favorite movies for an account
	@GET
	@Path("/favorite/movies")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountFavoriteMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		MovieResultsPage mrp = account.getFavoriteMovies(st, aid);
		hm.put(Constants.Pagination.DATA, mrp);
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
	// 5) Get the list of movies on an accounts watchlist
	@GET
	@Path("/watchlist/movies")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountWatchListMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		MovieResultsPage mrp = account.getWatchListMovies(st, aid, 1);
		hm.put(Constants.Pagination.DATA, mrp);
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
	// 6) This method lets users rate a movie. 
	@POST
	@Path("/rate/movie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response postMovieRating(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("movieid") int movieid, 
		@QueryParam("rating") int rating) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.postMovieRating(st, movieid, rating));
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
	// 7) Add a movie to an account's favorites list
	@POST
	@Path("/add/favorite/movie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addFavoriteMovie(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.addFavorite(st, aid, movieid, m));
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
	// 8) Remove a movie from an account's favorites list
	@POST
	@Path("/remove/favorite/movie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeFavoriteMovie(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.removeFavorite(st, aid, movieid, m));
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
	// 9) Add a movie to an account's watch list
	@POST
	@Path("/add/watchlist/movie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addWatchlistMovie(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.addToWatchList(st, aid, movieid, m));
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
	// 10) Remove a movie from an account's watch list
	@POST
	@Path("/remove/watchlist/movie")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeWatchlistMovie(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, account.removeFromWatchList(st, aid, movieid, m));
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
}
