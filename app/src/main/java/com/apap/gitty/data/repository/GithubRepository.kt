package com.apap.gitty.data.repository

import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.Repository

interface GithubRepository {

    suspend fun getRepository(owner: String, name: String) : Result<Repository>
    suspend fun getCommits(owner: String, name: String) : Result<List<Commit>>
}