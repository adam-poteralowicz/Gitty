package com.apap.gitty.domain

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

class LocalDateTimeJsonAdapter {

    @ToJson
    fun toJson(localDateTime: LocalDateTime): String {
        return localDateTime.format(
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        )
    }

    @FromJson
    fun fromJson(json: String): LocalDateTime {
        return LocalDateTime.parse(json.dropLast(1)) // drop Z at the end of date
    }
}