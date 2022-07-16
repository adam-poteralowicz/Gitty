package com.apap.gitty.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.apap.gitty.data.storage.SearchedRepositoriesStore
import com.apap.gitty.testUtils.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var searchedRepositoriesStore: SearchedRepositoriesStore

    private lateinit var subject: HistoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun initViewModel() {
        subject = HistoryViewModel(store = searchedRepositoriesStore)
    }

    @Test
    fun `should attempt to load cached history on init`() {
        every { searchedRepositoriesStore.load() } returns listOf("test/test")

        initViewModel()

        verify { searchedRepositoriesStore.load() }
    }

    @Test
    fun `should attempt to load history from shared preferences when cache is empty`() = runTest {
        every { searchedRepositoriesStore.load() } returns emptyList()
        every { searchedRepositoriesStore.loadFromSharedPreferences() } returns emptyList()

        initViewModel()

        verify { searchedRepositoriesStore.load() }
        verify { searchedRepositoriesStore.loadFromSharedPreferences() }

        subject.repositoriesFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(emptyList<String>())
        }
    }
}