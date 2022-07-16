package com.apap.gitty.util

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*

private const val SEARCH = "search"

fun NavController.navigateToSearchResults(
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {

    val routeLink = NavDeepLinkRequest.Builder
        .fromUri(NavDestination.createRoute(SEARCH).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    deepLinkMatch?.let {
        navigate(it.destination.id, args, navOptions, navigatorExtras)
    } ?: navigate(SEARCH, navOptions, navigatorExtras)
}