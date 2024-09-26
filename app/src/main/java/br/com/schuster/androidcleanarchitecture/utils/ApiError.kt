package br.com.schuster.androidcleanarchitecture.utils

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toErrorType(): Any = when (this) {
    is IOException -> ErrorType.Api.Network
    is HttpException -> when (code()) {
//        ErrorCodes.Http.ResourceNotFound -> ErrorType.Api.NotFound
        ErrorCodes.Http.ResourceNotFound -> code()
        ErrorCodes.Http.InternalServer -> ErrorType.Api.Server
        ErrorCodes.Http.ServiceUnavailable -> ErrorType.Api.ServiceUnavailable
        else -> ErrorType.Unknown
    }
    else -> ErrorType.Unknown
}

object ErrorCodes {

    object Http {
        const val InternalServer = 501
        const val ServiceUnavailable = 503
        const val ResourceNotFound = 404
    }
}

sealed class ErrorType {

    sealed class Api: ErrorType() {

        data object Network: Api()

        data object ServiceUnavailable : Api()

        data object NotFound : Api()

        data object Server : Api()

    }

    data object Unknown: ErrorType()
}