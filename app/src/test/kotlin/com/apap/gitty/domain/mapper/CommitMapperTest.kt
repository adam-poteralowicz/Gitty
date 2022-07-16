package com.apap.gitty.domain.mapper

import com.apap.gitty.data.AuthorResponse
import com.apap.gitty.data.CommitDetailsResponse
import com.apap.gitty.data.CommitResponse
import com.apap.gitty.domain.model.Author
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.CommitDetails
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDateTime

class CommitMapperTest {

    @Test
    fun `correctly maps commit response to domain model`() {
        val sha = "sha"
        val details = CommitDetails(Author("author", LocalDateTime.now()), "message")
        val commitResponse = toCommitResponse(sha, details)
        val expectedCommit = Commit(sha, details)

        val commit = commitResponse.toCommit()

        assertThat(commit).isEqualTo(expectedCommit)
    }

    @Test
    fun `maps to null if response is null`() {
        val commitResponse = null
        val expectedCommit = null

        val commit = commitResponse?.toCommit()

        assertThat(commit).isEqualTo(expectedCommit)
    }

    @Test
    fun `maps to null if commit sha is null`() {
        val sha = null
        val details = CommitDetails(Author("author", LocalDateTime.now()), "message")
        val commitResponse = toCommitResponse(sha, details)
        val expectedCommit = null

        val commit = commitResponse.toCommit()

        assertThat(commit).isEqualTo(expectedCommit)
    }

    private fun toCommitResponse(sha: String?, details: CommitDetails?) = CommitResponse(
        sha = sha,
        commitDetails = details?.toCommitDetailsResponse()
    )

    private fun CommitDetails.toCommitDetailsResponse() =
        CommitDetailsResponse(
            author = author.toAuthorResponse(),
            message = message,
        )

    private fun Author.toAuthorResponse() = AuthorResponse(
        name = name,
        date = date,
    )
}