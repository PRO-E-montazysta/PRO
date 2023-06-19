package com.example.e_montazysta.helpers

import android.content.ContentValues.TAG
import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomDateAdapter : JsonAdapter<Date>() {
    private val dateFormat1 = SimpleDateFormat(SERVER_FORMAT1, Locale.getDefault())
    private val dateFormat2 = SimpleDateFormat(SERVER_FORMAT2, Locale.getDefault())
    private val targetFormat = SimpleDateFormat(TARGET_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat1) {
                try {
                    val originalDate = dateFormat1.parse(dateAsString)
                    val transformedDateString = targetFormat.format(originalDate)
                    targetFormat.parse(transformedDateString)
                } catch (e: Exception) {
                    synchronized(dateFormat2) {
                        val originalDate = dateFormat2.parse(dateAsString)
                        val transformedDateString = targetFormat.format(originalDate)
                        targetFormat.parse(transformedDateString)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(targetFormat) {
                val transformedDateString = targetFormat.format(value)
                writer.value(transformedDateString)
            }
        }
    }

    companion object {
        const val SERVER_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss"
        const val SERVER_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
        const val TARGET_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    }
}