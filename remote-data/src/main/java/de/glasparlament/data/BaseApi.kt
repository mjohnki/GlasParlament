package de.glasparlament.data

import retrofit2.Response

open class BaseApi{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): Transfer<T> {
        return safeApiResult(call,errorMessage)
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : Transfer<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Transfer.Success(response.body()!!)

        return Transfer.Error(errorMessage)
    }
}
