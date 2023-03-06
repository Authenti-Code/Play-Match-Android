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
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.ui.profile.model.profile.SportLevel
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.playMatch.ui.signUp.signupModel.selectedSportModel

class EditSportsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditSportsBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SportsList>()
    private var sportList = ArrayList<SportLevel>()

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
        sportList = intent.extras?.getParcelableArrayList(PrefData.SPORT_LIST)!!
    }

    private fun setAdapter() {
        if (list.isEmpty()) {
            adapter = SelectSportAdapter(list, this,object:SelectSportsListener{

                override fun onItemClick(position: Int, list: ArrayList<selectedSportModel>) {
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
                onBackPressed()
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
                val resultResponse = UserApi(this@EditSportsActivity).sportsList()
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