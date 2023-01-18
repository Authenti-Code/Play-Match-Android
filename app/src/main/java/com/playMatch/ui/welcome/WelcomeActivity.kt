package com.playMatch.ui.welcome

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityWelcomeBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.login.LoginActivity

class WelcomeActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.getStarted.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.getStarted -> {
                CommonUtils.performIntent(this, LoginActivity::class.java)
            }
        }
    }
}