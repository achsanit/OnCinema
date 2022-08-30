package com.achsanit.oncinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.oncinema.BuildConfig
import com.achsanit.oncinema.data.repository.MainRepository
import com.achsanit.oncinema.data.source.remote.model.Genre
import com.achsanit.oncinema.data.source.remote.model.GetDetailMovieResponse
import com.achsanit.oncinema.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private var _detailMv = MutableLiveData<Resource<GetDetailMovieResponse>>()
    val detailMv: LiveData<Resource<GetDetailMovieResponse>> get() = _detailMv

    private var _listGenre = MutableLiveData<Resource<List<Genre>>>()
    val listGenre: LiveData<Resource<List<Genre>>> get() = _listGenre

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _detailMv.postValue(Resource.loading(null))
            _listGenre.postValue(Resource.loading(null))
            try {
                val query = HashMap<String,String>()
                query["api_key"] = BuildConfig.API_KEY
                query["language"] = "en-Us"

                val response = mainRepository.getDetailMv(movieId,query)

                if (response.isSuccessful) {
                    _detailMv.postValue(Resource.success(response.body()))
                    _listGenre.postValue(Resource.success(response.body()?.genres))
                } else {
                    _detailMv.postValue(Resource.error("error to get detail movie",null))
                }
            } catch (e: Exception) {
                _detailMv.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

}