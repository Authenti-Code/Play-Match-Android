package com.playMatch.ui.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySelectSportBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.login.LoginActivity
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList

class SelectSportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectSportBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SportsList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sportListApi()
        initView()
        setAdapter()
     }

    private fun setAdapter() {
        adapter = SelectSportAdapter(list, this)
        binding.rvSports.adapter = adapter
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

    private fun sportListApi(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@SelectSportActivity).sportsList()
                apiSportListResult(resultResponse)
            }
        } else {
            CommonUtils.hideProgressDialog()
            showNetworkSpeedError()
        }
    }

    private fun apiSportListResult(resultResponse: ResultResponse): Any {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as SportListResponse
                //get data and convert string to json and save data
                if (response.success == "true") {

                adapter!!.updateList(response.data)

                } else {
                    CommonUtils.hideProgressDialog()
                    Toast.makeText(
                        this,
                        "Server Error",
                        Toast.LENGTH_SHORT
                    ).show()                }
            }
            else -> {
                CommonUtils.hideProgressDialog()
                showError(resultResponse)
            }
        }
    }
}