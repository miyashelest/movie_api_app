package com.example.moviedbapp

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.themoviedb.org/3/"
private val gson = GsonConverterFactory.create(GsonBuilder().create())
// moshi object with kotlin adapter to convert json request data
private val moshi = MoshiConverterFactory.create(
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
)

// retrofit object with moshi object
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(gson)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

// public interface that exposes getTrending method
interface MovieDBApiService {
    // return a coroutine deffered list of movies
    // can be fetched with await() if in a Coroutine scope.
    @GET("trending/all/day?")
    fun getTrending(@Query("api_key") apiKey: String):
            //coroutine call adapter allows to return a deferred list
            //a job with result
            Deferred<List<MovieItem>>
}
// public api object that exposes the lazy-initialized retrofit service
object MovieApi {
    val RETROFIT_SERVICE: MovieDBApiService by lazy {
        retrofit.create(MovieDBApiService::class.java)
    }
}


