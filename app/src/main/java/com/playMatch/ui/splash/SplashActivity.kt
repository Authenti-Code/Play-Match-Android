package com.playMatch.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySplashBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.welcome.WelcomeActivity

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({

            CommonUtils.performIntentFinish(
                    this@SplashActivity,
                    WelcomeActivity::class.java
                )

        }, 1500) // 3000 is the delayed time in milliseconds.

    }
}