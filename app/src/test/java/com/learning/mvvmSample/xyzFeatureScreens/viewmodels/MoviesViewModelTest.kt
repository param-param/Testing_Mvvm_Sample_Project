package com.learning.mvvmSample.xyzFeatureScreens.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learning.mvvmSample.xyzFeatureScreens.getOrAwaitValue
import com.learning.mvvmSample.xyzFeatureScreens.repository.MoviesRepository
import com.learning.mvvmSample.xyzFeatureScreens.repository.ResponseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


internal class MoviesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_GetProducts() = runTest{
        Mockito.`when`(repository.getMovies()).thenReturn(ResponseType.Success(emptyList()))

        val moviesViewModel = MoviesViewModel(repository)
        moviesViewModel.getMovies()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = moviesViewModel.movies.getOrAwaitValue()
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun test_GetProduct_expectedError() = runTest{
        Mockito.`when`(repository.getMovies()).thenReturn(ResponseType.Error("Something Went Wrong"))

        val moviesViewModel = MoviesViewModel(repository)
        moviesViewModel.getMovies()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = moviesViewModel.movies.getOrAwaitValue()
        Assert.assertEquals(true, result is ResponseType.Error)
        Assert.assertEquals("Something Went Wrong", result.error)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}






