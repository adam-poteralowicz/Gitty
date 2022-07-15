package com.apap.gitty.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitDetailsResponse(
    @Json(name = "author") val author: AuthorResponse?,
    @Json(name = "message") val message: String?,
)
