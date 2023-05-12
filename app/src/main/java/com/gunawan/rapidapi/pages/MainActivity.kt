package com.gunawan.rapidapi.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunawan.rapidapi.R
import com.gunawan.rapidapi.databinding.ActivityMainBinding
import com.gunawan.rapidapi.pages.anime.AnimeActivity
import com.gunawan.rapidapi.pages.genre.AnimeByGenreActivity
import com.gunawan.rapidapi.pages.json.JSONActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.tbMain.title = getString(R.string.app_name)

        binding.btnJSON.setOnClickListener {
            intent = Intent(this, JSONActivity::class.java)
            startActivity(intent)
        }

        binding.btnAnime.setOnClickListener {
            intent = Intent(this, AnimeActivity::class.java)
            startActivity(intent)
        }

        binding.btnAnimeByGenre.setOnClickListener {
            intent = Intent(this, AnimeByGenreActivity::class.java)
            startActivity(intent)
        }
    }

}