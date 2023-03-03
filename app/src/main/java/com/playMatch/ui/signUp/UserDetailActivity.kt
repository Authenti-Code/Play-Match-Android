package com.playMatch.ui.signUp

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityUserDetailBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.location.activity.LocationActivity
import com.playMatch.ui.signUp.signupModel.RegistrationResponse
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
import java.util.*

class UserDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserDetailBinding
    private var cal: Calendar = Calendar.getInstance()
    private var name:String?=null
    private var email:String?=null
    private var password:String?=null
    private var confirmPass:String?=null
    private var fitnessLevel:String?="Intermediate"
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }

    private fun initView() {
        binding.Continue.setOnClickListener(this)
        binding.location.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.dob.setOnClickListener(this)
        binding.name.text=name
    }

    private fun getIntentData() {

        if (intent?.extras != null) {
            name = intent.extras?.getString(PrefData.NAME,"")
            email = intent.extras?.getString(PrefData.EMAIL,"")
            password = intent.extras?.getString(PrefData.PASSWORD,"")
            confirmPass = intent.extras?.getString(PrefData.CONFIRM_PASSWORD,"")
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.location -> {
                CommonUtils.performIntent(this, LocationActivity::class.java)
            }

            R.id.beginner -> {
                binding.beginnerCheck.visibility=View.VISIBLE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.GONE
                fitnessLevel=binding.begginerTv.text.toString().trim()
            }
            R.id.intermediate -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.VISIBLE
                binding.experiencedCheck.visibility=View.GONE
                fitnessLevel=binding.IntermediateTv.text.toString().trim()
            }
            R.id.experienced -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.VISIBLE
                fitnessLevel=binding.ExperiencedTv.text.toString().trim()
            }
            R.id.Continue -> {
                val bundle=Bundle()
                showProgressBar()
                if (binding.dob.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_dob, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                } else if (binding.locationTv.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                }else if (binding.locationTv.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                }else{
                    registerApi ()
                }
        }
            R.id.dob -> {
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    }

                DatePickerDialog(
                    this,R.style.dialogTheme,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val location = PrefData.getStringPrefs(this,PrefData.LOCATION,"")
        binding.locationTv.text = location
    }
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dob.text = sdf.format(cal.time)
    }

    private fun registerApi (){

        if (isNetworkAvailable()) {
            showProgressBar()
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@UserDetailActivity).registerUser(
                    RegisterPost(
                        name,email,password,confirmPass,binding.dob.text.toString().trim(),binding.locationTv.text.toString().trim(),binding.gender.text.toString().trim(),fitnessLevel, AppConstant.DEVICE_TYPE,
                        "firebaseToken",getUuid(),
                    )
                )
                apiResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as RegistrationResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    hideProgressBar()
                    val bundle=Bundle()

                    PrefData.setStringPrefs(
                        this@UserDetailActivity,
                        PrefData.KEY_BEARER_TOKEN,
                        response.token
                    )
//                    PrefData.setStringPrefs(
//                        this@RegistrationActivity,
//                        PrefData.EVENT_ID,
//                        response.data._id
//                    )
//                    PrefData.setStringPrefs(
//                        this@RegistrationActivity,
//                        PrefData.MY_ID,
//                        response.data._id
//                    )
//                    val bundle = Bundle()
//                    bundle.putString(
//                        IntentConstant.PHONE_TYPE,
//                        "+" + cpCountry?.phoneCode.toString()
//                                + " " + binding?.edtPhoneNoVerification?.text?.trim().toString()
//                    )
                    bundle.putString(PrefData.NAME, name)
                    bundle.putString(PrefData.DOB, binding.dob.text.toString().trim())
                    bundle.putString(PrefData.FITNESS_LEVEL, fitnessLevel)
                    CommonUtils.performIntentWithBundleFinish(this, AddProfileImageActivity::class.java,bundle)
                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                    hideProgressBar()
                }
            }
            else -> {
                showError(resultResponse)
                hideProgressBar()
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