package com.learning.mvvmSample.xyzFeatureScreens.repository

import com.learning.mvvmSample.xyzFeatureScreens.api.MoviesService
import com.learning.mvvmSample.xyzFeatureScreens.models.Movie
import com.learning.mvvmSample.xyzFeatureScreens.utils.Constants


class MoviesRepository(private val moviesService: MoviesService) {

    suspend fun getMovies() :ResponseType<List<Movie>>{
        val response = moviesService.getUpcomingMoviesList(Constants.API_KEY, 1)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                ResponseType.Success(responseBody.results)
            } else {
                ResponseType.Error("Something went wrong")
            }
        } else {
            ResponseType.Error("Something went wrong")
        }
    }

}







