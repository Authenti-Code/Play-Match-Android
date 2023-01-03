package com.playMatch.ui.activity.commonActivity.splashActivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySplashBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity
import com.playMatch.ui.activity.commonActivity.welcomeActivity.WelcomeActivity

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