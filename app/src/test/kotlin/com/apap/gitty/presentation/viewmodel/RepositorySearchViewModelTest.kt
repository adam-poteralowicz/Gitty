package com.apap.gitty.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.apap.gitty.data.storage.SearchedRepositoriesStore
import com.apap.gitty.domain.model.*
import com.apap.gitty.domain.usecase.GetRepository
import com.apap.gitty.domain.usecase.GetRepositoryCommits
import com.apap.gitty.presentation.view.LoadingState
import com.apap.gitty.testUtils.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class RepositorySearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getRepository: GetRepository

    @MockK
    private lateinit var getRepositoryCommits: GetRepositoryCommits

    @MockK
    private lateinit var searchedRepositoriesStore: SearchedRepositoriesStore

    private lateinit var subject: RepositorySearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun initViewModel() {
        subject = RepositorySearchViewModel(
            getRepository = getRepository,
            getRepositoryCommits = getRepositoryCommits,
            store = searchedRepositoriesStore
        )
    }

    @Test
    fun `should emit failure when repository is not found`() = runTest {
        coEvery { getRepository("test", "repo") } returns Result.failure(Throwable())
        coEvery { getRepositoryCommits("test", "repo") } returns Result.failure(Throwable())

        initViewModel()
        subject.onRepositorySearched("test/repo")

        coVerify { getRepository("test", "repo") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit failure when repository commits are not found`() = runTest {
        coEvery { getRepository("test", "repo") } returns Result.success(Repository(id = 0))
        coEvery { searchedRepositoriesStore.add("test", "repo") } just runs
        coEvery { getRepositoryCommits("test", "repo") } returns Result.failure(Throwable())

        initViewModel()
        subject.onRepositorySearched("test/repo")

        coVerify { getRepository("test", "repo") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Pending)
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit done when repository and its commits are found`() = runTest {
        val expectedCommitList = listOf(
            Commit(
                sha = "sha",
                details = CommitDetails(
                    author = Author(name = "Author", date = LocalDateTime.now()),
                    message = "Message",
                )
            )
        )

        coEvery { getRepository("test", "repo") } returns Result.success(Repository(id = 0))
        coEvery { searchedRepositoriesStore.add("test", "repo") } just runs
        coEvery {
            getRepositoryCommits("test", "repo")
        } returns Result.success(expectedCommitList)

        initViewModel()
        subject.onRepositorySearched("test/repo")

        coVerify { getRepository("test", "repo") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Done)
            expectNoEvents()
        }
        subject.repositoryIdFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(RepositoryId(id = 0))
        }
        subject.commitsFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(expectedCommitList)
        }
    }
}