package com.achsanit.oncinema.data.source.remote.services

import com.achsanit.oncinema.data.source.remote.model.GetDetailMovieResponse
import com.achsanit.oncinema.data.source.remote.model.GetMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    //get now playing
    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @QueryMap query: HashMap<String,String>
    ): Response<GetMovieResponse>

    //get popular movie
    @GET("3/movie/popular")
    suspend fun getPopularMovie(
        @QueryMap query: HashMap<String,String>
    ): Response<GetMovieResponse>

    //get top rated
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovie(
        @QueryMap query: HashMap<String,String>
    ): Response<GetMovieResponse>

    //get detail movie
    @GET("3/movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @QueryMap query: HashMap<String,String>
    ): Response<GetDetailMovieResponse>
}