package com.rijks.museum.arts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijks.museum.arts.ui.details.ArtsDetailsScreen
import com.rijks.museum.core.ui.navigation.Route

fun NavHostController.navigateToArtsDetailsScreen(artId: String, navOptions: NavOptions? = null) {
    this.navigate(Route.ArtsDetailsScreen(artId), navOptions)
}

fun NavGraphBuilder.artsDetailsScreen() {
    composable<Route.ArtsDetailsScreen> { backStackEntry ->
        val artId = backStackEntry.toRoute<Route.ArtsDetailsScreen>().artId
        ArtsDetailsScreen(artId = artId)
    }
}
