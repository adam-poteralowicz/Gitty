package com.apap.gitty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.domain.model.RepositoryId
import com.apap.gitty.domain.usecase.GetRepository
import com.apap.gitty.domain.usecase.GetRepositoryCommits
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val getRepository: GetRepository,
    private val getRepositoryCommits: GetRepositoryCommits,
) : ViewModel() {

    private val _commitsFlow = MutableStateFlow<List<Commit>>(listOf())
    val commitsFlow = _commitsFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow(LoadingState.Idle)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _repositoryIdFlow = MutableStateFlow<RepositoryId?>(null)
    val repositoryIdFlow = _repositoryIdFlow.asStateFlow()

    fun onRepositorySearched(input: String) {
        viewModelScope.launch {

        }
    }
}