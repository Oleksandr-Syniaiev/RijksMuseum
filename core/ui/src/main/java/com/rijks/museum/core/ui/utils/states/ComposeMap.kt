package com.rijks.museum.core.ui.utils.states

import androidx.compose.runtime.Immutable

@Immutable
data class ComposeMap<K, V>(val items: Map<K, V>) : Map<K, V> by items

fun <K : Any, V> emptyComposeMap(): ComposeMap<K, V> = ComposeMap(emptyMap())
