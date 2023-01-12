package com.playMatch.ui.activity.commonActivity.loginActivity

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityLoginBinding
import com.playMatch.databinding.ActivityWelcomeBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity
import com.playMatch.ui.activity.commonActivity.homeActivity.HomeActivity
import com.playMatch.ui.activity.commonActivity.signUpActivity.SignUpActivity

class LoginActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.login.setOnClickListener(this)
        binding.signupTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                CommonUtils.performIntentFinish(this, HomeActivity::class.java)
            } R.id.signupTv -> {
                CommonUtils.performIntent(this, SignUpActivity::class.java)
            }
        }
    }
}