package com.rijks.museum.core.ui.utils.extensions

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false

fun Boolean.toInt() = if (this) 1 else 0
