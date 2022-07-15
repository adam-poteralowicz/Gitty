package com.apap.gitty.domain.mapper

import com.apap.gitty.data.AuthorResponse
import com.apap.gitty.data.CommitDetailsResponse
import com.apap.gitty.data.CommitResponse
import com.apap.gitty.domain.model.Author
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.CommitDetails

fun List<CommitResponse>?.toCommits() = this?.mapNotNull {
    it.toCommit()
}

fun CommitResponse.toCommit(): Commit? {
    return Commit(
        sha = sha ?: return null,
        details = this.commitDetails.toCommitDetails() ?: return null,
    )
}

fun CommitDetailsResponse?.toCommitDetails(): CommitDetails? {
    return CommitDetails(
        author = this?.author.toAuthor() ?: return null,
        message = this?.message ?: return null,
    )
}

fun AuthorResponse?.toAuthor(): Author? {
    return Author(
        name = this?.name ?: return null,
        date = date ?: return null,
    )
}