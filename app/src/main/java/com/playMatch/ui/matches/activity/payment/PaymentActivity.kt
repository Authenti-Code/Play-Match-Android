package com.playMatch.ui.matches.activity.payment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityCreateMatchBinding
import com.playMatch.databinding.ActivityPaymentBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.ui.matches.adapter.selectTeamAdapter.SelectTeamAdapter

class PaymentActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPaymentBinding

    private var list = ArrayList<HomeChildModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
//        adapterView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.pay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.pay -> {
                CommonUtils.performIntent(this,MatchDetailsActivity::class.java)
            }
        }
    }
}