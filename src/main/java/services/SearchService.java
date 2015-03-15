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
import info.movito.themoviedbapi.TmdbPeople.PersonResultsPage;
import info.movito.themoviedbapi.TmdbSearch.KeywordResultsPage;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.people.*;

@Path("/search")
public class SearchService {
	private static String apikey = "e688f51c2289b388729acfe277687a99";
	ObjectMapper mapper = new ObjectMapper();
	TmdbSearch search = new TmdbApi(apikey).getSearch();
	
	//---------------------------------------------------------------------------------------------
	//SEARCH SERVICES (4)
	// 1) Search for movies by title
	@GET
	@Path("/movie/{title}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response SearchMovieByTitle(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("title") String title) {
					
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = search.searchMovie(title, null, "en", true, 1);
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
	// 2) Search for people by name
	@GET
	@Path("/person/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response SearchPeopleByName(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("name") String name) {
					
		ArrayList<Person> p = new ArrayList<Person>();
		PersonResultsPage prp = search.searchPerson(name, true, null);
		p.addAll(prp.getResults());	
							
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, p);
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
	// 3) Search for keywords by name
	@GET
	@Path("/keyword/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response SearchKeywordsByName(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("name") String name) {
					
		ArrayList<Keyword> k = new ArrayList<Keyword>();
		KeywordResultsPage krp = search.searchKeyword(name, 1);
		k.addAll(krp.getResults());	
						
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, k);
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
	// 4) Search for lists by name and description
	@GET
	@Path("/list/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response SearchListsByName(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("name") String name) {
					
		ArrayList<MovieList> mlist = new ArrayList<MovieList>();
		MovieListResultsPage mlrp = search.searchList(name, "en", 1);
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
}
