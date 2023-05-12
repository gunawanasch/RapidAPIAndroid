package com.gunawan.rapidapi.pages.json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunawan.rapidapi.R
import com.gunawan.rapidapi.databinding.ActivityJsonBinding
import com.gunawan.rapidapi.viewmodels.JSONViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JSONActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJsonBinding
    private lateinit var jsonAdapter: JSONAdapter
    private val jsonViewModel: JSONViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJsonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        getJSON()
    }

    private fun initView() {
        binding.tbJSON.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbJSON.title                = getString(R.string.title_json)
        binding.pbLoading.visibility        = View.VISIBLE
        binding.rvJSON.visibility           = View.GONE

        binding.tbJSON.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getJSON() {
        jsonViewModel.ldJSON = MutableLiveData()
        jsonViewModel.getJSON(applicationContext, "name-email.json")
        jsonViewModel.ldJSON.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.pbLoading.visibility    = View.GONE
                binding.rvJSON.visibility       = View.VISIBLE

                jsonAdapter = JSONAdapter(it)
                binding.rvJSON.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@JSONActivity)
                    adapter = jsonAdapter
                }
            } else {
                binding.pbLoading.visibility    = View.GONE
                binding.rvJSON.visibility       = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

}