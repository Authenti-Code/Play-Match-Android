package com.playMatch.ui.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.`interface`.RecyclerviewListener
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySelectSportBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.login.LoginActivity
import com.playMatch.ui.login.model.LoginResponse
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsLevelsResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.playMatch.ui.signUp.userSports.UserSportsPost

class SelectSportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectSportBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SportsList>()
    private var sportLevel:String?=null

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
        adapter = SelectSportAdapter(list, this,object : SelectSportsListener{
            override fun onItemClick(position: Int, list: String) {
                sportLevel = list
            }

        })
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
                if (sportLevel!=null) {
                    selectSportApi()
                }
                else{
                   Toast.makeText(this@SelectSportActivity,"Please Select at lease one Sport", Toast.LENGTH_SHORT).show()
                }
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

    private fun selectSportApi (){

        if (isNetworkAvailable()) {
            showProgressBar()
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@SelectSportActivity).sportsLevels(
                    UserSportsPost(
                        sportLevel!!
                    )
                )
                apiResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        hideProgressBar()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as SportsLevelsResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    CommonUtils.performIntent(this, MatchSignUpActivity::class.java)
                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                showError(resultResponse)
            }
        }
    }

    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
        binding.Continue.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.Continue.visibility=View.VISIBLE
    }
}