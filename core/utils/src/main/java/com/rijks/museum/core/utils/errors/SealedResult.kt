package com.rijks.museum.core.utils.errors

sealed interface SealedResult<out S : Any, out E : Any> {
    data class Success<out S : Any>(
        val value: S,
    ) : SealedResult<S, Nothing>

    data class Failure<out E : Any>(
        val error: E,
    ) : SealedResult<Nothing, E>

    companion object {
        fun <T : Any> success(value: T): Success<T> = Success(value)

        fun <T : Any> failure(error: T): Failure<T> = Failure(error)
    }

    fun isSuccess() = this is Success

    fun isFailure() = this is Failure
}

@Suppress("UNCHECKED_CAST", "TooGenericExceptionCaught")
suspend fun <S : Any, E : Any> wrapThrowable(
    action: suspend () -> S,
    exceptionHandler: (Throwable) -> E,
): SealedResult<S, E> {
    return try {
        SealedResult.success(action())
    } catch (e: Throwable) {
        runCatching { SealedResult.failure(exceptionHandler(e)) }
            .getOrElse { SealedResult.failure(it as? E ?: throw it) }
    }
}

fun <S : Any, E : Any, R : Any> SealedResult<S, E>.fold(
    onSuccess: (S) -> R,
    onError: (E) -> R,
): R {
    return when (this) {
        is SealedResult.Failure -> onError(error)
        is SealedResult.Success -> onSuccess(value)
    }
}

suspend fun <SuccessType : Any, ErrorType : Any, SuccessType1 : Any> SealedResult<SuccessType, ErrorType>.map(
    block: suspend (SuccessType) -> SuccessType1,
): SealedResult<SuccessType1, ErrorType> {
    return when (this) {
        is SealedResult.Failure -> this
        is SealedResult.Success -> SealedResult.success(block(this.value))
    }
}
