package com.gunawan.rapidapi.repositories

import android.content.Context
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JSONRepository @Inject constructor() {

    fun getJSONDataFromAsset(context: Context, fileName: String): String? {
        val output: String
        try {
            output = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        return output
    }

}