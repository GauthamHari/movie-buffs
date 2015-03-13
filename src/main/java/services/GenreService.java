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

@Path("/genre")
public class GenreService {
	private static String apikey = "";
	TmdbGenre genre = new TmdbApi(apikey).getGenre();
	ObjectMapper mapper = new ObjectMapper();
	
	//---------------------------------------------------------------------------------------------
	// GENRE SERVICES (2)
	// 1) Browse all genres
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
		
	//---------------------------------------------------------------------------------------------
	// 2) Browse all movies in a particular genre
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
}
