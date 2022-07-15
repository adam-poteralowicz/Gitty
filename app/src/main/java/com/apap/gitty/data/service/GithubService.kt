package com.apap.gitty.data.service

import com.apap.gitty.data.CommitResponse
import com.apap.gitty.data.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): RepositoryResponse

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getRepositoryCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): List<CommitResponse>
}