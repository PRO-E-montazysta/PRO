package com.example.e_montazysta.helpers

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.math.BigDecimal

class BigDecimalAdapter: JsonAdapter<BigDecimal>() {
    override fun fromJson(reader: JsonReader): BigDecimal? {
        val bigDecimalAsString = reader.nextString()
        return BigDecimal(bigDecimalAsString)
    }

    override fun toJson(writer: JsonWriter, value: BigDecimal?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}