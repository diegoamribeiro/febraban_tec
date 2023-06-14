package com.cap.techsurvey.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.cap.techsurvey.MainActivity
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.ActivitySplashBinding
import com.cap.techsurvey.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        Utils.hideStatusBar(window)
        navigateToActivity()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        animateImage()
    }

    private fun navigateToActivity() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun animateImage(){

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 3000 // Tempo do efeito em milissegundos.

        binding.ivLogo.startAnimation(alphaAnimation)
    }
}