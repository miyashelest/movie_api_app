package com.example.moviedbapp.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.MovieDetailsFragmentBinding

class MovieDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MovieDetailsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false)
        return binding.root
    }
}