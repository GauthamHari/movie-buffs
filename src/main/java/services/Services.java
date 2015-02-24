package services;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Movie;
import commands.CreateMovieCommand;
import commands.GetMovieCommand;
import commands.ListMoviesCommand;
import commands.UpdateMovieCommand;
import commands.DeleteMovieCommand;
import util.Constants;

@Path("movie")
public class Services {
	ObjectMapper mapper = new ObjectMapper();

	// Browse all movies
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response browseMovies(@QueryParam("offset") int offset,
			@QueryParam("count") int count) {
		ListMoviesCommand command = new ListMoviesCommand();
		ArrayList<Movie> list = command.execute();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list);
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
	
	// Add a movie
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createMovies(String payload) {
		CreateMovieCommand create = new CreateMovieCommand();
		Movie m = null;
		String i = "";
		try {
			m = mapper.readValue(payload, Movie.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(400).entity("could not read string").build();
		}
		try {
			i = create.execute(m);
		} catch (Exception e) {
			e.printStackTrace();
			Response.status(500).build();
		}
		return Response.status(200).entity(i).build();
	}
	
	// Update a movie
	@POST
	@Path("update/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateMovies(String payload, @PathParam("id") int id) {
		UpdateMovieCommand update = new UpdateMovieCommand();
		Movie m = null;
		try {
			m = mapper.readValue(payload, Movie.class);
			m.setId(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(400).entity("could not read string").build();
		}
		try {
			update.execute(m);
		} catch (Exception e) {
			e.printStackTrace();
			Response.status(500).build();
		}
		return Response.status(200).build();
	}
	
	// Delete a movie
	@DELETE
	@Path("delete/{id}")
	public Response deleteMovie(@PathParam("id") int id) {
		DeleteMovieCommand delete = new DeleteMovieCommand();
		try {
			delete.execute(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("").build();
	}
	
	// Search movies
	// get movie by Id
	@GET
	@Path("getid/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieByID(@PathParam("id") int id) {
		GetMovieCommand command = new GetMovieCommand();
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(command.execute(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
		
	// get song by title
	@GET
	@Path("gettitle/{title}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieByTitle(@PathParam("title") String title) {
		GetMovieCommand command = new GetMovieCommand();
		String movieString = null;
		try {
			movieString = mapper.writeValueAsString(command.execute1(title));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(movieString).build();
	}
}