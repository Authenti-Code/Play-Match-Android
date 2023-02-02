package com.playMatch.ui.signUp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchSignUpBinding
import com.playMatch.ui.baseActivity.BaseActivity

class MatchSignUpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMatchSignUpBinding
    private var CvColor:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityMatchSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Stv.setOnClickListener(this)
        binding.Mtv.setOnClickListener(this)
        binding.Ttv.setOnClickListener(this)
        binding.Wtv.setOnClickListener(this)
        binding.Thtv.setOnClickListener(this)
        binding.Ftv.setOnClickListener(this)
        binding.Stv.setOnClickListener(this)
        binding.Scv.setOnClickListener(this)
        binding.Mcv.setOnClickListener(this)
        binding.Tcv.setOnClickListener(this)
        binding.Wcv.setOnClickListener(this)
        binding.Thcv.setOnClickListener(this)
        binding.Fcv.setOnClickListener(this)
        binding.Scv.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
        binding.mondaySlider.setValues(0.0f,5.0f)
        binding.tuesdaySlider.setValues(0.0f,5.0f)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.Scv -> {
                if (CvColor==true){
                    binding.Scv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Stv.setTextColor(Color.WHITE)
                    CvColor=false

                }else{
                    binding.Scv.setCardBackgroundColor(Color.WHITE)
                    binding.Stv.setTextColor(Color.parseColor("#F95047"))
                    CvColor=true
                }
            }
            R.id.back -> {
                onBackPressed()
            }

            R.id.Continue -> {
        CommonUtils.performIntentFinish(this@MatchSignUpActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)            }

        }
    }
}