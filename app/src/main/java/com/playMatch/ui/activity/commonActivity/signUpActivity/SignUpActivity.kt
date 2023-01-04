package com.playMatch.ui.activity.commonActivity.signUpActivity

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySignUpBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity
import com.playMatch.ui.activity.commonActivity.loginActivity.LoginActivity

class SignUpActivity : BaseActivity(), View.OnClickListener  {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.login.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                CommonUtils.performIntentFinish(this, LoginActivity::class.java)
            } R.id.Continue -> {
            CommonUtils.performIntent(this, UserDetailActivity::class.java)
        }
        }
    }
}