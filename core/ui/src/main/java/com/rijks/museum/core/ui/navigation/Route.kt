package com.rijks.museum.core.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object ListOfArtsScreen : Route

}
