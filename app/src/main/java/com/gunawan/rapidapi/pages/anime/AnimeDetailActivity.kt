package com.gunawan.rapidapi.pages.anime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gunawan.rapidapi.R
import com.gunawan.rapidapi.databinding.ActivityAnimeDetailBinding
import com.gunawan.rapidapi.viewmodels.RapidAPIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeDetailBinding
    private val rapidAPIViewModel: RapidAPIViewModel by viewModels()
    private var animeId = ""
    private var titleAnime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        handleCollapsedToolbarTitle()
        getAnimeDetail()
        getAnimeDetailMsg()
    }

    private fun initView() {
        animeId = intent.getStringExtra("animeId").toString()

        binding.tbDetail.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbDetail.title           = getString(R.string.title_anime)
        binding.appBar.visibility       = View.GONE
        binding.nsvDetail.visibility    = View.GONE
        binding.pbLoading.visibility    = View.VISIBLE

        binding.tbDetail.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getAnimeDetail() {
        rapidAPIViewModel.ldAnimeById   = MutableLiveData()
        rapidAPIViewModel.ldMsg         = MutableLiveData()
        rapidAPIViewModel.animeById(animeId)
        rapidAPIViewModel.ldAnimeById.observe(this) {
            if (it != null) {
                binding.appBar.visibility       = View.VISIBLE
                binding.nsvDetail.visibility    = View.VISIBLE
                binding.pbLoading.visibility    = View.GONE

                Glide.with(this).load(it.image).into(binding.ivBackdrop)
                Glide.with(this).load(it.image).into(binding.partialAnimeDetail.ivPoster)

                titleAnime                              = it.title.toString()
                binding.tbDetail.title                  = titleAnime
                binding.partialAnimeDetail.tvTitle.text = titleAnime
                binding.partialAnimeDetail.tvType.text  = it.type

                if (!it.genres.isNullOrEmpty()) {
                    for (i in it.genres.indices) {
                        binding.partialAnimeDetail.cgDetail.addChip(this, it.genres[i] ?: "")
                    }
                }

                binding.partialAnimeDetail.tvValueSynopsis.text = it.synopsis
            } else {
                binding.appBar.visibility                           = View.GONE
                binding.nsvDetail.visibility                        = View.GONE
                binding.pbLoading.visibility                        = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAnimeDetailMsg() {
        rapidAPIViewModel.ldMsg.observe(this) {
            binding.appBar.visibility                           = View.GONE
            binding.nsvDetail.visibility                        = View.GONE
            binding.pbLoading.visibility                        = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun ChipGroup.addChip(context: Context, label: String){
        Chip(context).apply {
            id                      = View.generateViewId()
            text                    = label
            isClickable             = true
            isCheckable             = true
            setChipSpacingHorizontalResource(R.dimen.dimen_4)
            isCheckedIconVisible    = false
            isFocusable             = true
            addView(this)
        }
    }

    private fun handleCollapsedToolbarTitle() {
        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = titleAnime
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }
}