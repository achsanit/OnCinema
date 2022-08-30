package com.achsanit.oncinema.data.source.remote.services

import com.achsanit.oncinema.data.source.remote.model.GetDetailMovieResponse
import com.achsanit.oncinema.data.source.remote.model.GetMovieResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovie(query: HashMap<String,String>): Response<GetMovieResponse> {
        return apiService.getPopularMovie(query)
    }

    suspend fun getNowPlaying(query: HashMap<String, String>): Response<GetMovieResponse> {
        return apiService.getNowPlaying(query)
    }

    suspend fun getTopRatedMv(query: HashMap<String, String>): Response<GetMovieResponse> {
        return apiService.getTopRatedMovie(query)
    }

    suspend fun getDetailMovie(movieId: Int,query: HashMap<String, String>): Response<GetDetailMovieResponse> {
        return apiService.getDetailMovie(movieId,query)
    }
}