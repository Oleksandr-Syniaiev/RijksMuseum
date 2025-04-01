package com.rijks.museum.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.rijks.museum.arts.navigation.artsDetailsScreen
import com.rijks.museum.arts.navigation.listOfArtsScreen
import com.rijks.museum.core.ui.navigation.Route

@Composable
fun MuseumAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Route.ArtsGraph,
    ) {
        navigation<Route.ArtsGraph>(Route.ListOfArtsScreen){
            listOfArtsScreen()
            artsDetailsScreen()
        }
    }
}
