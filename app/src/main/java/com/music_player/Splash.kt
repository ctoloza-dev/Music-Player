package com.music_player

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.music_player.activities.PlayerActivity

lateinit var animation: LottieAnimationView

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation = findViewById(R.id.animation)
        val secs: Long = 2
        Handler().postDelayed({
            startActivity(Intent(this@Splash, PlayerActivity::class.java))
        }, (secs * 1000))
    }
}