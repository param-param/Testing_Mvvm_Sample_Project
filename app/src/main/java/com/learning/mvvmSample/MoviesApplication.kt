package com.learning.mvvmSample

import android.app.Application
import com.learning.mvvmSample.xyzFeatureScreens.api.MoviesService
import com.learning.mvvmSample.xyzFeatureScreens.api.RetrofitHelper
import com.learning.mvvmSample.xyzFeatureScreens.repository.MoviesRepository


class MoviesApplication : Application() {

    lateinit var moviesRepository: MoviesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val moviesService = RetrofitHelper.getInstance().create(MoviesService::class.java)
        moviesRepository = MoviesRepository(moviesService)
    }
}