package com.playMatch.ui.profile.activity.settingActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityPaymentBinding
import com.playMatch.databinding.ActivitySettingBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.login.LoginActivity
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse

class SettingActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingBinding

    private var list = ArrayList<HomeChildModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
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
                logoutApi()
            }
        }
    }
//-----------------------------------logout Api--------------------------------------------------
    private fun logoutApi(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@SettingActivity).Logout(LogoutPost(getUuid()!!,AppConstant.DEVICE_TYPE,"firebaseToken"))
                apiProfileResult(resultResponse)
            }
        } else {
            CommonUtils.hideProgressDialog()
            showNetworkSpeedError()
        }
    }

    private fun apiProfileResult(resultResponse: ResultResponse): Any {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as LogoutResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    CommonUtils.performIntentFinish(
                        this,
                        LoginActivity::class.java
                    )
                    PrefData.clearWholePreference(this)

                    Toast.makeText(
                        this,
                        "User logged out successfully",
                        Toast.LENGTH_SHORT
                    ).show()


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