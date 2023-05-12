package com.gunawan.rapidapi.pages.genre

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunawan.rapidapi.R
import com.gunawan.rapidapi.databinding.ActivityAnimeByGenreBinding
import com.gunawan.rapidapi.pages.anime.AnimeAdapter
import com.gunawan.rapidapi.pages.anime.AnimeDetailActivity
import com.gunawan.rapidapi.repositories.model.DataItemAnime
import com.gunawan.rapidapi.repositories.model.GenreResp
import com.gunawan.rapidapi.viewmodels.RapidAPIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeByGenreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeByGenreBinding
    private lateinit var genreAdapter: ArrayAdapter<GenreResp>
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var listGenre: MutableList<GenreResp>
    private lateinit var listAnime: MutableList<DataItemAnime>
    private val rapidAPIViewModel: RapidAPIViewModel by viewModels()
    private var selectedGenre: String? = null
    private var search: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeByGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        getGenre()
        getAnimeByGenreMsg()
    }

    private fun initView() {
        binding.tbAnimeByGenre.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbAnimeByGenre.title        = getString(R.string.title_anime_by_genre)
        binding.pbGenre.visibility          = View.VISIBLE
        binding.llAnimeByGenre.visibility   = View.GONE
        listGenre                           = mutableListOf()
        listAnime                           = mutableListOf()

        binding.tbAnimeByGenre.setNavigationOnClickListener {
            finish()
        }

        binding.etSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                search = binding.etSearch.text.toString()
                val imm = this@AnimeByGenreActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                getAnime()

                return@OnKeyListener true
            }
            false
        })
    }

    private fun getGenre() {
        rapidAPIViewModel.ldGenre   = MutableLiveData()
        rapidAPIViewModel.ldMsg     = MutableLiveData()
        rapidAPIViewModel.genre()
        rapidAPIViewModel.ldGenre.observe(this) {
            if (it != null && !it.isNullOrEmpty()) {
                binding.pbGenre.visibility              = View.GONE
                binding.llAnimeByGenre.visibility       = View.VISIBLE
                binding.pbAnime.visibility              = View.GONE
                binding.rvAnime.visibility              = View.GONE
                listGenre                               = it.toMutableList()
                genreAdapter                            = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
                binding.spGenre.adapter                 = genreAdapter
                binding.spGenre.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedGenre = listGenre[position].id
                        getAnime()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
            } else {
                binding.pbGenre.visibility          = View.GONE
                binding.llAnimeByGenre.visibility   = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAnime() {
        rapidAPIViewModel.ldAnime   = MutableLiveData()
        rapidAPIViewModel.ldMsg     = MutableLiveData()
        binding.pbAnime.visibility  = View.VISIBLE
        binding.rvAnime.visibility  = View.GONE
        listAnime.clear()
        animeAdapter = AnimeAdapter(listAnime.toMutableList())
        binding.rvAnime.adapter = animeAdapter
        if (search == "") search = null
        rapidAPIViewModel.anime(1, 20, selectedGenre, search)
        rapidAPIViewModel.ldAnime.observe(this) {
            if (it != null) {
                binding.pbAnime.visibility  = View.GONE
                binding.rvAnime.visibility  = View.VISIBLE

                if (!it.data.isNullOrEmpty()) {
                    for (i in it.data.indices) {
                        listAnime.add(it.data[i]!!)
                    }

                    animeAdapter = AnimeAdapter(listAnime.toMutableList())
                    binding.rvAnime.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@AnimeByGenreActivity)
                        adapter = animeAdapter
                        animeAdapter.setOnCustomClickListener(object :
                            AnimeAdapter.OnCustomClickListener {
                            override fun onItemClicked(item: DataItemAnime) {
                                intent = Intent(this@AnimeByGenreActivity, AnimeDetailActivity::class.java)
                                intent.putExtra("animeId", item.id)
                                startActivity(intent)
                            }
                        })
                    }
                } else {
                    Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.pbAnime.visibility = View.GONE
                binding.rvAnime.visibility = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getAnimeByGenreMsg() {
        rapidAPIViewModel.ldMsg.observe(this) {
            binding.pbGenre.visibility = View.GONE
            binding.llAnimeByGenre.visibility = View.GONE
            Toast.makeText(this@AnimeByGenreActivity, it, Toast.LENGTH_SHORT).show()
        }
    }
}