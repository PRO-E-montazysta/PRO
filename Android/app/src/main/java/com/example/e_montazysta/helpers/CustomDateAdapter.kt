package com.example.e_montazysta.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomDateAdapter : JsonAdapter<LocalDateTime>() {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(SERVER_FORMAT)

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return try {
            val dateAsString = reader.nextString()
            LocalDateTime.parse(dateAsString, dateTimeFormatter)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            writer.value(value.format(dateTimeFormatter))
        }
    }

    companion object {
        const val SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS" // define your server format here
    }
}
