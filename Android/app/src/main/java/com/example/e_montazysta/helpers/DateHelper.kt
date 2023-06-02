package com.example.e_montazysta.helpers

import android.icu.text.DateFormat
import java.util.Date

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
            return null
        }

        fun toJsonString(dateString: String): String {
            parse
        }
    }
}