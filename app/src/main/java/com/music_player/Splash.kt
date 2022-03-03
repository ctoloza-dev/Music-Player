package com.music_player

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.music_player.views.MainActivity

lateinit var animation: LottieAnimationView

/**
 * Created by David on 20-01-2022.
 */
class Splash : AppCompatActivity() {
    private val secs: Long = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation = findViewById(R.id.animation)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
        }, (secs * 1000))
    }
}