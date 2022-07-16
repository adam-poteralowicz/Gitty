package com.apap.gitty.domain.mapper

import com.apap.gitty.data.RepositoryResponse
import com.apap.gitty.domain.model.Repository
import org.assertj.core.api.Assertions
import org.junit.Test

class RepositoryMapperTest {

    @Test
    fun `correctly maps repository response to domain model`() {
        val id = 0
        val repositoryResponse = toRepositoryResponse(id)
        val expectedRepository = Repository(id)

        val commit = repositoryResponse.toRepository()

        Assertions.assertThat(commit).isEqualTo(expectedRepository)
    }

    @Test
    fun `maps to null if response is null`() {
        val repositoryResponse = null
        val expectedRepository = null

        val repository = repositoryResponse?.toCommit()

        Assertions.assertThat(repository).isEqualTo(expectedRepository)
    }

    @Test
    fun `maps to null if repository id is null`() {
        val id = null
        val repositoryResponse = toRepositoryResponse(id)
        val expectedRepository = null

        val repository = repositoryResponse.toRepository()

        Assertions.assertThat(repository).isEqualTo(expectedRepository)
    }

    private fun toRepositoryResponse(id: Int?) = RepositoryResponse(id = id)
}