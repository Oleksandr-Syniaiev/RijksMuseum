package com.rijks.museum.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rijks.museum.core.ui.navigation.NavigationAction
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.ui.utils.ObserveAsEvents
import com.rijks.museum.navigation.MuseumAppNavHost

@Suppress("ModifierMissing")
@Composable
fun RijksMuseumApp(navigator: Navigator) {
    val navController: NavHostController = rememberNavController()

    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.destination
            ) {
                action.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    Scaffold { innerPadding ->
        MuseumAppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
