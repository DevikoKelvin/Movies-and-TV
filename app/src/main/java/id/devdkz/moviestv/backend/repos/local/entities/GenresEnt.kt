package id.devdkz.moviestv.backend.repos.local.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "genres_table")
@Parcelize
data class GenresEnt(
    @PrimaryKey @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
) : Parcelable
