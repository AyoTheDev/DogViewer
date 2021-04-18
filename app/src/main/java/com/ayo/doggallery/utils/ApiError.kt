package com.ayo.doggallery.utils

import com.ayo.api.exceptions.NoNetworkException
import com.ayo.api.exceptions.ServerException
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

object ApiError  {

    fun extractErrorMessage(throwable: Throwable?): String {
        return when (throwable) {
            is NoNetworkException -> "Please connect to the internet"
            is ServerException -> "Dogs API is currently down"
            else -> "Problem fetching dogs"
        }
    }
}




