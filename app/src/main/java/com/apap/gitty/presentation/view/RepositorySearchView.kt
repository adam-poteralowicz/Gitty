package com.apap.gitty.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apap.gitty.R
import com.apap.gitty.domain.model.Commit
import com.apap.gitty.presentation.Toolbar
import com.apap.gitty.presentation.viewmodel.RepositorySearchViewModel
import com.apap.gitty.ui.theme.Purple200

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun RepositorySearchView(
    modifier: Modifier = Modifier,
    owner: String?,
    repo: String?,
    onHistoryOpened: () -> Unit,
    sendEmail: (Intent) -> Unit,
    viewModel: RepositorySearchViewModel = hiltViewModel()
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var input by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    val commits by viewModel.commitsFlow.collectAsState()
    val repositoryId by viewModel.repositoryIdFlow.collectAsState()
    val state by viewModel.loadingStateFlow.collectAsState()
    val emailIntent by viewModel.emailIntentFlow.collectAsState()

    LaunchedEffect(Unit) {
        if (owner != null && repo != null) {
            viewModel.onRepositorySearched("$owner/$repo")
        }
    }

    LaunchedEffect(emailIntent) {
        emailIntent?.let { sendEmail(it) }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Bottom),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Toolbar(
                titleResId = R.string.app_name,
                navigationAction = { onHistoryOpened() }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
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
                    if (input.isNotEmpty() && input.contains('/')) {
                        viewModel.onRepositorySearched(input.trim())
                    }
                }
                LoadingComponent(
                    success = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            repositoryId?.let {
                                SearchTitle(owner, repo, it.id)
                            }
                            if (commits.isNotEmpty()) {
                                CommitsList(commits, snackbarHostState)
                            }
                        }
                    },
                    error = {
                        SearchErrorText()
                    },
                    loadingState = state,
                )
            }
        }
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
        modifier = Modifier.padding(bottom = 8.dp),
        label = { Text(text = stringResource(R.string.repository_search_hint)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onDone),
    )
}

@Composable
fun SearchButton(action: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 8.dp)
    ) {
        Button(
            onClick = { action() },
            modifier = Modifier
                .background(color = Color.Black, shape = CircleShape)
        ) {
            Text(
                text = stringResource(R.string.search),
                color = Color.White,
            )
        }
    }
}

@Composable
fun CommitItem(
    commit: Commit,
    snackbarHostState: SnackbarHostState,
    viewModel: RepositorySearchViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .background(Purple200, shape = RectangleShape)
            .clickable {
                viewModel.addSelectedCommit(commit) {
                    when (snackbarHostState.showSnackbar(
                        "Selected commit for sending",
                        actionLabel = "Send"
                    )) {
                        SnackbarResult.ActionPerformed -> viewModel.onSnackbarActionPerformed()
                        else -> Unit
                    }
                }
            }
    ) {
        Text(
            text = commit.details.message,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = commit.sha,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.W300,
        )
        Text(text = commit.details.author.name, maxLines = 1)
    }
}

@Composable
fun SearchTitle(owner: String?, repo: String?, id: Int) {
    Text(
        modifier = Modifier.padding(
            vertical = 16.dp,
            horizontal = 16.dp
        ),
        text = if (owner != null && repo != null) {
            "$owner / $repo ($id)"
        } else "$id",
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun CommitsList(commits: List<Commit>, snackbarHostState: SnackbarHostState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(commits) {
                CommitItem(it, snackbarHostState)
                disableCircularProgress()
            }
        }
    }
}

@Composable
fun SearchErrorText() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.no_repository_found_error))
    }
}