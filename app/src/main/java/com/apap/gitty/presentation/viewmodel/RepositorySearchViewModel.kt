package com.apap.gitty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apap.gitty.data.storage.LastSearchedRepositoriesStore
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.RepositoryId
import com.apap.gitty.domain.usecase.GetRepository
import com.apap.gitty.domain.usecase.GetRepositoryCommits
import com.apap.gitty.presentation.view.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val getRepository: GetRepository,
    private val getRepositoryCommits: GetRepositoryCommits,
    private val store: LastSearchedRepositoriesStore,
) : ViewModel() {

    private val _commitsFlow = MutableStateFlow<List<Commit>>(listOf())
    val commitsFlow = _commitsFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow(LoadingState.Idle)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _repositoryIdFlow = MutableStateFlow<RepositoryId?>(null)
    val repositoryIdFlow = _repositoryIdFlow.asStateFlow()

    private val selectedCommits = mutableListOf<Commit>()

    fun onRepositorySearched(input: String) {
        viewModelScope.launch {
            _loadingStateFlow.value = LoadingState.Pending
            val details = input.split('/')
            val owner = details[0]
            val name = details[1]

            val repository = withContext(Dispatchers.IO) { getRepository(owner, name).getOrNull() }
            val repositoryCommits = withContext(Dispatchers.IO) {
                getRepositoryCommits(owner, name).getOrNull()
                    ?.sortedByDescending { it.details.author.date }
            }

            repository?.let {
                _repositoryIdFlow.value = RepositoryId(it.id)
                store.add(owner, name)
            } ?: run { _loadingStateFlow.value = LoadingState.Failure }
            repositoryCommits?.let {
                _commitsFlow.value = it
                _loadingStateFlow.value = LoadingState.Done
            } ?: run { _loadingStateFlow.value = LoadingState.Failure }
        }
    }

    fun addSelectedCommit(commit: Commit, action: suspend () -> Unit) {
        viewModelScope.launch {
            if (selectedCommits.contains(commit).not()) {
                selectedCommits += commit
                action()
            }
        }
    }
}