package id.devdkz.moviestv.backend.repos.local

import androidx.lifecycle.LiveData
import id.devdkz.moviestv.backend.repos.local.database.DBQuery
import id.devdkz.moviestv.backend.repos.local.entities.BookmarkEnt
import id.devdkz.moviestv.backend.repos.local.entities.GenresEnt
import id.devdkz.moviestv.backend.repos.local.entities.MovieEnt
import id.devdkz.moviestv.backend.repos.local.entities.TVEnt

class LocalSource private constructor(private val query: DBQuery) {
    companion object {
        @Volatile
        private var INSTANCE: LocalSource? = null

        fun getInstance(query: DBQuery): LocalSource =
            INSTANCE ?: LocalSource(query)
    }

    fun getListMovies(): List<MovieEnt> = query.getListMovies()

    fun getListTv(): List<TVEnt> = query.getListTv()

    fun getMovieGenre(): List<GenresEnt> = query.getListMovieGenre()

    fun getDetailMovie(id: Int): MovieEnt = query.getDetailMovieById(id)

    fun getDetailTv(id: Int): TVEnt = query.getDetailTvById(id)

    fun insertMovies(movies: ArrayList<MovieEnt>) = query.insertMovies(movies)

    fun insertTv(tv: ArrayList<TVEnt>) = query.insertTv(tv)

    fun insertMovieGenre(genre: ArrayList<GenresEnt>) = query.insertMovieGenre(genre)

    // Bookmark
    fun getListBookmark(cat: String): List<BookmarkEnt> = query.getListBookmark(cat)

    fun checkBookmark(id: Int) = query.checkBookmark(id)

    fun getBookmarkDetail(id: Int, cat: String): LiveData<BookmarkEnt> =
        query.getBookmarkDetail(id, cat)

    fun insertToBookmark(bookmark: BookmarkEnt) = query.insertToBookmark(bookmark)

    fun deleteFromBookmark(bookmark: BookmarkEnt) = query.deleteFromBookmark(bookmark)
}