package com.learning.mvvmSample.xyzFeatureScreens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.mvvmSample.xyzFeatureScreens.models.Movie
import com.learning.mvvmSample.xyzFeatureScreens.repository.MoviesRepository
import com.learning.mvvmSample.xyzFeatureScreens.repository.ResponseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<ResponseType<List<Movie>>>()

    val movies: LiveData<ResponseType<List<Movie>>>
        get() = _moviesLiveData


    fun getMovies(){
        viewModelScope.launch {
            val result = repository.getMovies()
            _moviesLiveData.postValue(result)
        }
    }

}