package com.rijks.museum.data.source

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.ErrorMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultApiErrorMapper @Inject constructor() : ErrorMapper {

    override fun map(exception: Throwable): DataError =
        when (exception) {
            is IOException -> DataError.Network.NO_INTERNET
            is HttpException -> mapHttp(exception)
            else -> throw exception
        }

    private fun mapHttp(exception: HttpException) = when (exception.code()) {
        500 -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }
}
