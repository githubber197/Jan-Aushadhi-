package com.janaushadhi.finder.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.janaushadhi.finder.MainActivity
import com.janaushadhi.finder.databinding.ActivitySplashBinding
import com.janaushadhi.finder.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)

        // Each view needs its OWN Animation instance.
        // Reusing the same Animation object on multiple views (or in an AnimationSet AND
        // directly on a view) corrupts the animation's internal state and can cause a crash.
        val logoScale = ScaleAnimation(
            0.7f, 1f, 0.7f, 1f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply { duration = 800 }
        val logoFade  = AlphaAnimation(0f, 1f).apply { duration = 800 }
        val logoAnim  = AnimationSet(true).apply {
            addAnimation(logoScale)
            addAnimation(logoFade)
        }

        val titleFade    = AlphaAnimation(0f, 1f).apply { duration = 900; startOffset = 200 }
        val taglineFade  = AlphaAnimation(0f, 1f).apply { duration = 1000; startOffset = 400 }

        binding.splashLogo.startAnimation(logoAnim)
        binding.splashTitle.startAnimation(titleFade)
        binding.splashTagline.startAnimation(taglineFade)

        lifecycleScope.launch {
            delay(1200)   // Reduced from 2500ms — shorter = faster launch detection by Android Studio
            val isOnboarded = prefs.getBoolean(Constants.KEY_ONBOARDED, false)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                putExtra("show_onboarding", !isOnboarded)
            })
            finish()
        }
    }
}
