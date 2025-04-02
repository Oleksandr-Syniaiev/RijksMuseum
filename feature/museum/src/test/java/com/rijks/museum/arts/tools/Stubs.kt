package com.rijks.museum.arts.tools

import com.rijks.museum.core.ui.utils.EMPTY_STRING
import com.rijks.museum.domain.model.UiArtsObject

fun listOfArtsFirstPageStubs(): Map<String, List<UiArtsObject>> {
    val rembrandtList = mutableListOf<UiArtsObject>()
    val vanGoghList = mutableListOf<UiArtsObject>()

    val authorRembrandt = "Rembrandt"
    val authorVanGogh = "Van Gogh"

    for (i in 1..20) {
        if (i % 2 != 0) {
            rembrandtList.add(
                UiArtsObject(
                    id = "$i",
                    title = "The Night Watch$i",
                    author = authorRembrandt,
                    image = EMPTY_STRING,
                    objectNumber = "objectNumber$i",
                ),
            )
        } else {
            vanGoghList.add(
                UiArtsObject(
                    id = "$i",
                    title = "Starry Night$i",
                    author = authorVanGogh,
                    image = EMPTY_STRING,
                    objectNumber = "objectNumber$i",
                ),
            )
        }
    }
    return mapOf(
        authorRembrandt to rembrandtList,
        authorVanGogh to vanGoghList,
    )
}

fun listOfArtsNextPageStubs(): Map<String, List<UiArtsObject>> {
    val daVinciList = mutableListOf<UiArtsObject>()
    val monetList = mutableListOf<UiArtsObject>()

    val authorDaVinci = "Leonardo da Vinci"
    val authorMonet = "Claude Monet"

    for (i in 21..40) {
        if (i % 2 != 0) {
            daVinciList.add(
                UiArtsObject(
                    id = "$i",
                    title = "The Last Supper$i",
                    author = authorDaVinci,
                    image = EMPTY_STRING,
                    objectNumber = "objectNumber$i",
                ),
            )
        } else {
            monetList.add(
                UiArtsObject(
                    id = "$i",
                    title = "Water Lilies$i",
                    author = authorMonet,
                    image = EMPTY_STRING,
                    objectNumber = "objectNumber$i",
                ),
            )
        }
    }
    return mapOf(
        authorDaVinci to daVinciList,
        authorMonet to monetList,
    )
}
