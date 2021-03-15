package com.example.moviedbapp.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedbapp.MovieApi
import com.example.moviedbapp.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// request status
enum class ApiStatus { LOADING, ERROR, DONE }

// ViewModel attached to the MovieListFragment
class MovieListViewModel : ViewModel() {
    // api key for request access
    private val apiKey = "000a82d1c4bed4e33c6ef6298df85f0c"

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // the internal MutableLiveData that stores the response
    private val _response = MutableLiveData<List<MovieItem>>()
    val response: LiveData<List<MovieItem>>
        get() = _response

    // Internal. MutableLiveData to handle navigation to the selected movie
    private val _navigateToSelectedMovie = MutableLiveData<MovieItem>()

    // External. Immutable LiveData for the navigation
    val navigateToSelectedMovie: LiveData<MovieItem>
        get() = _navigateToSelectedMovie

    // Coroutine scope created with the job. Can be cancelled when needed
    private var viewModelJob = Job()

    // coroutine runs using the main(=ui) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // call getMovieList on init block to display data immediately
    init {
        getMovieList()
    }


    // getMovieList function gets movie list using Retrofit
    // update Movie list  and live data
    // retrofit returns a coroutine deferred which we await to get the result of request
    private fun getMovieList() {
        coroutineScope.launch {
            // Get the Deferred object for Retrofit request
            val getMoviesDeferred = MovieApi.RETROFIT_SERVICE.getTrending(apiKey)
            try {
                _status.value = ApiStatus.LOADING
                //entry point for a thread managed by retrofit
                val listResult = getMoviesDeferred.await()
                _status.value = ApiStatus.DONE
                _response.value = listResult
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _response.value = ArrayList()
            }
        }
    }

    // cancel coroutine to stop ViewModelJob when ViewModel is finished
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // when the movie is clicked set the value of _navigateToSelectedMovie to movieItem
    fun displayMovieDetails(movieItem: MovieItem) {
        _navigateToSelectedMovie.value = movieItem
    }

    // after the navigation is done, set value to null
    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }
}

