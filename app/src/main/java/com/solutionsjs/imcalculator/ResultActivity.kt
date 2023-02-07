package com.solutionsjs.imcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.ads.*
import com.solutionsjs.imcalculator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    private lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAd()
        backHome()
        getData()
    }

    private fun getData() {
        val result = intent.getDoubleExtra("result", 00.00)
        val formatResult = String.format("%.2f", result)
        val resultText = validateData(result)


        binding.imcResultText.text = "Seu IMC é $formatResult, considerado"
        binding.imcTagText.text = resultText
    }

    private fun validateData(imc: Double): String {
        if (imc < 16) {
            return "Magreza Grave"
        }
        else if (imc >= 16 && imc < 17) {
            return "Magreza moderada"
        }
        else if (imc >= 17 && imc < 18.5) {
            return "Magreza Leve"
        }
        else if (imc >= 18.5 && imc < 25) {
            return "Saudável"
        }
        else if (imc >= 25 && imc < 30) {
            return "Sobrepeso"
        }
        else if (imc >= 30 && imc < 35) {
            return "Obesidade grau 1"
        }
        else if (imc >= 35 && imc < 40) {
            return "Obesidade grau 2 (Severa)"
        }
        else  {
            return "Obesidade Grau III (Mórbida)"
        }
    }

    private fun backHome() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAd() {
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Toast.makeText(this@ResultActivity, "Clicou", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(this@ResultActivity, "Retorne para o app", Toast.LENGTH_SHORT).show()
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