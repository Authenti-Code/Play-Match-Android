package com.playMatch.ui.signUp

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySignUpBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.login.LoginActivity

class SignUpActivity : BaseActivity(), View.OnClickListener  {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.login.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                CommonUtils.performIntentFinish(this, LoginActivity::class.java)
            }
            R.id.Continue -> {
            val bundle=Bundle()
            showProgressBar()
            if (binding.name.text.toString().trim().isEmpty()) {
                binding.name.error =
                    resources.getString(R.string.error_name)
                requestFocus(binding.name, this@SignUpActivity)
                hideProgressBar()
            } else if (binding.email.text.toString().trim().isEmpty()) {
                binding.email.error =
                    resources.getString(R.string.error_Email)
                requestFocus(binding.email, this@SignUpActivity)
                hideProgressBar()
            }else if (binding.password.text.toString().trim().isEmpty()) {
                binding.password.error =
                    resources.getString(R.string.error_Password)
                requestFocus(binding.password, this@SignUpActivity)
                hideProgressBar()
            } else if (binding.confirmPass.text.toString().trim().isEmpty()) {
                binding.confirmPass.error =
                    resources.getString(R.string.error_ConfirmPassword)
                requestFocus(binding.confirmPass, this@SignUpActivity)
                hideProgressBar()
            } else if (binding.password.text.toString() != binding.confirmPass.text.toString()) {
                binding.confirmPass.error =
                    resources.getString(R.string.error_match_password)
                hideProgressBar()
            }else{
                bundle.putString(PrefData.NAME,binding.name.text.toString().trim())
                bundle.putString(PrefData.EMAIL,binding.email.text.toString().trim())
                bundle.putString(PrefData.PASSWORD,binding.password.text.toString().trim())
                bundle.putString(PrefData.CONFIRM_PASSWORD,binding.confirmPass.text.toString().trim())
                CommonUtils.performIntentWithBundle(this, UserDetailActivity::class.java,bundle)
                hideProgressBar()
            }
        }
        }
    }

    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
        binding.continueTv.visibility=View.GONE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.continueTv.visibility=View.VISIBLE
    }
}