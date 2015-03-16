package services;

import java.util.HashMap;
import util.Constants;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.*;

@Path("/people")
public class PeopleService {
	private static String apikey = ""; // insert	
	TmdbPeople people = new TmdbApi(apikey).getPeople();
	ObjectMapper mapper = new ObjectMapper();
	
	//---------------------------------------------------------------------------------------------
	// PEOPLE SERVICES (4)
	// 1) Get the general person information for a specific id. 
	@GET
	@Path("/person")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonId(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("personid") int personid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, people.getPersonInfo(personid, "."));
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
	// 2) Get the credits for a specific person id. 
	@GET
	@Path("/credits")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonCredits(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("personid") int personid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, people.getPersonCredits(personid));
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
	// 3) Get the images for a specific person id. 
	@GET
	@Path("/images")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonImages(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @QueryParam("personid") int personid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, people.getPersonImages(personid));
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
	// 4) Get the list of popular people on The Movie Database. 
	@GET
	@Path("/popular")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonPopular(@QueryParam("offset") int offset, @QueryParam("count") int count) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, people.getPersonPopular(1));
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
