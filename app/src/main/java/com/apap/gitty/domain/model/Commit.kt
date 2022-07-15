package com.apap.gitty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Commit(
    val sha: String,
    val details: CommitDetails,
) : Parcelable