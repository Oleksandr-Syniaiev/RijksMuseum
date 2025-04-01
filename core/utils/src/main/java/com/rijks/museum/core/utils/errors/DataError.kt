package com.rijks.museum.core.utils.errors

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }
}
