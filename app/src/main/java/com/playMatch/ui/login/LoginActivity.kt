package com.playMatch.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityLoginBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.login.model.LoginResponse
import com.playMatch.ui.signUp.SignUpActivity

class LoginActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }
    private fun initView() {
        binding.login.setOnClickListener(this)
        binding.signupTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                if (binding.email.text.toString().trim().isEmpty()) {
                    binding.email.error =
                        resources.getString(R.string.error_email)
                    requestFocus(binding.email, this@LoginActivity)
                }else if (!validateEmail(binding.email, this@LoginActivity)) {
                    hideProgressBar()
                    return
                }else if (binding.password.text.toString().trim().isEmpty()) {
                    binding.password.error =
                        resources.getString(R.string.error_Password)
                    requestFocus(binding.password, this@LoginActivity)
                }else{
                    loginApi()
                }
            } R.id.signupTv -> {
                CommonUtils.performIntent(this, SignUpActivity::class.java)
            }
        }
    }

    private fun loginApi (){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (isNetworkAvailable()) {
            showProgressBar()
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@LoginActivity).loginUser(
                    LoginPost(
                        email,password, AppConstant.DEVICE_TYPE,
                        "firebaseToken",getUuid()
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
                val response = resultResponse.response as LoginResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    PrefData.setStringPrefs(
                        this@LoginActivity,
                        PrefData.KEY_BEARER_TOKEN,
                        response.token
                    )

//                    PrefData.setStringPrefs(
//                        this@LoginActivity,
//                        PrefData.MY_ID,
//                        response.data._id
//                    )

                    CommonUtils.performIntentFinish(
                        this,
                        HomeActivity::class.java
                    )

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
        binding.loginTv.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.loginTv.visibility=View.VISIBLE
    }
}