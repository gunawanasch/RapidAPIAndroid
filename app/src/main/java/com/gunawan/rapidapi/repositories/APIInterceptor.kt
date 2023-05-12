package com.gunawan.rapidapi.repositories

import com.gunawan.rapidapi.utils.Constants.Companion.RAPID_API_HOST
import com.gunawan.rapidapi.utils.Constants.Companion.RAPID_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class APIInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
            .newBuilder()
            .header("X-RapidAPI-Key", RAPID_API_KEY)
            .header("X-RapidAPI-Host", RAPID_API_HOST)
            .build()
        return chain.proceed(originalRequest)
    }

}