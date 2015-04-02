package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.DB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;

@Path("/user")
public class UserService {
	private static String apikey = "e688f51c2289b388729acfe277687a99";
	TmdbAuthentication authentication = new TmdbApi(apikey).getAuthentication();
	ObjectMapper mapper = new ObjectMapper();
	
	//-----------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("username") String username, @QueryParam("password") String password, 
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname, 
			@QueryParam("gender") String gender) {
		String result = null;
		DB db = new DB();
		try {
			result = db.addUser(username, password, firstname, lastname, gender);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("username") String username, @QueryParam("password") String password) {
		String result = null;
		DB db = new DB();
		try {
			result = db.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/getrequesttoken")
	@Produces(MediaType.APPLICATION_JSON)
	public void getRequestToken(@Context HttpServletResponse response, @Context HttpServletRequest request, 
			@QueryParam("username") String username) {
		String requestToken = null;
		DB db = new DB();
		try {
			requestToken = authentication.getAuthorisationToken().getRequestToken();
			db.saveTmdbRequestToken(username, requestToken);
			response.sendRedirect("https://www.themoviedb.org/authenticate/" + requestToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/getaccesstoken")
	@Produces(MediaType.APPLICATION_JSON)
	public void getAccessToken(@Context HttpServletResponse response, @Context HttpServletRequest request, 
			@QueryParam("username") String username) {
		DB db = new DB();
		try {
			response.sendRedirect("http://api.themoviedb.org/3/authentication/session/new?api_key=" 
				+ apikey + "&request_token=" + db.getTmdbRequestToken(username));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/saveaccesstoken")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveAccessToken(@QueryParam("username") String username, @QueryParam("sessionid") String sessionid) {
		DB db = new DB();
		try {
			db.saveTmdbAccessToken(username, sessionid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Your access token has been successfully saved in the database.";
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@DELETE
	@Path("/deregister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deregister(@QueryParam("username") String username) {
		DB db = new DB();
		try {
			db.removeUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("").build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		DB db = new DB();
		try {
			db.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("").build();
	}
}
