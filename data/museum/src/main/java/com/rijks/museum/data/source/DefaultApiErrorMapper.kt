package com.rijks.museum.data.source

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.ErrorMapper
import com.rijks.museum.data.source.HttpErrorCodes.SERVER_ERROR
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultApiErrorMapper
    @Inject
    constructor() : ErrorMapper {
        override fun map(exception: Throwable): DataError =
            when (exception) {
                is IOException -> DataError.Network.NO_INTERNET
                is HttpException -> mapHttp(exception)
                else -> throw exception
            }

        private fun mapHttp(exception: HttpException) =
            when (exception.code()) {
                SERVER_ERROR -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
    }

object HttpErrorCodes {
    const val SERVER_ERROR = 500
}
