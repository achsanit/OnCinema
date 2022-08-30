package com.achsanit.oncinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.oncinema.BuildConfig
import com.achsanit.oncinema.data.repository.MainRepository
import com.achsanit.oncinema.data.source.remote.model.Result
import com.achsanit.oncinema.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _listNowPlaying = MutableLiveData<Resource<List<Result>>>()
    val listNowPlaying: LiveData<Resource<List<Result>>> get() = _listNowPlaying

    private val _listPopular = MutableLiveData<Resource<List<Result>>>()
    val listPopular: LiveData<Resource<List<Result>>> get() = _listPopular

    private val _listTopRated = MutableLiveData<Resource<List<Result>>>()
    val listTopRated: LiveData<Resource<List<Result>>> get() = _listTopRated

    init {
        getPopularMovie()
        getNowPlaying()
        getTopRated()
    }

    private fun getPopularMovie() {
        viewModelScope.launch {
            _listPopular.postValue(Resource.loading(null))
            try {
                val query = HashMap<String,String>()
                query["page"] = "1"
                query["language"] = "en-US"
                query["api_key"] = BuildConfig.API_KEY

                val response = mainRepository.getPopularMovie(query)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        _listPopular.postValue(Resource.success(it.results))
                    }
                } else {
                    _listPopular.postValue(Resource.error("error to get popular movie",null))
                }
            } catch (e:Exception) {
                _listPopular.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    private fun getNowPlaying() {
        viewModelScope.launch {
            _listNowPlaying.postValue(Resource.loading(null))
            try {
                val query = HashMap<String,String>()
                query["page"] = "1"
                query["language"] = "en-US"
                query["api_key"] = BuildConfig.API_KEY

                val response = mainRepository.getNowPlaying(query)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        _listNowPlaying.postValue(Resource.success(it.results))
                    }
                } else {
                    _listNowPlaying.postValue(Resource.error("error to get data from server",null))
                }
            } catch (e: Exception) {
                _listNowPlaying.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    private fun getTopRated() {
        viewModelScope.launch {
            _listTopRated.postValue(Resource.loading(null))
            try {
                val query = HashMap<String,String>()
                query["page"] = "1"
                query["language"] = "en-US"
                query["api_key"] = BuildConfig.API_KEY

                val response = mainRepository.getTopRatedMv(query)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        _listTopRated.postValue(Resource.success(it.results))
                    }
                } else {
                    _listTopRated.postValue(Resource.error("error to get top rated movie",null))
                }
            } catch (e: Exception) {
                _listTopRated.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

}