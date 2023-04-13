package com.example.e_montazysta.helpers

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class MapHelper {
    companion object {
        private fun <T : Any> toMap(obj: T): Map<String, Any?> {
            return (obj::class as KClass<T>).memberProperties.associate { prop ->
                prop.name to prop.get(obj)?.let { value ->
                    if (value::class.isData) {
                        toMap(value)
                    } else {
                        value
                    }
                }
            }
        }

        fun <T : Any> toMapNotNull(obj: T): Map<String, Any?> {
            return toMap(obj).filterValues { it != null }
        }
    }
}