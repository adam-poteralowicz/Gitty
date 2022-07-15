package com.apap.gitty.domain.mapper

import com.apap.gitty.data.RepositoryResponse
import com.apap.gitty.domain.model.Repository

fun RepositoryResponse?.toRepository() = this?.let {
    Repository(it.id ?: return null)
}
