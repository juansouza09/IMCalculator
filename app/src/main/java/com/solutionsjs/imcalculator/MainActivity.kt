package com.solutionsjs.imcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.ads.*
import com.solutionsjs.imcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAd()
        binding.calculateButton.setOnClickListener {
            validateInputData()
        }
    }

    private fun sendData() {
        val height = binding.heightEditText.editText?.text.toString().toDouble()
        val weight = binding.weightEditText.editText?.text.toString().toDouble()
        val result = weight / (height * height)

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("result", result)

        startActivity(intent)

        Log.e("OK", result.toString())
    }

    private fun validateInputData() {
        if (binding.heightEditText.editText!!.text.isNotEmpty() &&
            binding.weightEditText.editText!!.text.isNotEmpty() &&
            binding.heightEditText.editText!!.text.toString().toDouble() in 1.00..2.30 &&
            binding.weightEditText.editText!!.text.toString().toDouble() in 30.00..200.00
        ) {
            sendData()
        } else {
            binding.heightEditText.error = "Insira um valor válido"
            binding.weightEditText.error = "Insira um valor válido"
        }
    }

    private fun setupAd() {
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Toast.makeText(this@MainActivity, "Clicou", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(this@MainActivity, "Retorne para o app", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                mAdView.isVisible = false
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }
}
