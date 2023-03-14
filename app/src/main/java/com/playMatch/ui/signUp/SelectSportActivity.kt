package com.playMatch.ui.signUp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.selectSportSearchPost.SelectSportSearchPost
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySelectSportBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.ui.signUp.signupModel.*
import com.playMatch.ui.signUp.userSports.UserSportsPost


class SelectSportActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectSportBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SportsList>()
    private var sportLevel=ArrayList<selectedSportModel>()

    private var name:String?=null
    private var search:String?=null
    private var dob:String?=null
    private var fitnessLevel:String?=null
    private var profileImage:Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        sportListApi()
        initView()
        setAdapter()
     }

    private fun setAdapter() {
        adapter = SelectSportAdapter(list, this,object : SelectSportsListener{
            override fun onItemClick(position: Int, list: ArrayList<selectedSportModel>) {
                sportLevel = list
            }

        })
        binding.rvSports.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefData.remove(this, PrefData.CHECK_BOX)
    }

    private fun getIntentData() {

        if (intent?.extras != null) {
            name = intent.extras?.getString(PrefData.NAME,"")
            dob = intent.extras?.getString(PrefData.DOB,"")
            fitnessLevel = intent.extras?.getString(PrefData.FITNESS_LEVEL,"")
//            profileImage= intent.getParcelableExtra<Parcelable>(PrefData.PROFILE_IMAGE) as Bitmap?

            profileImage = BitmapFactory.decodeByteArray(
                intent.getByteArrayExtra(PrefData.PROFILE_IMAGE), 0, intent
                    .getByteArrayExtra(PrefData.PROFILE_IMAGE)!!.size
            )

            if (intent.hasExtra(PrefData.PROFILE_IMAGE)) {
                profileImage = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra(PrefData.PROFILE_IMAGE), 0, intent
                        .getByteArrayExtra(PrefData.PROFILE_IMAGE)!!.size
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.Continue.setOnClickListener(this)


        Glide.with(this)
            .load(profileImage)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .priority(Priority.IMMEDIATE)
            .placeholder(R.drawable.new_dummy_profile)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.profileProgressBar.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.profileProgressBar.visibility = View.GONE
                    return false
                }

            }).into(binding.profileImage)

        binding.userName.text= "Hi,$name"
        binding.fitnessLevel.text= fitnessLevel
        binding.dob.text= dob
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
                val resultResponse = UserApi(this@SelectSportActivity).sportsList(
                    SelectSportSearchPost(search!!))
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
        binding.continueTv.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.continueTv.visibility=View.VISIBLE
    }
}