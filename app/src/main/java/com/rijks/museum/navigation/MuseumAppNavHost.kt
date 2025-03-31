package com.rijks.museum.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rijks.museum.arts.navigation.listOfArtsScreen
import com.rijks.museum.core.ui.navigation.Route

@Composable
fun MuseumAppNavHost(
    navController: NavHostController,
    @Suppress("UNUSED_PARAMETER")
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    startDestination: Any = Route.ListOfArtsScreen,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination,
    ) {
        listOfArtsScreen()
    }
}
