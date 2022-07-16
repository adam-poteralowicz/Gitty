package com.apap.gitty.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apap.gitty.presentation.view.HistoryView
import com.apap.gitty.presentation.view.RepositorySearchView
import com.apap.gitty.ui.theme.GittyTheme
import com.apap.gitty.util.navigateToSearchResults
import dagger.hilt.android.AndroidEntryPoint

private const val SEARCH = "search"
private const val HISTORY = "history"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GittyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = SEARCH) {
                    composable(SEARCH) { entry ->
                        RepositorySearchView(
                            owner = entry.arguments?.getString("owner"),
                            repo = entry.arguments?.getString("repo"),
                            onHistoryOpened = { navController.navigate(HISTORY) }
                        )
                    }
                    composable(HISTORY) {
                        HistoryView(
                            navigateToSearchResults = {
                                val data = it.split('/')
                                val bundle = Bundle()
                                bundle.putString("owner", data[0])
                                bundle.putString("repo", data[1])
                                navController.navigateToSearchResults(bundle)
                            }
                        )
                    }
                }
            }
        }
    }
}