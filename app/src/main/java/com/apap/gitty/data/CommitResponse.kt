package com.apap.gitty.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitResponse(
    @Json(name = "sha") val sha: String?,
    @Json(name = "commit") val commitDetails: CommitDetailsResponse?,
)