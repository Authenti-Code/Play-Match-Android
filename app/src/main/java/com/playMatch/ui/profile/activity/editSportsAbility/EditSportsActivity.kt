package com.playMatch.ui.profile.activity.editSportsAbility

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.databinding.ActivityEditSportsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.ui.profile.adapter.EditSportAdapter
import com.playMatch.ui.profile.model.editProfile.EditSportList
import com.playMatch.ui.profile.model.editProfile.EditSportResponse
import com.playMatch.ui.profile.model.profile.SportLevel
import com.playMatch.ui.signUp.MatchSignUpActivity
import com.playMatch.ui.signUp.signupModel.*
import com.playMatch.ui.signUp.userSports.UserSportsPost

class EditSportsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditSportsBinding
    private var adapter: EditSportAdapter?=null
    private  var list = ArrayList<EditSportList>()
    private var sportList = ArrayList<EditSportList>()
    private var sportLevel=ArrayList<selectedSportModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityEditSportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
        sportListApi()
        getIntentData()
    }

    private fun getIntentData() {
//        sportList = intent.extras?.getParcelableArrayList(PrefData.SPORT_LIST)!!
    }

    private fun setAdapter() {
        if (list.isEmpty()) {
            adapter = EditSportAdapter(list, this,object:SelectSportsListener{

                override fun onItemClick(position: Int, list: ArrayList<selectedSportModel>) {
                    sportLevel = list
                }
            })
            binding.rvEditSports.adapter = adapter

        }else{
            list.clear()
            setAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefData.remove(this, PrefData.CHECK_BOX)
    }

    private fun initView() {
        binding.update.setOnClickListener(this)
        binding.back.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.update -> {
                selectSportApi()
            }
            R.id.back -> {
                onBackPressed()
            }
        }
    }

    private fun sportListApi(){
        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@EditSportsActivity).editSportsList()
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
                val response = resultResponse.response as EditSportResponse
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
                val resultResponse = UserApi(this@EditSportsActivity).sportsLevels(
                    UserSportsPost(
                        sportLevel
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
                    onBackPressed()
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
        binding.updateTv.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.updateTv.visibility=View.VISIBLE
    }
}