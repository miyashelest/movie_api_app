package com.example.moviedbapp.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.MovieItem
import com.example.moviedbapp.databinding.MovieItemBinding
import com.example.moviedbapp.movielist.MovieListAdapter.*

// adapter class for recycler view movie list using data binding to show list of data
class MovieListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MovieItem, MovieListViewHolder>(DiffCallback) {

// view holder constructor takes the binding variable from associated item view
    class MovieListViewHolder(private var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItem: MovieItem) {
            //set movie item to the container
            binding.item = movieItem
            //data binding executes immediately
            // allow recycler view to make the correct size measurements
            binding.executePendingBindings()
        }
    }

    //callback to find changed items when list was updated
    companion object DiffCallback : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //create new recyclerView item view (invoked by layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        return MovieListViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    // replaces contents of a view (invoked by layout manager)
    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movieItem)
        }
        holder.bind(movieItem)
    }

    //click listener for recycle view item
    //lambda click listener that will be called with the current movie item
    class OnClickListener(val clickListener: (movieItem: MovieItem) -> Unit) {
        fun onClick(movieItem: MovieItem) = clickListener(movieItem)
    }

}