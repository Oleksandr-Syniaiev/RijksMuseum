package com.rijks.museum.core.utils.errors

sealed interface DataError : Error {

    enum class Network(val message: String) : DataError {
        REQUEST_TIMEOUT("Request timeout, try again later"),
        NO_INTERNET("Check your internet connection"),
        SERVER_ERROR("Unavailable, try again later"),
        REQUEST_QUOTA_LIMIT("Limit reached, try again later"),
        UNKNOWN("Unknown")
    }

    enum class Local : DataError{
        LOCATION_NOT_FOUND,
    }
}
