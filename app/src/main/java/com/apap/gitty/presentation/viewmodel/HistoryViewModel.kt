package com.apap.gitty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.apap.gitty.data.storage.LastSearchedRepositoriesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    store: LastSearchedRepositoriesStore,
) : ViewModel() {

    private val _repositoriesFlow = MutableStateFlow<List<String>>(listOf())
    val repositoriesFlow = _repositoriesFlow.asStateFlow()

    init {
        _repositoriesFlow.value = store.load()
    }
}