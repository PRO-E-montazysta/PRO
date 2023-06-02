package com.example.e_montazysta.helpers

import android.icu.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtil {
    companion object {
        val dateFormat = DateFormat.getDateTimeInstance()

        fun format(date: Date): String {
            return dateFormat.format(date)
        }

        fun format(date: Long): String {
            return dateFormat.format(date)
        }

        fun parse(dateString: String): Date? {
            return dateFormat.parse(dateString)
        }

        fun toJsonString(dateString: String): String {
            val date = dateFormat.parse(dateString)
            val serverDateFormat = SimpleDateFormat(CustomDateAdapter.SERVER_FORMAT, Locale.getDefault())
            return serverDateFormat.format(date)
        }
    }
}