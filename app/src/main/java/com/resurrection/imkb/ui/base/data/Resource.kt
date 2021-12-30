package com.resurrection.imkb.ui.base.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    INVALID
}

sealed class Resource<out T>(val status: Status, val data: T?, val message: Throwable?) {
    class Loading<T> : Resource<T>(Status.LOADING, null, null)
    class Success<T>(data: T?) : Resource<T>(Status.SUCCESS, data, null)
    class Error<T>(exception: Throwable?) : Resource<T>(Status.ERROR, null, exception)
    class InValid<T>(exception: Throwable?) : Resource<T>(Status.INVALID, null, exception)
}

fun <T> getDataByNetwork(request: suspend () -> Response<T>) =  flow { emit(getResourceByNetworkRequest { request() }) }
fun <T> getDataByDatabase(request: suspend () -> T) = flow { emit(getResourceByDatabaseRequest { request() }) }

suspend fun <T> getResourceByNetworkRequest(request: suspend () -> Response<T>): Resource<T> {
    try {
        val response = request()
        if (response.isSuccessful) {
            response.body()?.apply {
                return Resource.Success(this)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return Resource.Error(e)
    }
    return Resource.Loading()
}

suspend fun <T> getResourceByDatabaseRequest(request: suspend () -> T): Resource<T> {
    try {
        val result = request()
        result?.let {
            return Resource.Success(result)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return Resource.Error(e)
    }
    return Resource.Loading()
}