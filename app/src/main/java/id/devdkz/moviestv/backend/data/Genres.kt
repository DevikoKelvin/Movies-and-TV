package id.devdkz.moviestv.backend.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    val id: Int,
    val name: String
): Parcelable
