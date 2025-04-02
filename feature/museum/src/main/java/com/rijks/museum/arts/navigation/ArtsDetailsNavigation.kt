package com.rijks.museum.arts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rijks.museum.arts.ui.details.ArtsDetailsScreen
import com.rijks.museum.core.ui.navigation.Route

fun NavGraphBuilder.artsDetailsScreen() {
    composable<Route.ArtsDetailsScreen> { backStackEntry ->
        val artId = backStackEntry.toRoute<Route.ArtsDetailsScreen>().artId
        ArtsDetailsScreen(artId = artId)
    }
}
