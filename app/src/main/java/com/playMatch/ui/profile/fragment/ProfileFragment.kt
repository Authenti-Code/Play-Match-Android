package com.playMatch.ui.profile.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability.MatchAvailabilityPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentProfileBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.profile.activity.editProfile.EditProfileActivity
import com.playMatch.ui.profile.activity.settingActivity.SettingActivity
import com.playMatch.ui.profile.adapter.ProfileSportsAdapter
import com.playMatch.ui.profile.adapter.ProfileStatisticsAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.home.model.ProfileModel
import com.playMatch.ui.profile.model.profile.ProfileResponse
import com.playMatch.ui.profile.model.profile.SportLevel
import com.playMatch.ui.signUp.signupModel.MatchAvailabilityResponse

@Suppress("IMPLICIT_CAST_TO_ANY")
class ProfileFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentProfileBinding? = null
    private var profileSportsAdapter: ProfileSportsAdapter? = null
    private var profileStatisticsAdapter: ProfileStatisticsAdapter? = null
    private var list = ArrayList<ProfileModel>()
    private var sportsList = ArrayList<SportLevel>()
    private var pageNo: String = "1"
    private var totalPages: String = ""
    private var totalTeams:String?="0"
    private var matchesCreated:String?="0"
    private var matchesPlayed:String?="0"
    private var invitesReceived:String?="0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            (activity as BaseActivity).notifications = true
            initViews()
            setAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initViews() {
        PrefData.setBooleanPrefs(requireActivity(), PrefData.KEY_NOTIFICATION_IS_CHAT, false)
        binding?.setting?.setOnClickListener(this)
        binding?.editProfile?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setting->{
                CommonUtils.performIntent(requireActivity(),SettingActivity::class.java)
            }
            R.id.editProfile->{
                CommonUtils.performIntent(requireActivity(),EditProfileActivity::class.java)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setAdapter() {
        profileSportsAdapter = ProfileSportsAdapter(sportsList, requireActivity())
        binding?.rvSports?.adapter = profileSportsAdapter

    }

    private fun profileApi(){

        if ((activity as BaseActivity).isNetworkAvailable()) {
            CommonUtils.showProgressDialog(requireActivity())
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(requireActivity()).profile()
                apiResult(resultResponse)
            }
        } else {
            (activity as BaseActivity).showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as ProfileResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    Glide.with(requireActivity())
                        .load(response.data.image)
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
                                binding?.progressBar?.visibility = View.GONE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding?.progressBar?.visibility = View.GONE
                                return false
                            }

                        }).into(binding!!.profileImage)

                    binding?.name!!.text=response.data.name
                    binding?.email!!.text=response.data.email
                    binding?.dob!!.text=response.data.DOB
                    binding?.genderTv!!.text=response.data.gender
                    binding?.distanceTV!!.text=response.data.distance
                    binding?.fitnessLevel!!.text=response.data.fitnessLevel
                    binding?.address!!.text=response.data.location
                    binding?.address!!.text=response.data.location
                    totalTeams=response.data.totalTeams.toString()
//                    matchesCreated=response.data.matchesCreated.toString()
//                    matchesPlayed=response.data.matchesPlayed.toString()
//                    invitesReceived=response.data.invitesReceived.toString()

                    profileStatisticsAdapter = ProfileStatisticsAdapter(list, requireActivity())
                    binding?.rvStatistics?.adapter = profileStatisticsAdapter

                    list.clear()
                    list.add(
                        ProfileModel(
                            R.drawable.ic_hand,"Total Teams",response.data.totalTeams)
                    )
                    list.add(
                        ProfileModel(
                            R.drawable.stadium,"Matches Created",response.data.matchesCreated)
                    )
                    list.add(
                        ProfileModel(
                            R.drawable.triangle,"Matches Played",matchesPlayed.toString())
                    )
                    list.add(
                        ProfileModel(
                            R.drawable.museum,"Invites Received",invitesReceived.toString())
                    )

//                    profileStatisticsAdapter?.updateList(list)



                    profileSportsAdapter?.updateSportsList(response.data.sportLevel,binding?.rvSports)

                    if (response.data.sun.isNotEmpty()){
                        binding?.Scv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Stv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.mon.isNotEmpty()){
                        binding?.Mcv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Mtv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.tue.isNotEmpty()){
                        binding?.Tcv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Ttv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.wed.isNotEmpty()){
                        binding?.Wcv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Wtv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.thu.isNotEmpty()){
                        binding?.Thcv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Thtv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.fri.isNotEmpty()){
                        binding?.Fcv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Ftv!!.setTextColor(Color.WHITE)
                    }
                    if (response.data.sat.isNotEmpty()){
                        binding?.Sacv!!.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding?.Satv!!.setTextColor(Color.WHITE)
                    }else {
                        if (response.data.sun.isEmpty()) {
                            binding?.Scv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Stv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if (response.data.mon.isEmpty()) {
                            binding?.Mcv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Mtv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if (response.data.tue.isEmpty()) {
                            binding?.Tcv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Ttv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if (response.data.wed.isEmpty()) {
                            binding?.Wcv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Wtv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if (response.data.thu.isEmpty()) {
                            binding?.Thcv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Thtv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if (response.data.fri.isEmpty()) {
                            binding?.Fcv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Ftv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        if(response.data.sat.isEmpty()) {
                            binding?.Sacv!!.setCardBackgroundColor(Color.WHITE)
                            binding?.Satv!!.setTextColor(Color.parseColor("#F95047"))
                        }
                        else{

                        }

                    }

                } else {
                    (activity as BaseActivity).showSnackBar(view?.findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                (activity as BaseActivity).showError(resultResponse)
            }
        } as Unit
    }

    override fun onResume() {
        super.onResume()
        profileApi()
    }
}