package com.gunawan.rapidapi.pages.anime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunawan.rapidapi.R
import com.gunawan.rapidapi.databinding.ActivityAnimeBinding
import com.gunawan.rapidapi.repositories.model.DataItemAnime
import com.gunawan.rapidapi.viewmodels.RapidAPIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var listAnime: MutableList<DataItemAnime>
    private val rapidAPIViewModel: RapidAPIViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        getAnime()
        getAnimeMsg()
    }

    private fun initView() {
        binding.tbAnime.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbAnime.title           = getString(R.string.title_anime)
        binding.pbLoading.visibility    = View.VISIBLE
        binding.rvAnime.visibility      = View.GONE
        listAnime                       = mutableListOf()

        binding.tbAnime.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getAnime() {
        rapidAPIViewModel.ldAnime   = MutableLiveData()
        rapidAPIViewModel.ldMsg     = MutableLiveData()
        rapidAPIViewModel.anime(1, 20)
        rapidAPIViewModel.ldAnime.observe(this) {
            if (it != null) {
                binding.pbLoading.visibility    = View.GONE
                binding.rvAnime.visibility      = View.VISIBLE

                if (!it.data.isNullOrEmpty()) {
                    for (i in it.data.indices) {
                        listAnime.add(it.data[i]!!)
                    }

                    animeAdapter = AnimeAdapter(listAnime.toMutableList())
                    binding.rvAnime.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@AnimeActivity)
                        adapter = animeAdapter
                        animeAdapter.setOnCustomClickListener(object :
                            AnimeAdapter.OnCustomClickListener {
                            override fun onItemClicked(item: DataItemAnime) {
                                intent = Intent(this@AnimeActivity, AnimeDetailActivity::class.java)
                                intent.putExtra("animeId", item.id)
                                startActivity(intent)
                            }
                        })
                    }
                } else {
                    Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.rvAnime.visibility = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getAnimeMsg() {
        rapidAPIViewModel.ldMsg.observe(this) {
            binding.pbLoading.visibility = View.GONE
            binding.rvAnime.visibility = View.GONE
            Toast.makeText(this@AnimeActivity, it, Toast.LENGTH_SHORT).show()
        }
    }

}