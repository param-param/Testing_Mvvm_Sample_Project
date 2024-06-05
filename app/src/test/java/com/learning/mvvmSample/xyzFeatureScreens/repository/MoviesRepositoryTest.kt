package com.learning.mvvmSample.xyzFeatureScreens.repository

import com.learning.mvvmSample.xyzFeatureScreens.api.MoviesService
import com.learning.mvvmSample.xyzFeatureScreens.models.Movie
import com.learning.mvvmSample.xyzFeatureScreens.models.MoviesResponse
import com.learning.mvvmSample.xyzFeatureScreens.utils.Constants
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


internal class MoviesRepositoryTest {

    @Mock
    lateinit var moviesService: MoviesService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetMovies_EmptyList() = runTest {
        Mockito.`when`(moviesService.getUpcomingMoviesList(Constants.API_KEY, 1)).thenReturn(
            Response.success(MoviesResponse(1, emptyList(), 1,0)))

        val moviesRepository = MoviesRepository(moviesService)
        val result = moviesRepository.getMovies()
        Assert.assertEquals(true, result is ResponseType.Success)
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetMovies_expectedMovieList() = runTest {
        val movieList = listOf<Movie>(
            Movie("", 1, "", "", "First"),
            Movie("", 2, "", "", "Second")
        )
        Mockito.`when`(moviesService.getUpcomingMoviesList(Constants.API_KEY, 1)).thenReturn(
            Response.success(MoviesResponse(1, movieList, 1,0)))

        val moviesRepository = MoviesRepository(moviesService)
        val result = moviesRepository.getMovies()
        Assert.assertEquals(true, result is ResponseType.Success)
        Assert.assertEquals(2, result.data!!.size)
        Assert.assertEquals("First", result.data!![0].title)
    }

    @Test
    fun testGetMovies_expectedError() = runTest {
        Mockito.`when`(moviesService.getUpcomingMoviesList(Constants.API_KEY, 1)).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val moviesRepository = MoviesRepository(moviesService)
        val result = moviesRepository.getMovies()
        Assert.assertEquals(true, result is ResponseType.Error)
        Assert.assertEquals("Something went wrong", result.error)
    }

}