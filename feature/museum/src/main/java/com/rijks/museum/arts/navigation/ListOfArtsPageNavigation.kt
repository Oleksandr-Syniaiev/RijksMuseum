package com.rijks.museum.arts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rijks.museum.core.ui.navigation.Route
import com.rijks.museum.arts.ui.ListOfArtsScreen

fun NavHostController.navigateToListOfArtsScreen(navOptions: NavOptions? = null) {
    this.navigate(Route.ListOfArtsScreen, navOptions)
}

fun NavGraphBuilder.listOfArtsScreen(
) {
    composable<Route.ListOfArtsScreen> {
        ListOfArtsScreen()
    }
}
