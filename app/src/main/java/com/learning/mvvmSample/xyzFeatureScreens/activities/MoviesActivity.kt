package com.learning.mvvmSample.xyzFeatureScreens.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.learning.mvvmSample.MoviesApplication
import com.learning.mvvmSample.R
import com.learning.mvvmSample.databinding.ActivityMoviesBinding
import com.learning.mvvmSample.xyzFeatureScreens.adapter.MoviesAdapter
import com.learning.mvvmSample.xyzFeatureScreens.models.Movie
import com.learning.mvvmSample.xyzFeatureScreens.repository.ResponseType
import com.learning.mvvmSample.xyzFeatureScreens.viewmodels.MoviesViewModel
import com.learning.mvvmSample.xyzFeatureScreens.viewmodels.MoviesViewModelFactory

import java.util.ArrayList

class MoviesActivity : AppCompatActivity() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ActivityMoviesBinding
    private var itemsArrayList: List<Movie> = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies)

        initViews()

    }

    private fun initViews() {
        initRecycleViews()

        val repository = (application as MoviesApplication).moviesRepository
        moviesViewModel = ViewModelProvider(this, MoviesViewModelFactory(repository))
            .get(MoviesViewModel::class.java)

        binding.progressBar.visibility = View.VISIBLE

        moviesViewModel.getMovies()

        moviesViewModel.movies.observe(this, Observer {
            when (it) {
                is ResponseType.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ResponseType.Success -> {
                    binding.progressBar.visibility = View.GONE

                    it.data?.let {
                        adapter.setMovieList(it)
                    }

                }

                is ResponseType.Error -> {
                    binding.progressBar.visibility = View.GONE
                    it.error?.let {
                        Log.e("databaseerror", "-->" + it)
                        Toast.makeText(this@MoviesActivity, "Error -> " + it, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        })

    }

    private fun initRecycleViews() {
        adapter = MoviesAdapter(this, itemsArrayList)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.setAdapter(adapter)
    }


}