package com.playMatch.ui.signUp

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySelectSportBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData

class SelectSportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectSportBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SelectSportModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
     }

    private fun setAdapter() {
        adapter = SelectSportAdapter(list, this)
        binding.rvSports.adapter = adapter
        list.clear()
        for (i in 1..5) {
            list.add(
                SelectSportModel(
                    "Cricket"
                )
            )

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        PrefData.remove(this, PrefData.CHECK_BOX)
    }

    private fun initView() {
        binding.Continue.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.Continue -> {
                CommonUtils.performIntent(this, MatchSignUpActivity::class.java)
            }

        }
    }
}