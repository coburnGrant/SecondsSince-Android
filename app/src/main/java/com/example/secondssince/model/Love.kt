package com.example.secondssince.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

@Entity(tableName = "Love")
class Love(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "love_name")
    var loveName: String,

    @ColumnInfo(name = "anniversary")
    var anniversary: Date,

    @ColumnInfo(name = "love_image_uri")
    var loveImageUri: Uri? = null,
//    var loveEvents: List<LoveEvent> = emptyList<LoveEvent>()
) {
    companion object {
        fun testLove(): Love {
            return Love(
                userName = "Grant",
                loveName = "Dream",
                anniversary = Date(1638662400000)
            )
        }
    }
}

class LoveTypeConverters {
    @TypeConverter
    fun fromUri(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun toUri(uriString: String?): Uri? = uriString?.let { Uri.parse(it) }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }
}