package com.playMatch.ui.activity.commonActivity.welcomeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.playMatch.R
import com.playMatch.databinding.ActivitySplashBinding
import com.playMatch.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}