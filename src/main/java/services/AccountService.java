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
import info.movito.themoviedbapi.TmdbAccount.MovieListResultsPage;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;

@Path("/account")
public class AccountService {
	private static String apikey = "";
	TmdbAccount account = new TmdbApi(apikey).getAccount();
	SessionToken st = new SessionToken("");
	AccountID aid = new AccountID(6218283); // My account ID
	ObjectMapper mapper = new ObjectMapper();
	
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
	public Response getFavoriteMovies(@QueryParam("offset") int offset,
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
	public Response getWatchListMovies(@QueryParam("offset") int offset,
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
}
