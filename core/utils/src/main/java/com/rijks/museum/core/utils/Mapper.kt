package com.rijks.museum.core.utils

interface Mapper<F, T> {
    fun mapFrom(from: F): T

    fun mapAll(from: List<F>): List<T> = from.map(::mapFrom)
}
