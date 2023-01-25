package com.playMatch.ui.profile.activity.settingActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityPaymentBinding
import com.playMatch.databinding.ActivitySettingBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.login.LoginActivity
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity

class SettingActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingBinding

    private var list = ArrayList<HomeChildModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
//        adapterView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.logout -> {
                CommonUtils.performIntentFinish(this, LoginActivity::class.java)
            }
        }
    }
}