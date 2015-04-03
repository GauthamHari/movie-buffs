package services;

import java.util.HashMap;
import util.Constants;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.core.SessionToken;

@Path("/list")
public class ListService {
	private static String apikey = "e688f51c2289b388729acfe277687a99";	
	SessionToken st = new SessionToken(User.getTmdbsessionid()); 
	TmdbLists list = new TmdbApi(apikey).getLists();
	ObjectMapper mapper = new ObjectMapper();
	 
	//-----------------------------------------------------------------------------------------------------------------
	// LIST SERVICES (5)
	// 1) This method lets users create a new list.
	@Path("/create")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createList(@QueryParam("offset") int offset, @QueryParam("count") int count, 
			@QueryParam("name") String name, @QueryParam("description") String description) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list.createList(st, name, description));
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
	
	//-----------------------------------------------------------------------------------------------------------------
	// 2) This method lets users delete an existing list. 
	@Path("/delete")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteList(@QueryParam("offset") int offset, @QueryParam("count") int count, 
		@QueryParam("listid") String listid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list.deleteMovieList(st, listid));
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
	
	//-----------------------------------------------------------------------------------------------------------------
	// 3) This method lets users add new movies to a list that they created. 
	@Path("/add")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addMovie(@QueryParam("offset") int offset, @QueryParam("count") int count, 
		@QueryParam("listid") String listid, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list.addMovieToList(st, listid, movieid));
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
	
	//-----------------------------------------------------------------------------------------------------------------
	// 4) This method lets users delete movies from a list that they created. 
	@Path("/remove")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response removeMovie(@QueryParam("offset") int offset, @QueryParam("count") int count, 
		@QueryParam("listid") String listid, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list.removeMovieFromList(st, listid, movieid));
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
	
	//-----------------------------------------------------------------------------------------------------------------
	// 5) Check to see if a movie ID is already added to a list.
	@Path("/status")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response checkMovieStatus(@QueryParam("offset") int offset, @QueryParam("count") int count, 
		@QueryParam("listid") String listid, @QueryParam("movieid") int movieid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list.isMovieOnList(listid, movieid));
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
