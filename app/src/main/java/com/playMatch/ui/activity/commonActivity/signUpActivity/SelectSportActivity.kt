package com.playMatch.ui.activity.commonActivity.signUpActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.databinding.ActivitySelectSportBinding
import com.playMatch.databinding.ActivityUserDetailBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity

class SelectSportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectSportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
     }

    private fun initView() {
    }
    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}