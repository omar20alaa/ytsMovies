package com.yts_movies.ytsmovies.data.network.webservice;


import com.yts_movies.ytsmovies.data.network.Urls;
import com.yts_movies.ytsmovies.data.network.model.BaseMovie;
import com.yts_movies.ytsmovies.data.network.model.MovieDetail;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

@SuppressWarnings("SameParameterValue")
public interface BaseApi {

    @GET(Urls.ENDPOINTS.MOVIES_LIST) io.reactivex.Observable<BaseMovie> getMoviesList(@Query("page") int pageNum);

    @GET(Urls.ENDPOINTS.MOVIES_DETAILS)
    io.reactivex.Observable<MovieDetail> getMovieDetails(@Query("movie_id") long movieId,
                                                         @Query("with_images") boolean withImg,
                                                         @Query("with_cast") boolean withCast);

    @GET
    io.reactivex.Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

}
