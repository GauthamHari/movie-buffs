package services;

import java.util.HashMap;
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
import model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	public Response register(@QueryParam("username") String username, @QueryParam("password") String password, 
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname, 
			@QueryParam("gender") String gender) {
		DB db = new DB();
		try {
			if(db.addUser(username, password, firstname, lastname, gender))
				return Response.status(201).build();
			else
				return Response.status(500).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		DB db = new DB();
		try {
			if(db.login(username, password))
				return Response.status(200).build();
			else
				return Response.status(500).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/getrequesttoken")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequestToken(@Context HttpServletResponse response, @Context HttpServletRequest request) {
		String requestToken = null;
		DB db = new DB();
		try {
			requestToken = authentication.getAuthorisationToken().getRequestToken();
			db.saveTmdbRequestToken(User.getUsername(), requestToken);
			response.sendRedirect("https://www.themoviedb.org/authenticate/" + requestToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/getaccesstoken")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessToken(@Context HttpServletResponse response, @Context HttpServletRequest request) {
		DB db = new DB();
		try {
			response.sendRedirect("http://api.themoviedb.org/3/authentication/session/new?api_key=" 
				+ apikey + "&request_token=" + db.getTmdbRequestToken(User.getUsername()));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/saveaccesstoken")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveAccessToken(@QueryParam("sessionid") String sessionid) {
		DB db = new DB();
		try {
			db.saveTmdbAccessToken(sessionid);
			User.setTmdbsessionid(sessionid);
			return Response.status(201).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public Response logout() {
		try {
			User.setUsername(null);
			User.setPassword(null);
			User.setFirstname(null);
			User.setLastname(null);
			User.setGender(null);
			User.setTmdbsessionid(null);
			User.setTmdbrequesttoken(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	@DELETE
	@Path("/deregister")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deregister() {
		DB db = new DB();
		try {
			db.removeUser();
			return Response.status(200).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}
}
