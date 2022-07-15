package com.apap.gitty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommitDetails(
    val author: Author,
    val message: String,
) : Parcelable
