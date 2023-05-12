package com.gunawan.rapidapi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gunawan.rapidapi.repositories.RapidAPIRepository
import com.gunawan.rapidapi.repositories.model.AnimeByIdResp
import com.gunawan.rapidapi.repositories.model.AnimeResp
import com.gunawan.rapidapi.repositories.model.GenreResp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RapidAPIViewModel @Inject constructor(private val repo: RapidAPIRepository) : ViewModel() {
    private var disposables         = CompositeDisposable()
    var ldGenre                     = MutableLiveData<List<GenreResp>>()
    var ldAnime                     = MutableLiveData<AnimeResp>()
    var ldAnimeById                 = MutableLiveData<AnimeByIdResp>()
    var ldMsg                       = MutableLiveData<String>()

    fun genre() {
        disposables.add(
            repo.genre()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldGenre.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

    fun anime(page: Int, size: Int, genre: String? = null, search: String? = null) {
        disposables.add(
            repo.anime(page, size, genre, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldAnime.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

    fun animeById(id: String) {
        disposables.add(
            repo.animeById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldAnimeById.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

}