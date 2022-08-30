package com.achsanit.oncinema.data.repository

import com.achsanit.oncinema.data.source.remote.model.GetDetailMovieResponse
import com.achsanit.oncinema.data.source.remote.model.GetMovieResponse
import com.achsanit.oncinema.data.source.remote.services.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getNowPlaying(query: HashMap<String,String>): Response<GetMovieResponse> {
        return apiHelper.getNowPlaying(query)
    }

    suspend fun getPopularMovie(query: HashMap<String, String>): Response<GetMovieResponse> {
        return apiHelper.getPopularMovie(query)
    }

    suspend fun getTopRatedMv(query: HashMap<String, String>): Response<GetMovieResponse> {
        return apiHelper.getTopRatedMv(query)
    }

    suspend fun getDetailMv(movieId: Int,query: HashMap<String, String>): Response<GetDetailMovieResponse> {
        return apiHelper.getDetailMovie(movieId,query)
    }
}