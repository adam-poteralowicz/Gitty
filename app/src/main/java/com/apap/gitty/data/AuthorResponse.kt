package com.apap.gitty.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class AuthorResponse(
    @Json(name = "name") val name: String?,
    @Json(name = "date") val date: LocalDateTime?,
)
