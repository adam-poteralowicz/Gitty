package com.apap.gitty.data.repository

import com.apap.gitty.data.service.GithubService
import com.apap.gitty.domain.mapper.toCommits
import com.apap.gitty.domain.mapper.toRepository
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.Repository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubService,
) : GithubRepository {

    override suspend fun getRepository(owner: String, name: String): Result<Repository> =
        Result.runCatching {
            requireNotNull(service.getRepository(owner, name).toRepository())
        }

    override suspend fun getCommits(owner: String, name: String): Result<List<Commit>> =
        Result.runCatching {
            requireNotNull(service.getRepositoryCommits(owner, name).toCommits())
        }
}