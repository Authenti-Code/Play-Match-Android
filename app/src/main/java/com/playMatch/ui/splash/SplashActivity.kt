package com.playMatch.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySplashBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.login.LoginActivity
import com.playMatch.ui.welcome.WelcomeActivity

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startApp()
        }, 1500) // 3000 is the delayed time in milliseconds.

    }

    private fun startApp() {
//         CommonUtils.performIntentFinish(this, LoginActivity::class.java)
        if (PrefData.getBooleanPrefs(this@SplashActivity,PrefData.IS_USER_LOGIN)) {
            CommonUtils.performIntentFinish(
                this@SplashActivity,
                HomeActivity::class.java
            )
        } else {
            CommonUtils.performIntentFinish(
                this@SplashActivity,
                LoginActivity::class.java
            )
        }
    }
}