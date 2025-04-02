package com.rijks.museum.arts.ui.list

import com.rijks.museum.domain.model.UiArtsObject

data class PagingData(val data: Map<String, List<UiArtsObject>>, val isEndReached: Boolean)
