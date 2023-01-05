package com.playMatch.ui.activity.commonActivity.signUpActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddProfileImageBinding
import com.playMatch.databinding.ActivityLoginBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity

class AddProfileImageActivity :  BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddProfileImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityAddProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.Continue -> {
                CommonUtils.performIntent(this, SelectSportActivity::class.java)
            }
        }
    }
}