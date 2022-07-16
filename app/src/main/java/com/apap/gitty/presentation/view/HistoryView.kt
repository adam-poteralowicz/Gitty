package com.apap.gitty.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.gitty.R
import com.apap.gitty.presentation.Toolbar
import com.apap.gitty.presentation.viewmodel.HistoryViewModel

@Composable
fun HistoryView(
    navigateToSearchResults: (String) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val history by viewModel.repositoriesFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Toolbar(titleResId = R.string.history)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            if (history.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(history) {
                            Text(
                                text = it,
                                Modifier.clickable { navigateToSearchResults(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}