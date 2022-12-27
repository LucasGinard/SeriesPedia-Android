package com.lucasginard.pelispedia.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucasginard.pelispedia.databinding.ActivityHomeBinding
import com.lucasginard.pelispedia.home.step1ListSeries.HomeListFragment
import com.lucasginard.pelispedia.home.step1ListSeries.model.Serie
import com.lucasginard.pelispedia.home.step2DetailSerie.DetailSerieFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFragment()
    }

    private fun setupFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, HomeListFragment())
            .commit()
    }

    fun goFragmentDetail(serie: Serie){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, DetailSerieFragment(serie))
            .addToBackStack(null)
            .commit()
    }
}