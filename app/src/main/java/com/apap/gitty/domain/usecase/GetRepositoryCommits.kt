package com.apap.gitty.domain.usecase

import com.apap.gitty.data.repository.GithubRepository
import javax.inject.Inject

class GetRepositoryCommits @Inject constructor(
    private val repository: GithubRepository,
) {

    suspend operator fun invoke(owner: String, name: String) = repository.getCommits(owner, name)
}