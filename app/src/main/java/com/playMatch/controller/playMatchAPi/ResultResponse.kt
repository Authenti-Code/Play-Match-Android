package com.playMatch.controller.playMatchAPi

sealed class ResultResponse {
    data class Success<T>(val response: T?) : ResultResponse()
    data class Error(val error: String) : ResultResponse()
    data class NetworkException(val error: String) : ResultResponse()

    sealed class HttpErrors : ResultResponse() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}