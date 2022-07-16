package com.apap.gitty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.apap.gitty.data.storage.SearchedRepositoriesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    store: SearchedRepositoriesStore,
) : ViewModel() {

    private val _repositoriesFlow = MutableStateFlow<List<String>>(listOf())
    val repositoriesFlow = _repositoriesFlow.asStateFlow()

    init {
        val cachedHistory = store.load()
        _repositoriesFlow.value = cachedHistory.ifEmpty { store.loadFromSharedPreferences() }
    }
}