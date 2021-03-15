package com.example.moviedbapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
// gets movie list from TheMovieDB with retrofit and updates MovieItem and apiStatus
// retrofit return a coroutine Deferred, which we await to get the result of request
@Parcelize
data class MovieItem(
    val id: Int,
    @Json(name = "poster_path")
    val posterPath: String,
    val title: String,
    val overview: String
) : Parcelable