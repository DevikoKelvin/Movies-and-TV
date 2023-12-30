package id.devdkz.moviestv.backend.dataconvert

import id.devdkz.moviestv.backend.data.Genres
import id.devdkz.moviestv.backend.repos.local.entities.BookmarkEnt
import id.devdkz.moviestv.backend.repos.local.entities.MovieEnt
import id.devdkz.moviestv.backend.repos.local.entities.TVEnt
import id.devdkz.moviestv.backend.data.MainData
import id.devdkz.moviestv.backend.data.MovieDetailResponse
import id.devdkz.moviestv.backend.data.TvDetailResponse
import id.devdkz.moviestv.backend.repos.local.entities.GenresEnt

object Converter {
    fun movieEntityToMainData(listDB: List<MovieEnt>): ArrayList<MainData> {
        val list = ArrayList<MainData>()
        for (element in listDB) {
            list.add(
                MainData(
                    element.id,
                    element.img,
                    element.title,
                    element.rating,
                    element.release,
                    element.syn
                )
            )
        }

        return list
    }

    fun mainDataToMovieEntity(listDB: List<MainData>): ArrayList<MovieEnt> {
        val list = ArrayList<MovieEnt>()
        for (element in listDB) {
            list.add(
                MovieEnt(
                    element.id,
                    element.img,
                    element.title,
                    element.rating,
                    element.release,
                    element.syn
                )
            )
        }

        return list
    }

    fun tvEntityToMainData(listDB: List<TVEnt>): ArrayList<MainData> {
        val list = ArrayList<MainData>()
        for (element in listDB) {
            list.add(
                MainData(
                    element.id,
                    element.img,
                    element.title,
                    element.rating,
                    element.release,
                    element.syn
                )
            )
        }

        return list
    }

    fun mainDataToTvEntity(listDB: List<MainData>): ArrayList<TVEnt> {
        val list = ArrayList<TVEnt>()
        for (element in listDB) {
            list.add(
                TVEnt(
                    element.id,
                    element.img,
                    element.title,
                    element.rating,
                    element.release,
                    element.syn
                )
            )
        }

        return list
    }

    fun genreEntityToGenres(listDB: List<GenresEnt>): ArrayList<Genres> {
        val list = ArrayList<Genres>()
        for (element in listDB) {
            list.add(
                Genres(
                    element.id,
                    element.name
                )
            )
        }

        return list
    }

    fun genresToGenreEntity(listDB: List<Genres>): ArrayList<GenresEnt> {
        val list = ArrayList<GenresEnt>()
        for (element in listDB) {
            list.add(
                GenresEnt(
                    element.id,
                    element.name
                )
            )
        }

        return list
    }

    fun bookmarkEntityToMainData(listDB: List<BookmarkEnt>): ArrayList<MainData> {
        val list = ArrayList<MainData>()
        for (element in listDB) {
            list.add(
                MainData(
                    element.id,
                    element.img,
                    element.title,
                    element.rating,
                    element.release,
                    element.syn
                )
            )
        }

        return list
    }

    fun MovieEnt.movieDetailToMainData() = MainData(
        id = id,
        img = img,
        title = title,
        rating = rating,
        release = release,
        syn = syn
    )

    fun TVEnt.tvDetailToMainData() = MainData(
        id = id,
        img = img,
        title = title,
        rating = rating,
        release = release,
        syn = syn
    )

    fun MovieDetailResponse.movieDetailResponseToBookmarkEntity(cat: String) = BookmarkEnt(
        id = id!!,
        img = posterPath!!,
        title = title!!,
        rating = voteAverage as Double,
        release = releaseDate!!,
        syn = overview!!,
        cat
    )

    fun TvDetailResponse.tvDetailResponseToBookmarkEntity(cat: String) = BookmarkEnt(
        id = id!!,
        img = posterPath!!,
        title = name!!,
        rating = voteAverage as Double,
        release = firstAirDate!!,
        syn = overview!!,
        cat
    )
}