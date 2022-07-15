package com.apap.gitty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Author(
    val name: String,
    val date: LocalDateTime,
) : Parcelable
