package com.rijks.museum.core.utils.errors

interface ErrorMapper {
    fun map(exception: Throwable): DataError
}
