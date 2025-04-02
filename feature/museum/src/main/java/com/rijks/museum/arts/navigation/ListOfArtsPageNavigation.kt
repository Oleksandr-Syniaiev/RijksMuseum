package com.rijks.museum.arts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rijks.museum.arts.ui.list.ListOfArtsScreen
import com.rijks.museum.core.ui.navigation.Route

fun NavGraphBuilder.listOfArtsScreen() {
    composable<Route.ListOfArtsScreen> {
        ListOfArtsScreen()
    }
}
