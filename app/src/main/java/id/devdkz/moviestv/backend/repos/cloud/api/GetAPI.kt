package id.devdkz.moviestv.backend.repos.cloud.api

import id.devdkz.moviestv.BuildConfig
import id.devdkz.moviestv.backend.data.GenreListResponse
import id.devdkz.moviestv.backend.data.MovieDetailResponse
import id.devdkz.moviestv.backend.data.MovieTvReviewResponse
import id.devdkz.moviestv.backend.data.TvDetailResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetAPI {
    @GET("{type}/popular?api_key=${BuildConfig.API_KEY}")
    fun getList(
        @Path("type")
        type: String,
    ): Call<ResponseBody>

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    fun getOfficialGenreMovieList(): Call<ResponseBody>

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    fun getGenreList(
        @Query("with_genres")
        genre: String
    ): Call<GenreListResponse>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    fun getMovieDetail(
        @Path("id")
        id: Int
    ): Call<MovieDetailResponse>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    fun getTvDetail(
        @Path("id")
        id: Int
    ): Call<TvDetailResponse>

    @GET("{type}/{id}/reviews?api_key=${BuildConfig.API_KEY}")
    fun getMovieTvReviews(
        @Path("type")
        type: String,
        @Path("id")
        id: Int
    ): Call<MovieTvReviewResponse>
}