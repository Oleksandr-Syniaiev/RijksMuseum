package com.rijks.museum.arts.ui.reducers

import com.rijks.museum.core.utils.reducer.Reducer
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import javax.inject.Inject

class ListOfArtsReducer @Inject constructor() : Reducer<ListOfArtsScreenEvents, ListOfArtsScreenState> {

    override fun invoke(
        event: ListOfArtsScreenEvents
    ): ListOfArtsScreenState.() -> ListOfArtsScreenState = {
        copy()
    }
}
