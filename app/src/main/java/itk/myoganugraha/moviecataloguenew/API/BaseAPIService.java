package itk.myoganugraha.moviecataloguenew.API;


import itk.myoganugraha.moviecataloguenew.Model.Movie;
import itk.myoganugraha.moviecataloguenew.Model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseAPIService {

    @GET("search/movie")
    Call<MovieResponse> searchMovie(
            @Query("query") String query
    );

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );



}

