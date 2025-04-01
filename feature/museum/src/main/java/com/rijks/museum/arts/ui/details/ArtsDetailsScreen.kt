package com.rijks.museum.arts.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rijks.museum.arts.R
import com.rijks.museum.arts.common.RemoteIcon
import com.rijks.museum.arts.ui.components.BaseToolbar
import com.rijks.museum.arts.ui.components.Loading
import com.rijks.museum.arts.ui.components.LoadingError
import com.rijks.museum.arts.ui.components.NavigateBack
import com.rijks.museum.arts.ui.details.ArtDetailsScreenEvents.Back
import com.rijks.museum.arts.ui.state.ArtDetailsScreenState
import com.rijks.museum.core.ui.theme.RijksMuseumAppTheme
import com.rijks.museum.core.ui.utils.EMPTY_STRING
import com.rijks.museum.domain.model.UiArtDetails

@Composable
fun ArtsDetailsScreen(
    artId: String,
    viewModel: ArtDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val hasLoadedData = rememberSaveable { artId }

    LaunchedEffect(hasLoadedData) {
        viewModel.onEvent(ArtDetailsScreenEvents.Loading(artId))
    }

    ArtDetailsScreenContent(
        artId = artId,
        state = state,
        onEvent = remember { { event -> viewModel.onEvent(event) } },
    )
}

@Composable
private fun ArtDetailsScreenContent(
    state: ArtDetailsScreenState,
    onEvent: (ArtDetailsScreenEvents) -> Unit,
    artId: String,
) {
    Scaffold(topBar = {
        BaseToolbar(
            title = stringResource(R.string.toolbar_details_title),
            navigationIcon = {
                NavigateBack { onEvent(Back) }
            }
        )
    }) { contentPadding ->
        if (state.isLoading) {
            Loading()
        } else {
            if (state.error == null) {
                DetailsContent(contentPadding, state)
            } else {
                LoadingError(error = state.error, onClick = { onEvent(ArtDetailsScreenEvents.Loading(artId)) })
            }
        }
    }
}

@Composable
private fun DetailsContent(
    contentPadding: PaddingValues,
    state: ArtDetailsScreenState
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RemoteIcon(
                url = state.content.image,
                description = state.content.description,
                modifier = Modifier
                    .size(128.dp)
                    .padding(16.dp)
            )
            Column {
                Text(
                    text = state.content.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Text(
                    text = state.content.subTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Text(
            text = state.content.longTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = state.content.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun ArtDetailsScreenContentPreview() {
    RijksMuseumAppTheme {
        ArtDetailsScreenContent(
            state = ArtDetailsScreenState(
                content = UiArtDetails(
                    id = "1",
                    title = "Model of a Capstan",
                    description = "Art Description",
                    image = EMPTY_STRING,
                    author = "Artist Name",
                    subTitle = "h 25cm × w 25.5cm × d 25.5cm",
                    longTitle = "Model of a Capstan, 's Lands Werf Amsterdam (possibly), 1794"
                )
            ),
            onEvent = {},
            artId = EMPTY_STRING
        )
    }
}
