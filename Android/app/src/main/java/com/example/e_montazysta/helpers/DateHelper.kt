package com.example.e_montazysta.helpers

import android.icu.text.DateFormat
import java.util.Date

class DateUtil {
    companion object {
        val dateFormat = DateFormat.getDateTimeInstance()
        fun format(date: Date): String {
            return dateFormat.format(date)
        }
    }
}