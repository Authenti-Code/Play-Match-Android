package com.playMatch.ui.teams.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddTeamBinding
import com.playMatch.databinding.ActivityMatchSignUpBinding
import com.playMatch.ui.baseActivity.BaseActivity

class AddTeamActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddTeamBinding
    private var CvColor:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeamBinding.inflate(layoutInflater)
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
//        binding.Continue.setOnClickListener(this)
        binding.mondaySlider.setValues(1.0f,5.0f)
        binding.tuesdaySlider.setValues(1.0f,5.0f)
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
                CommonUtils.performIntentFinish(this@AddTeamActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)            }

        }
    }
}