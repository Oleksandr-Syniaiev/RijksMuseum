package com.rijks.museum.core.ui.utils.states

import androidx.compose.runtime.Immutable

@Immutable
data class ComposeList<T>(val items: List<T>) : List<T> by items

fun <T> emptyComposeList(): ComposeList<T> = ComposeList(emptyList())

