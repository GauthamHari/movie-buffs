package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.DB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.model.config.TokenAuthorisation;
import info.movito.themoviedbapi.model.config.TokenSession;

@Path("/tmdb")
public class TestService {
	private static String apikey = "e688f51c2289b388729acfe277687a99"; // insert
	TmdbAuthentication authentication = new TmdbApi(apikey).getAuthentication();
	ObjectMapper mapper = new ObjectMapper();
	
	//---------------------------------------------------------------------------------------------
	@GET
	@Path("/request")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRequestToken(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @QueryParam("user") String user) {
		String requestToken = null;
		try {
			requestToken = authentication.getAuthorisationToken().getRequestToken();
			request.getSession().setAttribute("requestToken", requestToken);
			request.getSession().setAttribute("username", user);
			response.sendRedirect("https://www.themoviedb.org/authenticate/" + requestToken);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return requestToken;
	}
	
	//---------------------------------------------------------------------------------------------
	@GET
	@Path("/generate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAccessToken(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @QueryParam("requestToken") String requestToken) {
		try {
			response.sendRedirect("http://api.themoviedb.org/3/authentication/session/new?api_key=" 
				+ apikey + "&request_token=" + requestToken);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//---------------------------------------------------------------------------------------------
	@GET
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	public String success(@QueryParam("user") String user, @QueryParam("secret") String secret) {
		try {
			DB db = new DB();
			db.saveSessionId(user, "Movie-buffs", secret);
		} catch (Exception e) {
			System.out.println("Could not store access token to DB");
		}
		return "Session ID has been successfully saved to the database.";
	}
}
