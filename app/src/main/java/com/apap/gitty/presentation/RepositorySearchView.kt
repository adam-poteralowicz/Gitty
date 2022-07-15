package com.apap.gitty.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.gitty.R
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.ui.theme.GittyTheme

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun RepositorySearchView(viewModel: RepositorySearchViewModel = hiltViewModel()) {

    val commits by viewModel.commitsFlow.collectAsState()
    val repositoryId by viewModel.repositoryIdFlow.collectAsState()
    val state by viewModel.loadingStateFlow.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    var input by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Toolbar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            RepositorySearchTextField(
                value = input,
                onValueChange = {
                    input = it
                },
                onDone = {
                    keyboardController?.hide()
                }
            )
            SearchButton {
                viewModel.onRepositorySearched(input)
            }
        }
        LoadingComponent(
            success = {
                repositoryId?.let { Text(text = "$it.id") }
                if (commits.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(commits) {
                                CommitItem(it)
                                disableCircularProgress()
                            }
                        }
                    }
                }
            },
            loadingState = state,
        )
    }
}

@Composable
fun RepositorySearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.padding(bottom = 30.dp),
        label = {
            Text(text = stringResource(R.string.repository_search_hint))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = onDone,
        ),
    )
}

@Composable
fun SearchButton(action: () -> Unit) {
    Button(
        onClick = { action() },
        modifier = Modifier.background(color = Color.Black, shape = CircleShape)
    ) {
        Text(
            text = stringResource(R.string.search),
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CommitItem(commit: Commit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Text(text = commit.details.message)
        Text(text = commit.sha)
        Text(text = commit.details.author.name)
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Preview
@Composable
fun Preview() {
    GittyTheme {
        RepositorySearchView()
    }
}