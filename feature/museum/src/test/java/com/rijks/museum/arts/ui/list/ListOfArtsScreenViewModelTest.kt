package com.rijks.museum.arts.ui.list

import com.rijks.museum.arts.tools.addReducerMockkRule
import com.rijks.museum.arts.tools.listOfArtsFirstPageStubs
import com.rijks.museum.arts.tools.listOfArtsNextPageStubs
import com.rijks.museum.arts.ui.list.ListOfArtsViewModel.Companion.PAGE_SIZE
import com.rijks.museum.arts.ui.list.ListOfArtsViewModel.Companion.START_PAGE
import com.rijks.museum.arts.ui.list.utils.GetGroupedListOfArtsUseCaseFixture
import com.rijks.museum.arts.ui.list.utils.MergePagingDataUseCaseFixture
import com.rijks.museum.arts.ui.list.utils.errorEventToState
import com.rijks.museum.arts.ui.list.utils.itemClickEventToState
import com.rijks.museum.arts.ui.list.utils.loadMoreEventToState
import com.rijks.museum.arts.ui.list.utils.loadedEventToState
import com.rijks.museum.arts.ui.list.utils.loadedMoreEventToState
import com.rijks.museum.arts.ui.list.utils.pagingErrorEventToState
import com.rijks.museum.arts.ui.reducers.ListOfArtsReducer
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.arts.ui.state.LoadingState
import com.rijks.museum.core.testing.base.BaseTestConfig
import com.rijks.museum.core.testing.base.TestConfig
import com.rijks.museum.core.testing.rules.MainDispatcherRule
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.ui.navigation.Route
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.ui.utils.states.emptyComposeMap
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtsObject
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ListOfArtsScreenViewModelTest : TestConfig by BaseTestConfig() {
    @get:Rule
    val dispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var viewModel: ListOfArtsViewModel
    private val uiStateObserver: (ListOfArtsScreenState) -> Unit = mockk()

    private val getGroupedListOfArtsUseCaseFixture: GetGroupedListOfArtsUseCaseFixture = GetGroupedListOfArtsUseCaseFixture()
    private val mergePagingDataUseCaseFixture: MergePagingDataUseCaseFixture = MergePagingDataUseCaseFixture()

    private val reducer: ListOfArtsReducer = mockk()
    private val navigator: Navigator = mockk()

    private val initialLoadingState =
        ListOfArtsScreenState(
            content = emptyComposeMap(),
            loadingState = LoadingState(isFullLoading = true),
        )

    @Test
    fun `test successful list loading`() =
        runTest {
            val firstPageArts = listOfArtsFirstPageStubs()
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    ComposeMap(firstPageArts),
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(SealedResult.success(firstPageArts))
            thenUiStateEmitted(dataSuccessfullyLoadedState)
        }

    @Test
    fun `test error list loading`() =
        runTest {
            val error = DataError.Network.NO_INTERNET
            val (errorEvent, errorState) =
                errorEventToState(
                    initialLoadingState,
                    error,
                )
            reducer.addReducerMockkRule(errorEvent, errorState)
            givenInit(SealedResult.failure(error))
            thenUiStateEmitted(errorState)
        }

    @Test
    fun `test on item click navigation`() =
        runTest {
            val firstPageArts = listOfArtsFirstPageStubs()
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    ComposeMap(firstPageArts),
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(
                result = SealedResult.success(firstPageArts),
                event = loadedEvent,
            )

            val objectNumber = "objectNumber1"
            val (itemClickedEvent, itemClickedState) = itemClickEventToState(objectNumber, dataSuccessfullyLoadedState)
            reducer.addReducerMockkRule(itemClickedEvent, itemClickedState)
            coEvery {
                navigator.navigate(Route.ArtsDetailsScreen(objectNumber))
            } returns Unit
            viewModel.onEvent(ListOfArtsScreenEvents.ItemClicked(id = objectNumber))

            thenUiStateEmitted(itemClickedState)

            coVerify {
                navigator.navigate(Route.ArtsDetailsScreen(objectNumber))
            }
        }

    @Test
    fun `test paging successful list loading`() =
        runTest {
            val firstPageArts = listOfArtsFirstPageStubs()
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    ComposeMap(firstPageArts),
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(SealedResult.success(firstPageArts))

            val nextPageStubs = listOfArtsNextPageStubs()
            val result = firstPageArts + nextPageStubs
            val nextPage = START_PAGE + 1

            getGroupedListOfArtsUseCaseFixture.givenListOfArts(
                pageSize = PAGE_SIZE,
                page = nextPage,
                result = SealedResult.success(nextPageStubs),
            )
            mergePagingDataUseCaseFixture.givenPaging(
                oldData = firstPageArts.toMutableMap(),
                newData = nextPageStubs,
                result = result,
            )

            val (loadMoreEvent, loadingMoreWithPaginationState) = loadMoreEventToState(dataSuccessfullyLoadedState)
            reducer.addReducerMockkRule(loadMoreEvent, loadingMoreWithPaginationState)

            val (loadedMoreEvent, loadedMoreWithPaginationState) =
                loadedMoreEventToState(
                    previousState = loadingMoreWithPaginationState,
                    content = ComposeMap(result),
                    page = nextPage,
                    isEndReached = false,
                )

            reducer.addReducerMockkRule(loadedMoreEvent, loadedMoreWithPaginationState)
            // pagination trigger
            viewModel.onEvent(ListOfArtsScreenEvents.LoadMore)

            verify(ordering = Ordering.SEQUENCE) {
                // Initial state
                uiStateObserver.invoke(ListOfArtsScreenState(content = emptyComposeMap()))
                // Loading on start
                uiStateObserver.invoke(initialLoadingState)
                // Loaded first page
                uiStateObserver.invoke(dataSuccessfullyLoadedState)
                // Pagination triggered
                uiStateObserver.invoke(loadingMoreWithPaginationState)
                // Next page loaded
                uiStateObserver.invoke(loadedMoreWithPaginationState)
            }
        }

    @Test
    fun `test paging failed list loading`() =
        runTest {
            val firstPageArts = listOfArtsFirstPageStubs()
            val (loadedEvent, dataSuccessfullyLoadedState) =
                loadedEventToState(
                    initialLoadingState,
                    ComposeMap(firstPageArts),
                )
            reducer.addReducerMockkRule(loadedEvent, dataSuccessfullyLoadedState)
            givenInit(SealedResult.success(firstPageArts))

            val nextPageStubs = listOfArtsNextPageStubs()
            val result = firstPageArts + nextPageStubs
            val nextPage = START_PAGE + 1

            val error = DataError.Network.NO_INTERNET

            getGroupedListOfArtsUseCaseFixture.givenListOfArts(
                pageSize = PAGE_SIZE,
                page = nextPage,
                result = SealedResult.failure(error),
            )
            mergePagingDataUseCaseFixture.givenPaging(
                oldData = firstPageArts.toMutableMap(),
                newData = nextPageStubs,
                result = result,
            )

            val (loadMoreEvent, loadingMoreWithPaginationState) = loadMoreEventToState(dataSuccessfullyLoadedState)
            reducer.addReducerMockkRule(loadMoreEvent, loadingMoreWithPaginationState)

            val (pagingErrorEvent, pagingErrorState) =
                pagingErrorEventToState(
                    loadingMoreWithPaginationState,
                    error,
                )

            reducer.addReducerMockkRule(pagingErrorEvent, pagingErrorState)
            // pagination trigger
            viewModel.onEvent(ListOfArtsScreenEvents.LoadMore)

            verify(ordering = Ordering.SEQUENCE) {
                // Initial state
                uiStateObserver.invoke(ListOfArtsScreenState(content = emptyComposeMap()))
                // Loading on start
                uiStateObserver.invoke(initialLoadingState)
                // Loaded first page
                uiStateObserver.invoke(dataSuccessfullyLoadedState)
                // Pagination triggered
                uiStateObserver.invoke(loadingMoreWithPaginationState)
                // Next page failed to load
                uiStateObserver.invoke(pagingErrorState)
            }
        }

    private fun givenInit(
        result: SealedResult<Map<String, List<UiArtsObject>>, DataError>,
        event: ListOfArtsScreenEvents = ListOfArtsScreenEvents.Loading,
    ) {
        val loadingState = ListOfArtsScreenEvents.Loading
        reducer.addReducerMockkRule(loadingState, initialLoadingState)
        every { uiStateObserver.invoke(any()) } returns Unit
        getGroupedListOfArtsUseCaseFixture.givenListOfArts(
            pageSize = PAGE_SIZE,
            page = START_PAGE,
            result = result,
        )

        viewModel =
            ListOfArtsViewModel(
                getListOfArtsUseCase = getGroupedListOfArtsUseCaseFixture.useCase,
                mergePagingDataUseCase = mergePagingDataUseCaseFixture.useCase,
                reducer = reducer,
                navigator = navigator,
            )
        viewModel.uiState
            .onEach {
                uiStateObserver(it)
            }
            .onCompletion { it?.printStackTrace() }
            .launchIn(testScope)

        viewModel.onEvent(event)
    }

    private fun thenUiStateEmitted(uiState: ListOfArtsScreenState) {
        verify {
            uiStateObserver.invoke(match { it == uiState })
        }
    }
}
