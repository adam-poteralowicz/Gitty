package com.apap.gitty.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RepositoryResponse(
    @Json(name = "id") val id: Int?,
)