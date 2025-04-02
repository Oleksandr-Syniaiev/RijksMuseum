package com.rijks.museum.domain.usecase

import com.rijks.museum.domain.model.UiArtsObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MergePagingDataUseCase
    @Inject
    constructor() {
        suspend operator fun invoke(
            oldData: MutableMap<String, List<UiArtsObject>>,
            newData: Map<String, List<UiArtsObject>>,
        ): Map<String, List<UiArtsObject>> =
            withContext(Dispatchers.Default) {
                newData.forEach { (author, items) ->
                    val existingItems = oldData[author] ?: emptyList()
                    val existingIds = existingItems.map { it.id }.toSet()
                    val newUniqueItems = items.filterNot { it.id in existingIds }
                    if (newUniqueItems.isNotEmpty()) {
                        oldData[author] = existingItems + newUniqueItems
                    }
                }

                newData.keys.forEach { author ->
                    if (!oldData.containsKey(author)) {
                        oldData[author] = newData[author] ?: emptyList()
                    }
                }
                return@withContext oldData
            }
    }
