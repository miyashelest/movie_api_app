package com.example.moviedbapp.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviedbapp.databinding.MovieListFragmentBinding

// MovieListFragment shows the status of request
class MovieListFragment : Fragment() {
    // Lazy initialization of MovieListViewModel allow to
    // load data only first time access the LiveData
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    // Inflates the layout with Data Binding, sets its lifecycle owner to the MovieListFragment
    // to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MovieListFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the MovieListViewModel
        binding.movieListViewModel = viewModel

        // Sets the adapter of the movie list recycler view with
        // click handler lambda that tells the ViewModel when list item is clicked
        binding.movieList.adapter = MovieListAdapter(MovieListAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        // Sets observer for navigateToSelectedProperty LiveData and navigate only if it's not null
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // find navController with action
                this.findNavController().navigate(MovieListFragmentDirections.actionShowDetail(it))
                // notify ViewModel about finished navigation to prevent multiple navigation
                viewModel.displayMovieDetailsComplete()
            }
        })

        // return required view with binding
        return binding.root
    }
}