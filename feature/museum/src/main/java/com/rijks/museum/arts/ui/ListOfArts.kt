package com.rijks.museum.arts.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState

@Composable
fun ListOfArtsScreen(
    viewModel: ListOfArtsViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LisOfArtsScreenContent(
        state = state,
        onEvent = remember { { event -> viewModel.onEvent(event) } },
    )
}

@Composable
private fun LisOfArtsScreenContent(
    state: ListOfArtsScreenState,
    onEvent: (ListOfArtsScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
) {

}


