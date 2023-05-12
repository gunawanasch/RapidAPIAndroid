package com.gunawan.rapidapi.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gunawan.rapidapi.repositories.JSONRepository
import com.gunawan.rapidapi.repositories.model.JSONResp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class JSONViewModel @Inject constructor(private val repo: JSONRepository) : ViewModel() {
    var ldJSON = MutableLiveData<List<JSONResp>>()

    fun getJSON(context: Context, fileName: String): LiveData<List<JSONResp>>? {
        val strJSON                     = repo.getJSONDataFromAsset(context, fileName)
        val gson                        = Gson()
        val listJSONType                = object : TypeToken<MutableList<JSONResp>>() {}.type
        var listJSON: List<JSONResp>    = gson.fromJson(strJSON, listJSONType)
        ldJSON.value = listJSON

        return ldJSON
    }

}