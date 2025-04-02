package com.rijks.museum.arts.ui.details

import androidx.lifecycle.SavedStateHandle
import com.rijks.museum.arts.tools.addReducerMockkRule
import com.rijks.museum.arts.ui.details.ArtDetailsViewModel.Companion.ART_CONTENT_KEY
import com.rijks.museum.arts.ui.details.ArtDetailsViewModel.Companion.ART_ID_KEY
import com.rijks.museum.arts.ui.details.ArtDetailsViewModel.Companion.HAS_LOADED_KEY
import com.rijks.museum.arts.ui.details.utils.GetArtDetailsUseCaseFixture
import com.rijks.museum.arts.ui.details.utils.errorEventToState
import com.rijks.museum.arts.ui.details.utils.loadedEventToState
import com.rijks.museum.arts.ui.reducers.ArtDetailsReducer
import com.rijks.museum.arts.ui.state.ArtDetailsScreenState
import com.rijks.museum.core.testing.base.BaseTestConfig
import com.rijks.museum.core.testing.base.TestConfig
import com.rijks.museum.core.testing.rules.MainDispatcherRule
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ArtDetailsViewModelTest : TestConfig by BaseTestConfig() {
    @get:Rule
    val dispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var viewModel: ArtDetailsViewModel
    private val uiStateObserver: (ArtDetailsScreenState) -> Unit = mockk()

    private val getArtDetailsUseCaseFixture: GetArtDetailsUseCaseFixture = GetArtDetailsUseCaseFixture()

    private val reducer: ArtDetailsReducer = mockk()
    private val navigator: Navigator = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private val initialLoadingState =
        ArtDetailsScreenState(
            content = UiArtDetails(),
        )

    @Test
    fun `test successful details loading`() =
        runTest {
            val objectNumber = "objectNumber1"
            val content = UiArtDetails(id = objectNumber, title = "Title 1")
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    content,
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(
                result = SealedResult.success(content),
                objectNumber = objectNumber,
            )
            thenUiStateEmitted(dataSuccessfullyLoadedState)
        }

    @Test
    fun `test error details loading`() =
        runTest {
            val objectNumber = "objectNumber2"
            val error = DataError.Network.NO_INTERNET
            val (errorEvent, errorState) = errorEventToState(initialLoadingState, error)

            reducer.addReducerMockkRule(errorEvent, errorState)
            givenInit(
                result = SealedResult.failure(error),
                objectNumber = objectNumber,
            )
            thenUiStateEmitted(errorState)
        }

    @Test
    fun `test on back click navigation`() =
        runTest {
            val objectNumber = "objectNumber1"
            val content = UiArtDetails(id = objectNumber, title = "Title 1")
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    content,
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(
                result = SealedResult.success(content),
                objectNumber = objectNumber,
            )

            val backEvent = ArtDetailsScreenEvents.Back

            reducer.addReducerMockkRule(backEvent, dataSuccessfullyLoadedState)

            coEvery {
                navigator.navigateUp()
            } returns Unit

            viewModel.onEvent(ArtDetailsScreenEvents.Back)

            coVerify {
                navigator.navigateUp()
            }
        }

    private fun givenInit(
        result: SealedResult<UiArtDetails, DataError>,
        objectNumber: String,
    ) {
        val loadingEvent = ArtDetailsScreenEvents.Loading(objectNumber)
        reducer.addReducerMockkRule(loadingEvent, initialLoadingState)
        every { uiStateObserver.invoke(any()) } returns Unit
        every { savedStateHandle.get<Boolean>(HAS_LOADED_KEY) } returns false
        every { savedStateHandle.get<String>(ART_ID_KEY) } returns null
        every { savedStateHandle.get<UiArtDetails>(ART_CONTENT_KEY) } returns null
        every { savedStateHandle.set<Any>(any<String>(), any()) } just runs

        getArtDetailsUseCaseFixture.givenArtDetails(
            objectNumber = objectNumber,
            result = result,
        )

        viewModel =
            ArtDetailsViewModel(
                getArtDetailsUseCase = getArtDetailsUseCaseFixture.useCase,
                reducer = reducer,
                navigator = navigator,
                savedStateHandle = savedStateHandle,
            )
        viewModel.uiState
            .onEach {
                uiStateObserver(it)
            }
            .onCompletion { it?.printStackTrace() }
            .launchIn(testScope)

        viewModel.onEvent(loadingEvent)
    }

    private fun thenUiStateEmitted(uiState: ArtDetailsScreenState) {
        verify {
            uiStateObserver.invoke(match { it == uiState })
        }
    }
}
