package uz.safix.shoptestapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 10:21 AM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */


class AppTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun convertLongToTime(time: Long): LocalDateTime{
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(time), ZoneId.systemDefault()
        )
    }

    @TypeConverter
    fun convertTimeToLong(time: LocalDateTime): Long{
        return time.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    @TypeConverter
    fun convertTagsToJson(tags: List<String>): String {
        return gson.toJson(tags)
    }

    @TypeConverter
    fun convertJsonToTags(str: String): List<String> {
        return gson.fromJson<List<String>>(str, List::class.java)
    }
}