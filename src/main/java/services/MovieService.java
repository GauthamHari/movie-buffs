package services;

import java.util.ArrayList;
import java.util.HashMap;

import util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.TmdbAccount.MovieListResultsPage;
import info.movito.themoviedbapi.TmdbPeople.PersonResultsPage;
import info.movito.themoviedbapi.TmdbSearch.CollectionResultsPage;
import info.movito.themoviedbapi.TmdbSearch.KeywordResultsPage;
import info.movito.themoviedbapi.tools.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.people.*;

@Path("/movies")
public class MovieService {
	private static String apikey = "";
	
	TmdbGenre genre = new TmdbApi(apikey).getGenre();
	TmdbMovies movies = new TmdbApi(apikey).getMovies();
	TmdbSearch search = new TmdbApi(apikey).getSearch();
	TmdbLists list = new TmdbApi(apikey).getLists();
	TmdbAccount account = new TmdbApi(apikey).getAccount();
	SessionToken st = new SessionToken("");
	AccountID aid = new AccountID(6218283);
	
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
	
	//---------------------------------------------------------------------------------------------
	// MOVIE SERVICES (13)
	// 1) Get a movie by id 
	@GET
	@Path("/getmovie/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieById(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		MovieDb m = movies.getMovie(movieid, "en");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, m);
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
	
	// 2) Get alternative titles for a movie 
	@GET
	@Path("/getmoviealternativetitles/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieAlternativeTitles(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<AlternativeTitle> tlist = new ArrayList<AlternativeTitle>();
		tlist.addAll(movies.getAlternativeTitles(movieid, "us"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, tlist);
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
	
	// 3) Get credits for a movie 
	@GET
	@Path("/getmoviecredits/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieCredits(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		Credits credit = movies.getCredits(movieid);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, credit);
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
	
	// 4) Get Images for a movie 
	@GET
	@Path("/getmovieimages/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieImages(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		MovieImages image = movies.getImages(movieid, "en");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		hm.put(Constants.Pagination.DATA, image);
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
	
	// 5) Get Keywords for a movie 
	@GET
	@Path("/getmoviekeywords/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieKeywords(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<Keyword> klist = new ArrayList<Keyword>();
		klist.addAll(movies.getKeywords(movieid));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, klist);
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
	
	// 6) Get Release Info for a movie 
	@GET
	@Path("/getmoviereleaseinfo/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieReleaseInfo(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<ReleaseInfo> rinfo = new ArrayList<ReleaseInfo>();
		rinfo.addAll(movies.getReleaseInfo(movieid, "en"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, rinfo);
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
	
	// 7) Get Videos for a movie 
	@GET
	@Path("/getmovievideos/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieVideos(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<Video> vlist = new ArrayList<Video>();
		vlist.addAll(movies.getVideos(movieid, "en"));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, vlist);
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
	
	// 8) Get Translations for a movie 
	@GET
	@Path("/getmovietranslations/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMovieTranslations(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
	
		ArrayList<Translation> tlist = new ArrayList<Translation>();
		tlist.addAll(movies.getTranslations(movieid));
			
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, tlist);
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
	
	// 9) Get a list of similar movies for a movie
	@GET
	@Path("/getmoviessimilar/{movieid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSimilarMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count, @PathParam("movieid") int movieid) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getSimilarMovies(movieid, "en", 1);
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
	
	// 10) Get a list of upcoming movies
	@GET
	@Path("/getmoviesupcoming")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUpcomingMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getUpcoming("en", 1);
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
	
	// 11) Get a list of now-playing movies
	@GET
	@Path("/getmoviesnowplaying")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getNowPlayingMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getNowPlayingMovies("en", 1);
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
	
	// 12) Get a list of popular movies
	@GET
	@Path("/getmoviespopular")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPopularMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
		
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getPopularMovieList("en", 1);
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
	
	// 13) Get a list of top-rated movies
	@GET
	@Path("/getmoviestoprated")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTopRatedMovies(@QueryParam("offset") int offset,
		@QueryParam("count") int count) {
			
		ArrayList<MovieDb> mdb = new ArrayList<MovieDb>();
		MovieResultsPage mrp = movies.getTopRatedMovies("en", 1);
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
	//SEARCH SERVICES (4)
	// 1) Search for movies by title
	@GET
	@Path("/search/movie/{title}")
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
	
	// 2) Search for people by name
	@GET
	@Path("/search/person/{name}")
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
	
	// 3) Search for keywords by name
	@GET
	@Path("/search/keyword/{name}")
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
	
	// 4) Search for lists by name and description
	@GET
	@Path("/search/list/{name}")
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
	
	//---------------------------------------------------------------------------------------------
	// ACCOUNT SERVICES (5)
	// 1) Get the basic information for an account.
	@GET
	@Path("/account")
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
	
	// 2) Get the lists that you have created and marked as a favorite
	@GET
	@Path("/account/lists")
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
	
	// 3) Get the list of rated movies (and associated rating) for an account
	@GET
	@Path("/account/rated/movies")
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
	
	// 4) Get the list of favorite movies for an account
	@GET
	@Path("/account/favorite/movies")
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
	
	// 5) Get the list of movies on an accounts watchlist
	@GET
	@Path("/account/watchlist/movies")
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