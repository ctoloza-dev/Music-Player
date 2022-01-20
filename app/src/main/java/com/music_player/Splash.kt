package com.music_player

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

lateinit var animation: LottieAnimationView

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation = findViewById(R.id.animation)
        Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
        }, 5000)
    }
}